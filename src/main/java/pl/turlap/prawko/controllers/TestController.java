package pl.turlap.prawko.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.turlap.prawko.dto.TestDto;
import pl.turlap.prawko.services.TestService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/tests")
@AllArgsConstructor
public class TestController {

    private final TestService testService;

    @PostMapping(path = "/new")
    public ResponseEntity<Map<String, String>> newExam(@RequestParam(name = "userId") Long userId) {
        testService.generateTest(userId);
        return ResponseEntity.ok(Map.of("message", "Exam for user with id '" + userId + "' created."));
    }

    @GetMapping(path = "/{testId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public TestDto findTest(@PathVariable(name = "testId") Long testId) {
        return testService.getTestDto(testId);
    }

    @GetMapping(path = "/all/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TestDto> findAllTestsOfUser(@PathVariable(name = "userId") Long userId) {
        return testService.findAllByUserId(userId);
    }
}