package ms.studies.bookmicrosservice.repository;

import ms.studies.bookmicrosservice.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {

}
