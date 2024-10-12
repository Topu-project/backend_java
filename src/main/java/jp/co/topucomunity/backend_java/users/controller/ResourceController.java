package jp.co.topucomunity.backend_java.users.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResourceController {

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/favicon.ico")
    public void favicon() {
        // Do nothing
    }

}
