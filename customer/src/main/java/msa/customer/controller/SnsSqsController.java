package msa.customer.controller;

import msa.customer.service.AwsSnsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/customer")
public class SnsSqsController {

    private final AwsSnsService awsSnsService;

    public SnsSqsController(AwsSnsService awsSnsService) {
        this.awsSnsService = awsSnsService;
    }

    @PostMapping("/pub")
    public void pub() {
        awsSnsService.publish("test message from spring");
    }
}
