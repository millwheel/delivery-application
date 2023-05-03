package msa.customer.repository;

import msa.customer.DAO.Member;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface SpringDataDynamoMemberRepository extends CrudRepository<Member, String> {

}
