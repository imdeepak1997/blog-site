package com.example.users.controller;

import com.example.users.feign.BlogsInterface;
import com.example.users.model.Blogs;
import com.example.users.model.User;
import com.example.users.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    BlogsInterface blogsInterface;


    @PostMapping("/register")
    public ResponseEntity register( @RequestBody User user) {
        String error = userService.validateUser(user);
        if (error.isEmpty()) {
            String resp = userService.saveUser(user);
            return new ResponseEntity<String>(resp, HttpStatus.OK);
        }
       return new ResponseEntity<>(error, HttpStatus.OK);
    }

    @PostMapping("/login")
    public String login( @RequestBody User user) {
        return userService.verifyUser(user);
    }

    @GetMapping("/getall")
    public List<User> getAllUsers() {
        System.out.println("=-====yaha aaa gya=====");
        return userService.getAllUsers();
    }

    @DeleteMapping("delete/{blogname}")
    public List<Blogs> deleteByBlogname(@PathVariable String blogname) {
        List<Blogs> blogs=  blogsInterface.getAll().getBody();
        return blogs;
    }

    @PostMapping("/blogs/add/{authorname}")
    public ResponseEntity<String> addBlog(@RequestBody Blogs blogs, @PathVariable String authorname) {
        blogs.setAuthor(authorname);
        return blogsInterface.create(blogs);
    }

    @GetMapping("/{author}")
    public ResponseEntity<List<Blogs>> getUserById(@PathVariable String author) {
        return   blogsInterface.searchBlogByAuthor(author);
    }

}
