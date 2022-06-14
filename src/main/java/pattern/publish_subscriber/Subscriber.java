package pattern.publish_subscriber;

public class Subscriber {

    public Subscriber(String ... topics) {
        for (String topic : topics) {
            ContentServer.getInstance().register(topic, this);
        }
    }

    public void receive(String topic, String message) {
        System.out.println(String.format("topic: %s\nreceive message:\n%s", topic, message));
    }
}
