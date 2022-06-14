package pattern.publish_subscriber;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;

public class ContentServer {
    Map<String, Collection<Subscriber>> topicSubscriber = new ConcurrentHashMap<>();

    private static ContentServer server = new ContentServer();

    private ContentServer() {
    }

    public static ContentServer getInstance() {
        return server;
    }

    public void sendMessage(String topic, String message) {
        topicSubscriber.getOrDefault(topic, new ArrayList<>())
                .forEach(s -> s.receive(topic, message));
    }

    public void register(String topic, Subscriber subscriber) {
        Collection<Subscriber> subscribers = topicSubscriber.get(topic);
        if (subscribers == null) {
            subscribers = topicSubscriber.put(topic, new SynchronousQueue<>());
        }
        subscribers.add(subscriber);
    }
}
