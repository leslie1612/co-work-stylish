package tw.appworks.school.example.stylish.service.amazon;

import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import org.springframework.stereotype.Service;

@Service
public class SnsService {

    private final AmazonSnsClient amazonSnsClient;

    private String TOPIC_ARN = "arn:aws:sns:us-east-1:975049923649:co-work-sns";

    public SnsService (AmazonSnsClient amazonSnsClient) {
        this.amazonSnsClient = amazonSnsClient;
    }

    public void pubTopic(String message) {

        try {

            PublishRequest request = new PublishRequest().withMessage(message).withTopicArn(TOPIC_ARN);

            PublishResult result = amazonSnsClient.getClient().publish(request);

            System.out.println(
                    result.getMessageId()
                            + " Message sent. Status is "
                            + result.getSdkHttpMetadata().getHttpStatusCode());

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
