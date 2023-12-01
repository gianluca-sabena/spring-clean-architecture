# spring-clean-architecture

[Generated from spring initializr](https://start.spring.io/#!type=gradle-project&language=java&platformVersion=3.1.5&packaging=jar&jvmVersion=17&groupId=com.example&artifactId=rest-service&name=rest-service&description=Demo%20project%20for%20Spring%20Boot&packageName=com.example.rest-service&dependencies=web,devtools,security,actuator,jdbc,postgresql,modulith,batch,liquibase,prometheus)



## Test

Create a user
```curl -X POST  http://127.0.0.1:8080/users --header "Content-Type: application/json"  --data '{"email":"xyz@ab.com","password":"xyz",  "lastName": "Me", "firstName": "You"}'```

List Users
````curl -X GET  http://127.0.0.1:8080/users `