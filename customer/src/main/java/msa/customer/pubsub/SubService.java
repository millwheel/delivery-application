package msa.customer.pubsub;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Service;
import test.redis.entity.ChatMessage;

import java.io.IOException;

@Slf4j
@Service
public class SubService implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ChatMessage chatMessage = objectMapper.readValue(message.getBody(), ChatMessage.class);
            log.info("message sender={}", chatMessage.getSender());
            log.info("message context={}", chatMessage.getContext());
        } catch (IOException e) {
            log.error("error occurred={}", e.getMessage());
        }
    }
}
