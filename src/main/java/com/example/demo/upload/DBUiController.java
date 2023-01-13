package com.example.demo.upload;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.URI;

@Controller
@Slf4j
public class DBUiController {

    @GetMapping("/app/db")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasAnyAuthority('ADMINISTRATOR')")
    public ResponseEntity databaseUi(HttpServletRequest request) {
        log.info("invoked databaseUi in Health");

        String serverAddress = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(serverAddress + "/h2-console"));
        return new ResponseEntity(headers, HttpStatus.MOVED_PERMANENTLY);
    }

}
