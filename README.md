# Amadeus Spring Boot 3.0 Case Study AirlineManagement
This project created for Amadeus Case Study. In this project includes the following features:

## Features
* User registration and login with JWT authentication
* Role-based authorization with Spring Security
* Refresh token
* Search recursive flight for finding transit or direct flights and order these flights by total price

## Technologies
* Spring Boot 3.0
* Spring Security
* JSON Web Tokens (JWT)
* Springdoc-openapi (Swagger for Spring Boot 3)
* H2 integrated database
* Maven

## Getting Started
To get started with this project, you will need to have the following installed on your local machine:

* JDK 17+
* Maven 3+

To build and run the project, follow these steps:

* Build the project: `mvn clean install`
* Run the project: `mvn spring-boot:run`

=> The application will be available at http://localhost:8080 <br/>
=> You can access hs-database at http://localhost:8080/h2-console/ <br/>
=> Swagger document UI will have accessed in http://localhost:8080/swagger-ui/index.html 

## Initialize with docker

To build and run the project, follow these steps:

* Build the project: `mvn clean install`
* Create an image from project: `docker build --build-arg JAR_FILE="target/*.jar" -t airlinemanagement .`
* Deploy the image to container and run: `docker run airlinemanagement --p 8080:8080 .`

=> The application will be available at http://localhost:8080 <br/>
=> You can access hs-database at http://localhost:8080/h2-console/ <br/>
=> Swagger document UI will have been running in http://localhost:8080/swagger-ui/index.html
## Authorization

Some of the endpoints require authentication, so you need authentication key firstly.
You can create User and get this user's token with /auth/register POST endpoint. This token have expiration date so when token expire you will need get new token.
This created user have USER role because of that you can't create or update airports or flight with that.
If you want to get new token with know user you can use /auth/authenticate POST endpoint.
Also, there is predefined admin user. Username: admin, Password: admin. With this user you can create or modify flight and airports.

I generate a admin token which is never expire. You can integrate your application or use in your browser this token
Never expire token: `eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY5NDM0NDQxMiwiZXhwIjo5MjIzMzcyMDM2ODU0Nzc1fQ.I1_IpYXGgzKra4uQ-l0Q-hEZRBuAJrmu0LwBxW2HgQw`

You must add request's header this token like below.
Authorization: Bearer [$token]

On swagger ui, it will enough write only token in Authorization label.

## Before initialization these have known
Before application entirely start data.sql will run. This file insert 10 different airports. You must use these cities for searching.
After that for testing run initialization event. This event create 1000 flights per day for now to 20 days later.You could change this value in application.properties. You must use these date for searching.

## Search Flight

For searching flight, I create an endpoint which url is '/flight/search'. These endpoint's have query params which are departureCity, destinationCity, beginDate, returnDate(optional). Date format must be yyyy-MM-dd.
Max transit flight count is 2. You can change this value with application.properties file. You must be careful to change this value. Increasing this value slowing the application exponential.

## Future Development

* Token blacklist for logout
* Add different sql provider like mysql or oracle.
* Add Unit tests.
