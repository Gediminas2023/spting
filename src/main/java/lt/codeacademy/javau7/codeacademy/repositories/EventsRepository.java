package lt.codeacademy.javau7.codeacademy.repositories;

import lt.codeacademy.javau7.codeacademy.entities.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventsRepository extends JpaRepository<Events, Long> {
    List<Events> findByStuffId(Long stuffId);
}
