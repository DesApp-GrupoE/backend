package app.controller;

import app.model.UserTest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public UserTest getUserTest() {
        return new UserTest();
    }

    @PostMapping
    public UserTest createUser(@RequestBody UserTest userTest) {
        userTest.setPassword("password_hashed");
        return userTest;
    }
}
