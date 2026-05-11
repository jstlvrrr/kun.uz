package dasturlash.uz.repository;

import dasturlash.uz.entity.Section;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SectionRepository extends JpaRepository<Section, Integer> {
    List<Section> findAllByVisibleTrueOrderByOrderNumber();
    Optional<Section> findByKey(String key);
}
