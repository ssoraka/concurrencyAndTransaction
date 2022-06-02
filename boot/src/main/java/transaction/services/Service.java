package transaction.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import transaction.model.Comment;
import transaction.repository.CommentRepository;

import java.util.Collection;
import java.util.Iterator;

@Component
public class Service {

    @Autowired
    private CommentRepository repository;

    @Autowired
    private Service self;


    @Transactional(propagation = Propagation.REQUIRED)
    public void changeAndSave(Comment comment) {
        System.out.println("changeAndSave start");
//        repository.save(comment);
        comment.setText(comment.getText() + "2");
        self.add(comment);
        System.out.println("changeAndSave end");
        throw new RuntimeException("qwerty");
    }

//    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Transactional
//    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void add(Comment comment) {
        System.out.println("add start");
        repository.save(comment);
        System.out.println("add end");
    }

    @Transactional
    public void addAll(Collection<Comment> comments) {
        repository.saveAll(comments);
    }

//    @Transactional(readOnly = true)
    public Iterator<Comment> listAll() {
        return repository.findAll().iterator();
    }

    @Transactional
    public void delete(Comment comment) {
        repository.delete(comment);
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public Iterator<Comment> readUncommited() {
        return repository.findAll().iterator();
    }


    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
    public void longSave(Comment comment) {
        System.out.println("add start");
        repository.save(comment);
        System.out.println("wait for commit");
        self.readUncommited().forEachRemaining(c -> System.out.println(c.getText()));
        try {
//            Thread.sleep(1000l);
        } catch (Exception e) {}
        System.out.println("add end");
    }
}
