package pl.turlap.prawko.services;

import org.springframework.http.ResponseEntity;

public interface TestService {

    ResponseEntity<String> generateTest(Long userId);

}
