package com.example.AlumniInternProject.user;

import com.example.AlumniInternProject.exceptions.UserNotFoundException;
import com.example.AlumniInternProject.user.security.ALumniUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Homepage {
    @GetMapping("homepage2")
    public String helloWorld2(@AuthenticationPrincipal ALumniUserDetails loggedUser) {
        return "Hello  " + loggedUser.getUsername()  + " " + loggedUser.getUser().getId() + " You are at homepage2";

    }

    @GetMapping("homepage")
    public String helloWorld() throws UserNotFoundException {
        throw new UserNotFoundException("User not found");
    }

    @GetMapping("homepage3")
    public String helloWorld3() {
        return "Hello  You are at homepage";
    }
}
