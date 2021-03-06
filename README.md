# Text Based Voting Application (RESTful Services)

This is a repository containing a text based voting application which demostrates the use of Spring based MVC framework and a number of 3rd party integrations.

Requirements:
https://docs.google.com/document/d/1PO10ZMc29OqqDWIVy28Qq3oXJwsqN9PCtSn3U-LQPO0/pub

Core Functionality:
- Moderator - Create, Update, View, Delete.
- Poll - Create, Update, Vote, View a Poll with Voting Results, View a Poll without Voting Results, View all Polls for a Moderator, Delete.

Features:
- API call validation (returns proper status codes).
- Basic Authentication (Spring HTTP Authentication Package).
- Scheduler (Spring Scheduler Package, checks for expired polls every 5 minutes).
- Remote Messaging (Message broker (Kafka Producer) sends emails with set (topic, message) for expired polls).
- Peristent Data (MongoLab).
- Amazon Cloud Hosting (EC2)

Technology Stack:
- Spring MVC
- Gradle (Build Automation/Packaging)
- Additional Spring Libraries (Authentication, HTTP Validation, Scheduler)
- MongoLab
- Kafka
- Amazon Web Services (EC2)

Files:
- resources/application.properties: connection file to hosted MongoLab. (test123:test123/mongolab) -> (username:password/DB name)

Sample Input for testing (Postman recommended):
- Postman configuration: raw -> JSON format; Header = "Content-Type", Value = "application/json"

- Endpoint (local): http://localhost:8080/api/v1/moderators/
- Endpoint (hosted): http://ec2-54-153-90-122.us-west-1.compute.amazonaws.com:8080/api/v1/moderators/
- Moderator
{
 "name": "John Smith",
 "email": "John.Smith@Gmail.com",
 "password": "secret"
}

- Endpoint (local): http://localhost:8080/api/v1/moderators/YYYYYY/polls
- Endpoint (hosted): http://ec2-54-153-90-122.us-west-1.compute.amazonaws.com:8080/api/v1/moderators/XXXXXX/polls
- Poll
{
 "question": "What type of smartphone do you have?",
 "started_at": "2015-02-23T13:00:00.000Z",
 "expired_at" : "2015-02-24T13:00:00.000Z",
 "choice": [ "Android", "iPhone" ]
}

