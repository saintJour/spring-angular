package com.proy.proy.service;

import com.google.common.io.BaseEncoding;
import com.proy.proy.model.User;
import com.proy.proy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    public User getUser(Long id){
        return userRepository.findById(id).orElse(null);
    }

    public Iterable<User> getAllUsers(){
        return userRepository.findAll();
    }

    public boolean addUser(User user){
        if(user.getPassword() == null || user.getEmail() == null){
            return false;
        }
        else{
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            String token = BaseEncoding.base64().encode(user.getEmail().getBytes());
            user.setEmailToken(token);
            String domain = "http://localhost:8080/auth/confirm?token=";
            sendSimpleMessage(user.getEmail(), "Confirmación", "Por favor, presione el siguiente link para validar su cuenta: " + domain + token);
            userRepository.save(user);
            return true;
        }
    }

    public boolean updateUser(Long id, User user){
        if(userRepository.existsById(id)){
            User userToUpdate = userRepository.findById(id).get();
            if(user.getName() != null){
                userToUpdate.setName(user.getName());
            }
            if(user.getAvatar() != null){
                userToUpdate.setAvatar(user.getAvatar());
            }
            userRepository.save(userToUpdate);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean deleteUser(Long id){
        if(userRepository.existsById(id)){
            userRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
