package msa.rider.repository.member;

import msa.rider.entity.member.Rider;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataMongoMemberRepository extends MongoRepository<Rider, String> {

}
