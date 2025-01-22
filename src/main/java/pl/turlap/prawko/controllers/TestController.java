package pl.turlap.prawko.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.turlap.prawko.services.TestService;

@Controller
@RequestMapping(path = "/tests")
@AllArgsConstructor
public class TestController {
    private final TestService testService;

    @GetMapping(path = "/new/{userId}")
    public ResponseEntity<String> newTest(@PathVariable(name = "userId") Long userId) {
        return testService.generateTest(userId);
    }

}
