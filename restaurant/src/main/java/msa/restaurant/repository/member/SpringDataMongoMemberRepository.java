package msa.restaurant.repository.member;

import msa.restaurant.DAO.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoMemberRepository extends MongoRepository<Manager, String> {
}
