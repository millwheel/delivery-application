package msa.restaurant.deserializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public abstract class PointMixin {
    @JsonCreator
    public PointMixin(@JsonProperty("x") double x, @JsonProperty("y") double y) {}
}
