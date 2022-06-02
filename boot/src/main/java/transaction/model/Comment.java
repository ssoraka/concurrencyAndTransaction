package transaction.model;


import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;

@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
public class Comment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private String text;
    @ManyToOne
    private Topic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    private LazyTopic lazyTopic;

    public Comment(){}

    public Comment(String text){
        this.text=text;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public LazyTopic getLazyTopic() {
        return lazyTopic;
    }

    public void setLazyTopic(LazyTopic lazyTopic) {
        this.lazyTopic = lazyTopic;
    }
}
