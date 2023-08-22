package msa.customer.deserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import msa.customer.dto.store.StoreSqsDto;
import msa.customer.entity.store.FoodKind;
import org.assertj.core.api.Assertions;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.geo.Point;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class StoreDeserializerTest {


    @Test
    public void deserializerTest() throws JsonProcessingException {
        // given
        // create data and serialization
        Point location = new Point(35.17, 15.36);
        StoreSqsDto sendingStoreSqsDto = new StoreSqsDto("storeId123", "good pizza", FoodKind.PIZZA, "0100001010", "somewhere", "room102", location, "Hello. We are good pizza.", false);
        JSONObject jsonObject = new JSONObject();
        JSONObject sendingData = new JSONObject(sendingStoreSqsDto);
        jsonObject.put("dataType", "store");
        jsonObject.put("method", "create");
        jsonObject.put("data", sendingData);

        // when
        // deserialization
        String receivedData = jsonObject.get("data").toString();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.addMixIn(Point.class, PointMixin.class);
        StoreSqsDto receivedStoreSqsDto = objectMapper.readValue(receivedData, StoreSqsDto.class);

        // then
        assertThat(receivedStoreSqsDto.getStoreId()).isEqualTo(sendingStoreSqsDto.getStoreId());
        assertThat(receivedStoreSqsDto.getLocation()).isEqualTo(sendingStoreSqsDto.getLocation());
    }
}
