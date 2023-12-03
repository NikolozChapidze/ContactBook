package ge.gov.dga.contactbook.repository;

import ge.gov.dga.contactbook.model.entity.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByIdAndUserId(Long id, Long userId);

    Page<Contact> findAllByUser_Id(Long userId, Pageable pageable);

    @Query("SELECT c FROM Contact c WHERE c.user.id = :userId AND (" +
            "LOWER(c.person) LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
            "LOWER(c.number) LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
            "LOWER(c.email) LIKE LOWER(CONCAT('%',:keyword,'%')) OR " +
            "LOWER(c.address) LIKE LOWER(CONCAT('%',:keyword,'%')))")
    List<Contact> findByUserIdAndKeyword(@Param("userId") Long userId, @Param("keyword") String keyword);
}
