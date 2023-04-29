package msa.customer.repository;

import msa.customer.DAO.Person;
import msa.customer.DAO.PersonKey;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PersonRepository extends CassandraRepository<Person, PersonKey> {

    List<Person> findByKeyFirstName(final String firstName);

    List<Person> findByKeyFirstNameAndKeyDateOfBirthGreaterThan(final String firstName, final LocalDateTime dateOfBirth);

}
