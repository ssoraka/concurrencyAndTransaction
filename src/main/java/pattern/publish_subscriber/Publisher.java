package pattern.publish_subscriber;

public class Publisher {
    private String topic;

    public Publisher(String topic) {
        this.topic = topic;
    }

    public void send(String message) {
        ContentServer.getInstance().sendMessage(topic, message);
    }
}
