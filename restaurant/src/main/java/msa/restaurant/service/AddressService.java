package msa.restaurant.service;

import lombok.extern.slf4j.Slf4j;
import msa.restaurant.DAO.Coordinates;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
public class AddressService {
    private final String uri = "https://dapi.kakao.com/v2/local/search/address.json";

    @Value("${kakao.local.key}")
    private String kakaoLocalKey;

    public Coordinates getCoordinate(String address){
        RestTemplate restTemplate = new RestTemplate();

        String apiKey = "KakaoAK " + kakaoLocalKey;
        
        // 요청 헤더에 만들기, Authorization 헤더 설정하기
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization", apiKey);
        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);

        UriComponents uriComponents = UriComponentsBuilder
                .fromHttpUrl(uri)
                .queryParam("query",address)
                .build();

        ResponseEntity<String> response = restTemplate.exchange(uriComponents.toString(), HttpMethod.GET, entity, String.class);

        // API Response로부터 body 뽑아내기
        String body = response.getBody();
        JSONObject json = new JSONObject(body);
        // body에서 좌표 뽑아내기
        JSONArray documents = json.getJSONArray("documents");
        String x = documents.getJSONObject(0).getString("x");
        String y = documents.getJSONObject(0).getString("y");

        return new Coordinates(x, y);
    }

}
