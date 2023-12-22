# Spring clean

An opinionated spring template based on a "clean architecture". (See credits)

## Architecture
```
┌─────────────────────────────────────┐
│Framework                            │
│ ┌────────────────────────────────┐  │
│ │Adapters                        │  │
│ │    ┌────────────────────────┐  │  │
│ │    │Use cases               │  │  │
│ │    │   ┌─────────────────┐  │  │  │
│ │    │   │Entities         │  │  │  │
│ │    │   │- Customers.java │  │  │  │
│ │    │   └─────────────────┘  │  │  │
│ │    │- Ports (CustomerRep...)│  │  │
│ │    │- CreateCustomer        │  │  │
│ │    │- FindCustomer          │  │  │
│ │    │- LoginCustomer         │  │  │
│ │    └────────────────────────┘  │  │
│ │- Jdbc                          │  │
│ │- Memory                        │  │
│ └────────────────────────────────┘  │
│- Spring Batch                       │
│- Spring Servlet                     │
└─────────────────────────────────────┘
```

## 1. Setup Environment

Java 17 or higher is required

Linux environment is required to run docker container

Linux/WSL2 - **Recommended** 
- Install sdkman <https://sdkman.io/>
- Install java `sdk install java 20.0.2-open`
- Install docker

Windows
- Install scoop.sh <https://scoop.sh/>
- Install java `scoop install openjdk20`
- Install git with bash `scoop install main/git`
- Start a bash terminal with `git-bash.exe`

## 2. Run

Open a terminal with a bash compatible shell

Use `devel.sh -h` to print help main actions 

- Start docker compose with postgreSQL `devel.sh du`
- `devel.sh test` -> execute `./gradlew build`
- `devel.sh buid` -> execute `./gradlew build`
- `devel.sh run` -> execute `./gradlew bootRun`

## 3. Setup Project

In order to switch adapter from memory to jdbc

Short answer: use `devel.sh config`

Long answer:
- 1) edit [settings.gradle](./settings.gradle) and comment `include 'adapters:memory'` and uncomment `include 'adapters:jdbc'`
- 2) In folder: frameworks / servlet edit file [build.gradle](./frameworks/servlet/build.gradle) and comment `implementation project(':adapters:memory')` and uncomment `implementation project(':adapters:jdbc')`
- 3) Repeat step 2 for all other frameworks...

## 4. Test 

### Framework/servlet

- Create a customer ```curl -X POST  http://127.0.0.1:8080/customers --header "Content-Type: application/json"  --data '{"email":"xyz@ab.com", "lastName": "Me", "firstName": "You"}'```
- List customers ```curl -X GET  http://127.0.0.1:8080/customers```

## TODO
- Experiments with application properties
- JDBC with postgresql and oracle with ucp
- Add tests to adapter
- Oauth2 with local IDP provider (<https://www.keycloak.org/>)
- Tescontainers
- Buid jar
- Add more entities (item, order)
- Integrate lombok for entities
- Add a serialization adapter

## Credits

This project was inspired by:

- Anatomy of a Spring Boot App with Clean Architecture Spring I/O 2023 - [Video](https://www.youtube.com/watch?v=mbNzUkNjrnA) - [Slide](https://www.slideshare.net/StevePember/anatomy-of-a-spring-boot-app-with-clean-architecture-spring-io-2023) - [ Kotlin code](https://github.com/spember/spring-shoestore)
- Spring boot example - Baeldung <https://www.baeldung.com/spring-boot-clean-architecture>
- Spring example with multi-module gradle <https://github.com/carlphilipp/clean-architecture-example/tree/master>
- Spring example with multi-module gradle - Thombergs <https://github.com/thombergs/buckpal>
- Blog <https://medium.com/@viniciusromualdobusiness/clean-architecture-with-spring-boot-a-good-idea-d6f97e450130>
- Red Hat <https://developers.redhat.com/articles/2023/08/08/implementing-clean-architecture-solutions-practical-example>
- Example with ArchUnit <https://reflectoring.io/java-components-clean-boundaries/>

## Resources
- Generate spring servlet from spring boot initializr
- Spring Boot rest <https://github.com/spring-guides/tut-rest>
- Microservice example <https://github.com/microservices-demo/orders>
- Baeldung - Rest with pagination <https://www.baeldung.com/rest-api-pagination-in-spring>
- Spring multi module <https://spring.io/guides/gs/multi-module/>
- Blog <https://www.arhohuttunen.com/hexagonal-architecture-spring-boot/>
- Blog <https://betterprogramming.pub/hexagonal-architecture-with-spring-boot-74e93030eba3>
