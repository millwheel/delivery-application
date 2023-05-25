package msa.customer.controller;

import com.amazonaws.services.sqs.model.SendMessageResult;
import msa.customer.service.AwsSqsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class SqsController {

    private final AwsSqsService awsSqsService;

    public SqsController(AwsSqsService awsSqsService) {
        this.awsSqsService = awsSqsService;
    }

    @PostMapping("/send")
    public SendMessageResult send(@RequestBody String message) {
        return awsSqsService.send(message);
    }

}
