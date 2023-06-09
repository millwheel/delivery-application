package msa.customer.repository.member;

import msa.customer.entity.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoMemberRepository extends MongoRepository<Customer, String> {
}
