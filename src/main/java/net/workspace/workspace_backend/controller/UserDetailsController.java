package net.workspace.workspace_backend.controller;


import net.workspace.workspace_backend.model.LoginRequest;
import net.workspace.workspace_backend.model.UserDetails;
import net.workspace.workspace_backend.model.UserResponse;
import net.workspace.workspace_backend.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserDetailsController {

    @Autowired
    UserDetailsService UserdetailsService;
@GetMapping("/UserDetails")
    public List<UserDetails> getAllUser(){

    return  UserdetailsService.getAlluser();

    }

    @PostMapping(value = "/Login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return UserdetailsService.login(loginRequest.getMailId(), loginRequest.getPassword());
    }

    @PostMapping("/CreateUser")
    public UserResponse createUser(@RequestBody UserDetails userdetails) {
        UserDetails savedUser = UserdetailsService.createUser(userdetails); // Save user to DB
        return new UserResponse("Success", savedUser);  // Return the user along with success status
    }
}
