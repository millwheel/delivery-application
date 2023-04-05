package msa.customer.controller;

import lombok.extern.slf4j.Slf4j;
import msa.customer.DTO.LoginForm;
import msa.customer.DTO.LyricForm;
import msa.customer.service.AwsDynamoDbService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LyricController {

    private final AwsDynamoDbService awsDynamoDbService;

    public LyricController(AwsDynamoDbService awsDynamoDbService) {
        this.awsDynamoDbService = awsDynamoDbService;
    }

    @PostMapping("/lyric")
    public String save(@RequestBody LyricForm data){
        boolean result = awsDynamoDbService.createItem(data.getId(), data.getLyrics());
        if(!result){
            return "failed.";
        }
        log.info("success");
        return "save succeeded";
    }

}
