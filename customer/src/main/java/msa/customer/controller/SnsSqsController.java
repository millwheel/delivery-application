package msa.customer.controller;

import msa.customer.service.AwsSnsService;
import msa.customer.service.AwsSqsService;
import org.springframework.cloud.aws.messaging.listener.Acknowledgment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class SnsSqsController {

    private final AwsSnsService awsSnsService;
    private final AwsSqsService awsSqsService;

    public SnsSqsController(AwsSnsService awsSnsService, AwsSqsService awsSqsService) {
        this.awsSnsService = awsSnsService;
        this.awsSqsService = awsSqsService;
    }

    @PostMapping("/pub")
    public void pub() {
        awsSnsService.publish("test message from spring");
    }

    @GetMapping("/sub")
    public void sub() {
        awsSqsService.listen("Delivery_restaurant_queue");
    }
}
