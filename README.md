# delivery-application

![delivery_icon.PNG](document%2Fimage%2Fdelivery_icon.PNG)

## Summary
This is a toy project implementing application for food delivery system.
The server serves three type of clients (customer, restaurant, rider).
This system produces basic function of delivery system, that is ordering menu. 
If a customer sends food delivery order, the restaurant can accept or deny the order.
If the restaurant accepts the order, it can be chosen by a rider for delivery.
Order status is going to be changed according to the present situation.

For your information, this project was implemented focusing on basic ordering function.
It doesn't have any coupon, event, discount and review system. 
Don't treat this project as real app. 
Hope you treat it as reference for making delivery ordering system.

## System structure

The whole system is consist of microservice architecture using message queue to communicate with each service.
Each service(customer, restaurant, rider) treats order information commonly, 
sending the order status to each service without being affected by responses from other services. 
That is why every service is connected by message queue.

![img.png](document/image/System_structure.png)

It is assumed that this project will use API gateway such as AWS API gateway.
The API gateway is connected to user authentication service like Cognito.
The responsibility of every authentication and authorization will be taken by Cognito and API gateway.
Each service server doesn't have to consider authentication and authorization process.

You can see more information about system architecture consideration from here. 
It is written in Korean.
https://bit.ly/4266S28

## DB selection

### NoSQL

The delivery application doesn't need to use SQL database.
Changing order status is the most important part of this project and the query related to order is quite simple. 
Furthermore, there was little possibility of manipulating multiple queries at the same time, so it doesn't need transaction for each service logic. 

NoSQL write/read time is much faster than SQL, 
that is helpful to improve user experience especially for basket function.

These are why I chose NoSQL for main database. 

### MongoDB
MongoDB serves DB lock. By using it, we can block the concurrent approach the order data
when the several riders want to take same order at the same time.

## Issue #1 Why the services are configured separately and use message queue?

Each service(customer, restaurant, rider) has to work independently.
In case of real production, there would be a possibility of certain server down for some reasons.
Regardless of whether the restaurant server is working or not,
customer server should work independently to get order from customer.
while breakdown of restaurant service, the message queue hold the order information from customer service.
After quick server recovery, restaurant can get order request from message queue, 
so there is no need for customer to send request order again. 
Separation of service and using message queue is really needed for user experience.

## Issue #2  How can we show each order to rider based on its location?

How can the system shows order data for rider based on near-field area?
It is needed to show only limited order data whose store location is near current rider's location (about within 4km)
when rider uses application to check new order delivery request.

### MongoDB
MongoDB serves Geo-spatial query which returns the data based on given location parameters.
With MongoDB, the implementation to get order information close to specific region is quite simple.
We can save order data with store location by using spring "Point". 
When rider send request to get new order near rider's location,
The server use Geo-spatial query with rider location
then MongoDB return the order information whose store location is near rider location.
This is another reason why I chose MongoDB as main Database.

So, all logic to get order information based on location is processed by MongoDB.
But I will implement service logic to get order information near rider's location without MongoDB later

Here is example code of Spring Data geolocation query method

```java
public interface SpringDataMongoOrderRepository extends MongoRepository<Order, String> {
    List<Order> findByStoreLocationNearAndOrderStatusIs(Point location, OrderStatus orderStatus);
}
```


## Issue #3 How can we get latitude and longitude?

To use MongoDB Geo-spatial query, we should use point class of spring framework.
Point class is simple. It has two properties, latitude and longitude.

```java
public class Point implements Serializable {

    private final double x;
    private final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
```

The problem is the system don't have the latitude and longitude information of clients.
There is no b2c application in the world that requires the client to put their latitude and longitude information.

The solution was quite simple: using well-known location api. 
I chose Kakao developer API to convert address to latitude and longitude.
This api works flexibly. It treats "서울", "서울시" and "서울특별시" as same information.
Those words mean really same but have different notation because of Korean grammar.

https://developers.kakao.com/docs/latest/ko/local/dev-guide

## Issue #4 How can client get order status update in real time while using app?

The clients(customer, restaurant and rider) should get real time update notification of order status change while they are using app.

There are two options to implement real time update notification.
First is web-socket, second is server-sent-event. 
It is shown that the performance of two technologies is quite similar by according to experimental research:

>The performance difference identified between Websockets and SSE is too small to be analyzed. The
differences could be a result of measurement inaccuracies. In theory, the overhead data would not
increase with payload size for SSE while it should do that for Websockets. Smaller payloads however
allow Websockets to send slightly less overhead than SSE. The difference is only a few bytes so a very
high client amount would be needed for this to possibly be measured and compared. These differences
are situational and it is concluded that identifying one of them as more performance efficient than the
other is not doable.

http://www.diva-portal.se/smash/get/diva2:1133465/FULLTEXT01.pdf

I chose server-sent-event because I thought SSE is more appropriate solution for this matter.
Notification function needs one directional transmission (server -> client).
It doesn't need bidirectional transmission. 
SSE data is sent over HTTP. It doesn't need custom protocol.
It is more simple to implement than websocket.

### SSE Implementation

SSE emitter is included in spring web library.
This is how server side SSE is implemented in java code.

- controller

```java

    @GetMapping("/{orderId}")
    @ResponseStatus(HttpStatus.OK)
    public SseEmitter showOrderInfo(@RequestAttribute("cognitoUsername") String customerId,
                                    @PathVariable String orderId){
        SseEmitter sseEmitter = sseService.connect(customerId);
        sseService.showOrder(customerId, orderId);
        return sseEmitter;
    }

```

- service

```java

    ConcurrentHashMap<String, SseEmitter> emitterList = new ConcurrentHashMap<>();

    public SseEmitter connect(String customerId) {
        SseEmitter emitter = new SseEmitter();
        emitterList.put(customerId, emitter);
        log.info("new emitter created: {}", emitter);
        emitter.onCompletion(() -> {
            emitterList.remove(emitter);
            log.info("emitter deleted: {}", emitter);
        });
        emitter.onTimeout(emitter::complete);
        try{
            emitter.send(SseEmitter.event().name("connect").data("connected"));
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return emitter;
    }

```

When client send the request, 
the server creates sse emitter and store it in server hashmap
to use it for a follow-up request.

showOrder method finds the emitter saved in server hashmap 
and uses it to send data by using SSE.

## Issue #5 Deserialization problem when using message queue

There was error about deserialization when the server get a message from message queue.
That is because it uses point of spring for its location property. 
JsonObject library doesn't support type of point class basically. 
To solve this problem, it is needed to deserialize the json data by using
object mapper of jackson library. 

Here is example of use of custom deserializer below.


### custom deserialization

```java

public class OrderDeserializer extends StdDeserializer {

    public OrderDeserializer() {
        this(null);
    }

    protected OrderDeserializer(Class vc) {
        super(vc);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        ...
        double cx = node.get("customerLocation").get("x").asDouble();
        double cy = node.get("customerLocation").get("y").asDouble();
        Point customerLocation = new Point(cx, cy);
        ...
    }
}

```

in receiving message function

```java
    public Order convertOrderDataWithCustomDeserializer(JSONObject jsonObject) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(Order.class, new OrderDeserializer());
        objectMapper.registerModule(module);
        String data = jsonObject.get("data").toString();
        return objectMapper.readValue(data, Order.class);
    }
```


## Issue #6 SSE matching problem in scale-out server environment.

There was problem related to SSE matching.
In scaled-out server environment, Each server has its own hashmap holding emitter value and client id key. 
When receiving event message from message queue,
the server has to notify client a real time update of order status in case of the client continuously staying in the order information page.
The problem is there is no way for each server to know which server has the right emitter key(client id) in its hash map.

Many solution are available. We can use zookeeper for server to communicate with other servers to notify event just received.
The solution, however, that I choose is redis pub/sub function which is quite light and simple solution.

![img.png](document/image/Redis_pubsub.png)


Spring Data Redis serves MessageListener interface. 
we can implement subscriber function of redis by using it. 

```java
public class SubService implements MessageListener {

    private final SseService sseService;

    public SubService(SseService sseService) {
        this.sseService = sseService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RiderMatchingMessage customerMatchingMessage = objectMapper.readValue(message.getBody(), RiderMatchingMessage.class);
            String riderId = customerMatchingMessage.getRiderId();
            String orderId = customerMatchingMessage.getOrderId();
            log.info("redis sub message: riderId={}, orderId={}", riderId, orderId);
            sseService.updateOrderFromRedis(riderId, orderId);
        } catch (IOException e) {
            log.error("error occurred={}", e.getMessage());
        }
    }
}
```
And publisher function can be implemented by using RedisTemplate.

```java
public class PubService {

    private final RedisTemplate<String, Object> redisTemplate;

    public PubService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void sendMessageToMatchRider(String riderId, String orderId) {
        RiderMatchingMessage customerMatchingMessage = new RiderMatchingMessage(riderId, orderId);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String messageString = objectMapper.writeValueAsString(customerMatchingMessage);
            redisTemplate.convertAndSend("rider-matching", messageString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
```



After receiving order status change message from queue,
the server checks if it has the id as key in the server hashmap.
If it doesn't have the key in hashmap, the server publishes the client id and order id to redis which works as message broker here.

Below is the code to check if the server has id as key in hashmap.

```java
    public void updateOrderFromSqs(String customerId, String orderId){
        if (emitterList.containsKey(customerId)){
            log.info("The server has customerId={}", customerId);
            showOrder(customerId, orderId);
        } else{
            log.info("The server doesn't have customerId={} redis publish is activated", customerId);
            pubService.sendMessageToMatchCustomer(customerId, orderId);
        }
    }
```

The redis broadcasts the data to all server in service(customer, restaurant, rider).
Every server in same service subscribe same topic in redis so that they can receive same id data.
By then, the server who has the key of emitter for SSE which means the client continuously see the order info page
send notification about order status update to its client.
If server doesn't have the key in its hashmap, it simply abandons message.

```java
    public void updateOrderFromRedis(String customerId, String orderId){
        if (emitterList.containsKey(customerId)){
            log.info("The server has customerId={}", customerId);
            showOrder(customerId, orderId);
        }
    }
```