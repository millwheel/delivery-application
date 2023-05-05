package msa.customer.repository;

import msa.customer.DAO.Member;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@EnableScan
public interface SpringDataDynamoMemberRepository extends CrudRepository<Member, String> {

    Optional<String> findByEmail(String email);
    long countByLastname(String lastname);

    List<Member> removeByFirstname(String firstname);

    boolean findByAgeLessThanEqual(int age);

}
