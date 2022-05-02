package com.fidenciodev.sqs;

import java.util.Date;
import java.util.List;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class App {

    private static final String QUEUE_NAME = "testQueue" + new Date().getTime();

    public static void main(String[] args) {

        final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();

        try {
            CreateQueueResult create_result = sqs.createQueue(QUEUE_NAME);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals("QueueAlreadyExists")) {
                throw e;
            }
        }

        String queueUrl = sqs.getQueueUrl(QUEUE_NAME).getQueueUrl();

        SendMessageRequest send_msg_request = new SendMessageRequest()
                .withQueueUrl(queueUrl)
                .withMessageBody("Mensagem teste")
                .withDelaySeconds(5);
        sqs.sendMessage(send_msg_request);

        // Send multiple messages to the queue
        SendMessageBatchRequest send_batch_request = new SendMessageBatchRequest()
                .withQueueUrl(queueUrl)
                .withEntries(
                        new SendMessageBatchRequestEntry(
                                "msg_1", "mensagem multipla teste 01"),
                        new SendMessageBatchRequestEntry(
                                "msg_2", "mensagem multipla teste 02")
                                .withDelaySeconds(10));
        sqs.sendMessageBatch(send_batch_request);

        List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();

        System.out.println(messages);

        for (Message m : messages) {
            sqs.deleteMessage(queueUrl, m.getReceiptHandle());
        }


    }

}
