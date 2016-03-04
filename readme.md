# camel-file-processing Project
====================

This is a distributed large file processing framework using Apache Camel. 
It is based on an event model. There code is written in such a way the the application can be distributed across multiple jvms.

The events here are:
* File Read 
* Record Processing
* Record Saving

These events are produced and queued using ActiveMQ. 
Distributed consumers consume the messages and work on them. Then they again queue the message for the next stage of processing.

This application can be deployed on a container as a war file. It is currently configured to run on JBoss EAP 6.4.x servers.

This project uses following components:
* Apache Camel 2.15.x
* Active MQ running on Docker image
* Apache Camel BeanIO (unmarshalling csv record to java objects)


## To build the project

From the project directory

`mvn clean install`

## Deploy the project

Build will generate a `war` file. That war file can be deployed on JBoss EAP 6.4.x servers.
 