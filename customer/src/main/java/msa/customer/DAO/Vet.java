package msa.customer.DAO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Set;
import java.util.UUID;

@Table
@Getter
@Setter
@AllArgsConstructor
public class Vet {

    @PrimaryKey
    private UUID id;
    private String firstName;
    private String lastName;
    private Set<String> specialties;
}
