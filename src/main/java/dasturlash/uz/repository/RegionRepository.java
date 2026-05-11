package dasturlash.uz.repository;

import dasturlash.uz.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RegionRepository extends JpaRepository<Region, Integer> {
    List<Region> findAllByVisibleTrueOrderByOrderNumber();
    Optional<Region> findByKey(String key);
}
