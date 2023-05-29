package msa.restaurant.controller;

import com.amazonaws.services.sqs.model.SendMessageResult;
import msa.restaurant.service.AwsSqsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurant")
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
