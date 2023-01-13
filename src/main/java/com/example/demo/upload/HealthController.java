package com.example.demo.upload;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@Slf4j
public class HealthController {

    @GetMapping("/app/status")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN') or hasAnyAuthority('ADMINISTRATOR')")
    public String health(HttpServletRequest request) throws IOException, InterruptedException {
        log.info("invoked api in health");

        String serverAddress = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        HttpRequest httpRequest = HttpRequest
                .newBuilder(URI.create(serverAddress + "/actuator/health"))
                .GET()
                .setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                .setHeader("Accept-Encoding", "gzip, deflate, br")
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(httpRequest, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
