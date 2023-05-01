package msa.customer.DAO;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("member")
@Getter
@Setter
public class Member {

    @PrimaryKey
    private Long id;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;

    public Member() {

    }
}
