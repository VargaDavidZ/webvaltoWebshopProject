package hu.petrik.webshopbackend.items;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Modifying
    @Transactional
    @Query(value = "UPDATE items_seq SET next_val = 1", nativeQuery = true)
    void resetSequence();

    Optional<Item> findById(Long id);
}
