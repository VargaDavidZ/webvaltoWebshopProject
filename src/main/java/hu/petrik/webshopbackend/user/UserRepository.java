package hu.petrik.webshopbackend.user;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    @Modifying
    @Transactional
    @Query(value = "UPDATE users_seq SET next_val = 1", nativeQuery = true)
    void resetSequence();

    Optional<User> findByEmail(String email);
}
