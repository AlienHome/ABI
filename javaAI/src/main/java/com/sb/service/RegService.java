// package com.sb.service;

// import org.springframework.security.access.prepost.PostAuthorize;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Service;
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

// @Service
// public class RegService{
//     static Logger logger = LoggerFactory.getLogger(RegService.class);
//     public int reg(String username, String password){
//         BCryptPasswordEncoder encoderPwd = new BCryptPasswordEncoder(10);
//         String encodedPwd = encoderPwd.encode(password);
//         logger.info("password:{}, endoced password:{}", password, encodedPwd);
//         return saveToDb(username, encodedPwd);
//     }
//     private int saveToDb(String username, String encodedPwd){
//         return 0;
//     }
// }