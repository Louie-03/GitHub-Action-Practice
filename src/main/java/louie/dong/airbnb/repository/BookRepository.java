package louie.dong.airbnb.repository;

import louie.dong.airbnb.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

}
