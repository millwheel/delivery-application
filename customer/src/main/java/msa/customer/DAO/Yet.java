package msa.customer.DAO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Set;
import java.util.UUID;

@Table
@NoArgsConstructor
@AllArgsConstructor
public class Yet {

    @PrimaryKey
    private UUID id;
    private String firstName;
    private String lastName;
    private Set<String> specialities;

}
