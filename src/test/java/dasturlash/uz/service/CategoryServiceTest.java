package dasturlash.uz.service;

import dasturlash.uz.dto.CategoryDto;
import dasturlash.uz.dto.CategoryShortInfoDTO;
import dasturlash.uz.entity.Category;
import dasturlash.uz.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryServiceTest {

    @Test
    void getAllUsesOrderedRepositoryMethod() {
        Category first = new Category();
        first.setId(1);
        first.setOrderNumber(1);
        first.setNameUz("Birinchi");
        first.setKey("first");

        Category second = new Category();
        second.setId(2);
        second.setOrderNumber(2);
        second.setNameUz("Ikkinchi");
        second.setKey("second");

        CategoryRepository repository = mock(CategoryRepository.class);
        when(repository.findAllByOrderByOrderNumber()).thenReturn(List.of(first, second));

        CategoryService service = new CategoryService();
        ReflectionTestUtils.setField(service, "categoryRepository", repository);

        List<CategoryDto> result = service.getAll();

        assertThat(result).extracting(CategoryDto::getId).containsExactly(1, 2);
        verify(repository).findAllByOrderByOrderNumber();
    }

    @Test
    void getByLanguageFallsBackToUzForUnknownOrNullLanguage() {
        Category category = new Category();
        category.setId(10);
        category.setKey("sport");
        category.setNameUz("Sport");
        category.setNameRu("Sport RU");
        category.setNameEn("Sport EN");

        CategoryRepository repository = mock(CategoryRepository.class);
        when(repository.findAllByVisibleTrueOrderByOrderNumber()).thenReturn(List.of(category));

        CategoryService service = new CategoryService();
        ReflectionTestUtils.setField(service, "categoryRepository", repository);

        List<CategoryShortInfoDTO> nullLangResult = service.getByLanguage(null);
        List<CategoryShortInfoDTO> unknownLangResult = service.getByLanguage("de");

        assertThat(nullLangResult).extracting(CategoryShortInfoDTO::getName).containsExactly("Sport");
        assertThat(unknownLangResult).extracting(CategoryShortInfoDTO::getName).containsExactly("Sport");
    }
}
