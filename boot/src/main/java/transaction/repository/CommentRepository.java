package transaction.repository;

import org.springframework.data.repository.CrudRepository;
import transaction.model.Comment;

public interface CommentRepository  extends CrudRepository<Comment, Long> {
}
