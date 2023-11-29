// package com.example.clean.batch.config;

// //import com.fasterxml.jackson.annotation.JsonInclude;
// //import com.fasterxml.jackson.databind.ObjectMapper;
// //import com.slalom.config.SpringConfig;
// //import com.example.clean.restservice.controller.UserController;
// import com.example.clean.usecase.CreateUser;
// import com.example.clean.usecase.FindUser;
// import com.example.clean.usecase.LoginUser;
// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// @Configuration
// public class Config {

//     private final SpringConfig config = new SpringConfig();

//     // @Bean
//     // public ObjectMapper objectMapper() {
//     //     var objectMapper = new ObjectMapper();
//     //     objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
//     //     return objectMapper;
//     // }

//     @Bean
//     public CreateUser createUser() {
//         return config.createUser();
//     }

//     @Bean
//     public FindUser findUser() {
//         return config.findUser();
//     }

//     @Bean
//     public LoginUser loginUser() {
//         return config.loginUser();
//     }

//     // @Bean
//     // public UserController userController() {
//     //     return new UserController(createUser(), findUser(), loginUser());
//     // }
// } 
