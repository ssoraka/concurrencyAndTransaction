package hiber;

import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.sql.Connection;
import java.util.function.Consumer;
import java.util.function.Function;

public class HibernateUtil {
    // Private constructor; Class cannot be initialized
    private HibernateUtil() {
    }

    private static SessionFactory sessionFactory;

    // create sessionFactory only once
    static {
        // A SessionFactory is set up once for an application!
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .loadProperties(new File("/Users/ssoraka/IdeaProjects/del/src/main/resources/application.properties"))
                .configure(new File("/Users/ssoraka/IdeaProjects/del/src/main/resources/hibernate.cfg.xml"))
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            // The registry would be destroyed by the SessionFactory, but we had trouble building
            // the SessionFactory so destroy it manually.
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void doInHibernate(Consumer<Session> callable) {
        Transaction txn = null;
        try (Session session = getSessionFactory().openSession()) {
            session.setCacheMode(CacheMode.NORMAL);
            txn = session.beginTransaction();

            callable.accept(session);

            txn.commit();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static <T> T doInHibernate(Function<Session, T> callable) {
        T result = null;
        Transaction txn = null;
        try (Session session = getSessionFactory().openSession()) {
            txn = session.beginTransaction();

            result = callable.apply(session);

            txn.commit();
        } catch (Throwable t) {
            txn.rollback();
            t.printStackTrace();
        }
        return result;
    }

}