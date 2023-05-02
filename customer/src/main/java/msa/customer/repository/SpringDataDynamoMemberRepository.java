package msa.customer.repository;

import msa.customer.DAO.Member;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;

import java.util.Optional;

public interface SpringDataDynamoMemberRepository extends DynamoDBCrudRepository<Member, String> {

    Optional<Member> findByEmail(String email);
}
