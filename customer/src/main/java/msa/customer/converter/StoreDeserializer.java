package msa.customer.converter;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import msa.customer.dto.store.StoreSqsDto;
import msa.customer.entity.FoodKindType;
import org.springframework.data.geo.Point;

import java.io.IOException;

public class StoreDeserializer extends StdDeserializer {

    public StoreDeserializer(){
        this(null);
    }
    protected StoreDeserializer(Class vc) {
        super(vc);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = p.getCodec().readTree(p);
        String storeId = node.get("storeId").asText();
        String name = node.get("name").asText();
        FoodKindType foodKind = FoodKindType.valueOf(node.get("foodKind").asText());
        String phoneNumber = node.get("phoneNumber").asText();
        String address = node.get("address").asText();
        String addressDetail = node.get("addressDetail").asText();
        double x = node.get("location").get("x").asDouble();
        double y = node.get("location").get("y").asDouble();
        Point location = new Point(x, y);
        String introduction = node.get("introduction").asText();
        return new StoreSqsDto(storeId, name, foodKind, phoneNumber, address, addressDetail, location, introduction);
    }
}
