package msa.rider.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/rider")
@RestController
public class HealthCheckController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String healthCheck(){
        return "Rider server is activated successfully";
    }

}