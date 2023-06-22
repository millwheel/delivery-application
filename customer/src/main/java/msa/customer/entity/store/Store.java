package msa.customer.entity.store;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import msa.customer.entity.menu.Menu;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Document("store")
public class Store {
    @MongoId
    private String storeId;
    private String name;
    private FoodKindType foodKind;
    private String phoneNumber;
    private String address;
    private String addressDetail;
    @GeoSpatialIndexed(type=GeoSpatialIndexType.GEO_2DSPHERE)
    private Point location;
    private String introduction;
    private List<Menu> menuList;
    private boolean open;
}
