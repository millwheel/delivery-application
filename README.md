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

## DB selection

### NoSQL

The delivery application doesn't need to use SQL database.
Changing order status is the most important part of this project and the query related to order is quite simple. 
Furthermore, there was little possibility of manipulating multiple queries at the same time so it doesn't need transaction for each service logic. 

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

