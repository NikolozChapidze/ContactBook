package ge.gov.dga.contactbook.repository;

import ge.gov.dga.contactbook.model.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByIdAndUserId(Long id, Long userId);
}
