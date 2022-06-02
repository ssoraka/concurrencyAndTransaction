package transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import transaction.model.Comment;
import transaction.services.Service;

//@EnableAutoConfiguration
//@EnableTransactionManagement
@SpringBootApplication
public class App implements CommandLineRunner {

    @Autowired
    Service service;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        isolationTest();
    }



    void isolationTest() {
        Comment comment = new Comment("comment");
        comment.setText("text 1");


//        service.add(comment);
        service.longSave(comment);


//        new Thread(() -> service.longSave(comment)).start();
//
//
//        try {
//            Thread.sleep(100L);
//
//            System.out.println("read start");
//            service.readUncommited().forEachRemaining(c -> System.out.println(c.getText()));
//            System.out.println("read end");
//            Thread.sleep(900L);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        service.listAll().forEachRemaining(c -> System.out.println(c.getText()));
    }



    void propagationTest() {
        Comment comment = new Comment("comment");
        comment.setText("text 1");


        try { //propagation test
            System.out.println("transaction start");
            service.changeAndSave(comment);
            System.out.println("transaction end");

//            comment = new Comment();
//            comment.setText("text 2");
//            service.changeAndSave(comment);
        } catch (Exception e) {
            e.printStackTrace();
        }
        service.listAll().forEachRemaining(c -> System.out.println(c.getText()));
    }

}
