package dasturlash.uz.repository;

import dasturlash.uz.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByOrderByOrderNumber();
    List<Category> findAllByVisibleTrueOrderByOrderNumber();
    Optional<Category> findByKey(String key);

}
