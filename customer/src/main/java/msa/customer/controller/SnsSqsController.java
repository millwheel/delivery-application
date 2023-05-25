package msa.customer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import msa.customer.DTO.EcmDto;
import msa.customer.service.AwsSqsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class SnsSqsController {

    private final AwsSqsService awsSqsService;

    public SnsSqsController(AwsSqsService awsSqsService) {
        this.awsSqsService = awsSqsService;
    }

    @PostMapping("/send")
    public String send(@RequestBody EcmDto message) throws JsonProcessingException {
        awsSqsService.sendMessage(message);
        return "OK";
    }

    @GetMapping("/sub")
    public void sub() {
        awsSqsService.listen("Delivery_restaurant_queue");
    }
}
