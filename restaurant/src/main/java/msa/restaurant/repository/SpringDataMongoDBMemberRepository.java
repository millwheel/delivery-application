package msa.restaurant.repository;

import msa.restaurant.DAO.Member;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpringDataMongoDBMemberRepository extends MongoRepository<Member, Long> {
     Optional<Member> findByEmail(String email);
}
