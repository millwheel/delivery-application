package msa.customer.repository;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import msa.customer.DAO.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AwsDynamoDbRepository implements MemberRepository{
    private final AmazonDynamoDBClient amazonDynamoDBClient;

    @Autowired
    public AwsDynamoDbRepository(AmazonDynamoDBClient amazonDynamoDBClient) {
        this.amazonDynamoDBClient = amazonDynamoDBClient;
    }

    @Override
    public void make(Member member) {
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
        mapper.save(member);
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
        return Optional.ofNullable(mapper.load(Member.class, email));
    }
}
