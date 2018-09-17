package com.proy.proy.service;

import com.google.common.io.BaseEncoding;
import com.proy.proy.model.User;
import com.proy.proy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserRepository userRepository;

    public boolean confirmUser(String token){
        byte[] decoded =  BaseEncoding.base64().decode(token);
        String decodedEmail =  new String(decoded);
        User user = userRepository.findByEmail(decodedEmail);
        if(user != null){
            user.setStatus(true);
            userRepository.save(user);
            return true;
        }
        else{
            return false;
        }
    }

}
