package msa.restaurant.repository.member;

import msa.restaurant.DAO.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpringDataMongoMemberRepository extends MongoRepository<Member, String> {
}
