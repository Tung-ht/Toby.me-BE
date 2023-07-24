package tunght.toby.notification.domain.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class FollowNotiConsumer {

    @KafkaListener(topics = "${spring.kafka.follow-noti-topic}", groupId = "${spring.kafka.group-noti-id}", containerFactory = "notiListenerContainerFactory")
    public void receiveMessage(@Payload String notification) {

    }
}
