package msa.customer.repository.member;

import msa.customer.DAO.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoMemberRepository extends MongoRepository<Customer, String> {
}
