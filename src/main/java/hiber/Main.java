package hiber;

import hiber.model.Comment;
import hiber.model.LazyTopic;
import hiber.model.Topic;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.query.Query;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        init();

//        eager();

//        topicFetch();

//        lazyTopicFetch();

//        cacheFirstLevel();

        readUncommited();
    }

    static void init() {
        System.out.println("_____________INIT_DB___________________");

        HibernateUtil.doInHibernate(session -> {
            for (int i = 0; i < 5; i++) {
                Topic topic = new Topic("topic" + i);
                LazyTopic lazyTopic = new LazyTopic("lazyTopic" + i);
                Comment comment = new Comment("comment" + i);
                comment.setTopic(topic);
                comment.setLazyTopic(lazyTopic);

                session.persist(comment);
                session.persist(topic);
                session.persist(lazyTopic);
            }
        });
    }

    static void eager() {
        System.out.println("_____________SELECT_DB_____EAGER______________");

        HibernateUtil.doInHibernate(session -> {
            Query<Comment> query = session.createQuery("from Comment c");

            List<Comment> comments = query.getResultList();
            comments.forEach(comment -> System.out.println(comment.getText() + " " + comment.getTopic().getTitle() + " " + comment.getLazyTopic().getTitle()));
        });
    }

    static void topicFetch() {
        System.out.println("_____________SELECT_DB____EAGER____FETCH______________");

        HibernateUtil.doInHibernate(session -> {
            Query<Comment> query = session.createQuery("from Comment c join fetch c.topic t", Comment.class);

            List<Comment> comments = query.getResultList();
            comments.forEach(comment -> System.out.println(comment.getText() + " " + comment.getTopic().getTitle() + " " + comment.getLazyTopic().getTitle()));
        });
    }


    static void lazyTopicFetch() {
        System.out.println("_____________SELECT_DB____EAGER____FETCH______________");

        HibernateUtil.doInHibernate(session -> {
            Query<Comment> query = session.createQuery("from Comment c join fetch c.topic t join fetch c.lazyTopic l", Comment.class);

            List<Comment> comments = query.getResultList();
            comments.forEach(comment -> System.out.println(comment.getText() + " " + comment.getTopic().getTitle() + " " + comment.getLazyTopic().getTitle()));
        });
    }

    static void cacheFirstLevel() {
        System.out.println("_____________SELECT_DB____from cache______________");


        Session session = HibernateUtil.getSessionFactory().openSession();
//        session.beginTransaction();

// Employee entity is fecthed very first time (It will be cached in both first-level and second-level cache)

        Comment comment = (Comment) session.load(Comment.class, 1l);
        System.out.println(comment.getText());

// Fetch the employee entity again
        comment = (Comment) session.load(Comment.class, 1l);
        System.out.println(comment.getText()); //It will return from the first-level

// Evict from first level cache (That will remove employee object from first-level cache)
//        session.evict(comment);

// Fetch same entity again using same session
        comment = (Comment) session.load(Comment.class, 1l);
        System.out.println(comment.getText()); //It will return from the second-level

// Fetch same entity again using another session
        Session anotherSession = HibernateUtil.getSessionFactory().openSession();
        comment = (Comment) anotherSession.load(Comment.class, 1l);
        System.out.println(comment.getText());//It will return from the second-level

        System.out.println("Response from the first-level : " + HibernateUtil.getSessionFactory().getStatistics().getEntityFetchCount());
        System.out.println("Response from the second-level : " + HibernateUtil.getSessionFactory().getStatistics().getSecondLevelCacheHitCount());

    }

    static void readUncommited() {
        new Thread(() -> {
            System.out.println("_____________START____LONG_COMMIT______________");
            HibernateUtil.doInHibernate(session -> {
                Comment comment = new Comment("__comment__");
                session.persist(comment);
//                session.
                try {
                    Thread.sleep(1000l);
                } catch (Exception e) {}
            });
            System.out.println("_____________END______LONG_COMMIT______________");
        }).start();

        try {
            Thread.sleep(100l);
        } catch (Exception e) {}

        System.out.println("_____________START____READ______________");
        HibernateUtil.doInHibernate(session -> {
            Query<Comment> query = session.createQuery("from Comment c where c.text = '__comment__'", Comment.class);

            List<Comment> comments = query.getResultList();
            comments.forEach(comment -> System.out.println(comment.getText()));
        });
        System.out.println("_____________END______READ______________");
    }
}
