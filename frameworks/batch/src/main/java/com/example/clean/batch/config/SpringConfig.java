// package com.example.clean.batch.config;

// import com.example.clean.usecase.port.IdGenerator;
// import com.example.clean.usecase.port.PasswordEncoder;
// import com.example.clean.usecase.port.UserRepository;
// import com.example.clean.adapter.DbUserRepository;
// import com.example.clean.usecase.CreateUser;
// import com.example.clean.usecase.FindUser;
// import com.example.clean.usecase.LoginUser;
// import java.util.UUID;

// public class SpringConfig {
    
//     public class UuidGenerator implements IdGenerator {
    
//         @Override
//         public String generate() {
//             return UUID.randomUUID().toString();
//         }
//     }
//     class Sha256PasswordEncoder implements PasswordEncoder {
//         @Override
//         public String encode(final String str) {
//             // TODO add SHA
//             return str;
//         }
//     }

//     private final UserRepository userRepository = new DbUserRepository();
//     private final IdGenerator idGenerator = new UuidGenerator();
//     private final PasswordEncoder passwordEncoder = new Sha256PasswordEncoder();

//     public CreateUser createUser() {
//         return new CreateUser(userRepository, passwordEncoder, idGenerator);
//     }

//     public FindUser findUser() {
//         return new FindUser(userRepository);
//     }

//     public LoginUser loginUser() {
//         return new LoginUser(userRepository, passwordEncoder);
//     }
// }
