package msa.restaurant.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@Document("manager")
public class Manager {

    @MongoId
    private String managerId;
    private String name;
    private String email;
    private String phoneNumber;
    private List<StorePartInfo> storePartInfoList;

}
