package msa.customer.repository.member;

import msa.customer.DAO.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoMemberRepository extends MongoRepository<Member, String> {
}
