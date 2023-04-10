# delivery-application

This is delivery application which has three types of client (customer, restaurant, rider)  
This is kind of full service that can handle peak RPS up to 10000.

Now project is under the task of AWS setting.

## System structure

### Rough design
![rough_design](/document/image/rough_design.PNG)

### Specific design considering AWS
![specific_design](/document/image/specific_design.PNG)

## DB Selection

### NoSQL

Key-value DB is fast and simple but not good for the complex data type which this project need. Graph DB is good for graph network structure which is not needed for this project. We choose Document DB and Column Oriented DB for our DB. MongoDB for DocumentDB and Cassandra for Columnar DB.

### CAP theory
Following CAP theory, Mongo DB is CP database. It has consistency and partition tolerence. Consistency is important for restaurant service and rider service because their action to change order state can be occured at the same time. Blocking the write action from another subject when it try to write to change the order state is essential because concurrent writing may cause different result randomly. We use mongoDB for restaurant application and rider application. 
Cassandra is AP database. It focus on Availibility rather than consistency. Availibility is needed for customer service because the application has shopping basket. There may be a lot of write action (add, delete, update) so write/read latency is so important for shopping basket. Cassandra has multi-master structure so its read/write latency is absolutely lower than mongoDB(document DB). we choose cassandra for customer application.