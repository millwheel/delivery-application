package msa.customer.repository.member;

import msa.customer.entity.member.Customer;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

@EnableScan
public interface SpringDataDynamoMemberRepository extends CrudRepository<Customer, String> {

}
