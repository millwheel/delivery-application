package msa.rider.repository;

import msa.rider.DAO.Rider;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SpringDataMongoMemberRepository extends MongoRepository<Rider, String> {

}
