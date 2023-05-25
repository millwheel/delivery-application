package msa.customer.controller;

import msa.customer.service.AwsSqsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
public class SnsSqsController {

    private final AwsSqsService awsSqsService;

    public SnsSqsController(AwsSqsService awsSqsService) {
        this.awsSqsService = awsSqsService;
    }

    @GetMapping("/sub")
    public void sub() {
        awsSqsService.listen("Delivery_restaurant_queue");
    }
}
