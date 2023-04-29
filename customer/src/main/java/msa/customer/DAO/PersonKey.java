package msa.customer.DAO;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@PrimaryKeyClass
public class PersonKey implements Serializable {

    @PrimaryKeyColumn(name = "first_name", type= PrimaryKeyType.PARTITIONED)
    private String firstName;

    @PrimaryKeyColumn(name = "date_of_birth", ordinal = 0)
    private LocalDateTime dateOfBirth;

    @PrimaryKeyColumn(name = "person_id", ordinal = 1, ordering = Ordering.DESCENDING)
    private UUID id;

    public PersonKey(String firstName, LocalDateTime dateOfBirth, UUID id) {
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.id = id;
    }
}
