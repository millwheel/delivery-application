package msa.customer.service;

import com.amazonaws.SdkBaseException;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import msa.customer.DAO.Lyric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AwsDynamoDbService {
    private final AmazonDynamoDBClient amazonDynamoDBClient;

    @Autowired
    public AwsDynamoDbService(AmazonDynamoDBClient amazonDynamoDBClient) {
        this.amazonDynamoDBClient = amazonDynamoDBClient;
    }

    public boolean createItem(String id, String _lyric) throws SdkBaseException {
        Lyric lyric = new Lyric(id, _lyric);
        DynamoDBMapper mapper = new DynamoDBMapper(amazonDynamoDBClient);
        mapper.save(lyric);
        return true;
    }
}
