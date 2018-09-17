package com.proy.proy.controller;

import com.proy.proy.model.User;
import com.proy.proy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //GET USER
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getUser(@PathVariable Long id){
        User user = userService.getUser(id);
        if(user != null){
            return new ResponseEntity(user, HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //GET ALL USERS
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getAllUsers(){
        return new ResponseEntity(userService.getAllUsers(), HttpStatus.OK);
    }

    //ADD USER
    @RequestMapping(method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity addUser(@RequestBody User user){
        if(userService.addUser(user)){
            return new ResponseEntity(HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    //UPDATE USER
    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH, produces = "application/json")
    public ResponseEntity updateUser(@PathVariable Long id, @RequestBody User user){
        if(userService.updateUser(id, user)){
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    //DELETE USER
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity deleteUser(@PathVariable Long id){
        if(userService.deleteUser(id)){
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

}
