package lt.codeacademy.javau7.codeacademy.repositories;

import lt.codeacademy.javau7.codeacademy.entities.Date;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DateRepository extends JpaRepository<Date, Long> {
    List<Date> findByStuffId(Long stuffId);
    void deleteByUserId(Long userId);
}
