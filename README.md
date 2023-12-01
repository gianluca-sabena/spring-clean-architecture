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
│ │    │   │- Users.java     │  │  │  │
│ │    │   └─────────────────┘  │  │  │
│ │    │- Ports (UserRepository)│  │  │
│ │    │- CreateUser            │  │  │
│ │    │- FindUser              │  │  │
│ │    │- LoginUser             │  │  │
│ │    └────────────────────────┘  │  │
│ │- Jdbc                          │  │
│ │- Memory                        │  │
│ └────────────────────────────────┘  │
│- Spring Batch                       │
│- Spring Servlet                     │
└─────────────────────────────────────┘
```

## Configure
In order to switch adapter from memory to jdbc
- 1) edit [settings.gradle](./settings.gradle) and comment `include 'adapters:memory'` and uncomment `include 'adapters:jdbc'`
- 2) In folder: frameworks / servlet edit file [build.gradle](./frameworks/servlet/build.gradle) and comment `implementation project(':adapters:memory')` and uncomment `implementation project(':adapters:jdbc')`
- 3) Repeat step 2 for all other frameworks...

## Run

- Run `./gradlew bootRun`
- Build (include tests) `./gradlew build`
- Test
  - Framework servlet see [frameworks/servlet/README.md](./frameworks/servlet/README.md) 



## Source

- Generate spring servlet from spring boot initializr
- Add example from official guide <https://github.com/spring-guides/tut-rest>
- Add code from <https://github.com/microservices-demo/orders>

## TODO
- Experiments with application properties
- JDBC with postgresql and oracle with 

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
- Baeldung - Rest with pagination <https://www.baeldung.com/rest-api-pagination-in-spring>
- Spring multi module <https://spring.io/guides/gs/multi-module/>
- Blog <https://www.arhohuttunen.com/hexagonal-architecture-spring-boot/>
- Blog <https://betterprogramming.pub/hexagonal-architecture-with-spring-boot-74e93030eba3>

