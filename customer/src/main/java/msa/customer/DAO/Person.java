package msa.customer.DAO;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("people_by_first_name")
public class Person {

    @PrimaryKey
    private PersonKey key;

    @Column("last_name")
    private String lastName;

    @Column
    private double salary;

    public Person(PersonKey key, String lastName, double salary) {
        this.key = key;
        this.lastName = lastName;
        this.salary = salary;
    }
}
