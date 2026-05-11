package dasturlash.uz.service;

import dasturlash.uz.dto.CategoryDto;
import dasturlash.uz.dto.CategoryShortInfoDTO;
import dasturlash.uz.entity.Category;
import dasturlash.uz.repository.CategoryRepository;
import dasturlash.uz.util.LanguageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService{
    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryDto create(CategoryDto dto) {
        Category entity = new Category();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setKey(dto.getKey());

        categoryRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean update(Integer id, CategoryDto dto) {
        Category entity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setKey(dto.getKey());

        categoryRepository.save(entity);
        return true;
    }

    public Boolean delete(Integer id) {
        Category entity = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        entity.setVisible(false); // Soft delete
        categoryRepository.save(entity);
        return true;
    }

    public List<CategoryDto> getAll() {
        return categoryRepository.findAllByOrderByOrderNumber().stream().map(entity -> {
            CategoryDto dto = new CategoryDto();
            dto.setId(entity.getId());
            dto.setNameUz(entity.getNameUz());
            dto.setNameRu(entity.getNameRu());
            dto.setNameEn(entity.getNameEn());
            dto.setOrderNumber(entity.getOrderNumber());
            dto.setKey(entity.getKey());
            return dto;
        }).collect(Collectors.toList());
    }

    public List<CategoryShortInfoDTO> getByLanguage(String lang) {
        List<Category> entityList = categoryRepository.findAllByVisibleTrueOrderByOrderNumber();
        List<CategoryShortInfoDTO> dtoList = new ArrayList<>();

        for (Category entity : entityList) {
            CategoryShortInfoDTO dto = new CategoryShortInfoDTO();
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());
            dto.setName(LanguageUtil.pick(lang, entity.getNameUz(), entity.getNameRu(), entity.getNameEn()));
            dtoList.add(dto);
        }
        return dtoList;
    }
}
