package ms.studies.personmicrosservice.repository;

import ms.studies.personmicrosservice.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

public interface PersonRepository extends JpaRepository<Person, UUID> {

    Optional<Person> findByDocument(String document);

    @Transactional
    @Modifying
    @Query("UPDATE Person p SET p.rentBook = ?2 WHERE p.document = ?1")
    int updateUserNameById(String document, Integer book);
}
