package msa.restaurant.repository.member;

import msa.restaurant.entity.member.Manager;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoMemberRepository extends MongoRepository<Manager, String> {
}
