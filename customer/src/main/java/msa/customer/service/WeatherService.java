package msa.customer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class WeatherService {

    private final String uri = "https://api.openweathermap.org/data/3.0/onecall?lat=39.31&lon=-74.5&exclude=minutely,hourly,daily&appid=f1519305e3ce3603284fbde37b5c5731";

    public String getWeather(){
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri, String.class);
        log.info(result);
        return result;
    }

}
