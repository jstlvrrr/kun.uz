package dasturlash.uz.service;

import dasturlash.uz.dto.SectionDto;
import dasturlash.uz.dto.SectionShortInfoDTO;
import dasturlash.uz.entity.Section;
import dasturlash.uz.repository.SectionRepository;
import dasturlash.uz.util.LanguageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionService {
    @Autowired
    private SectionRepository sectionRepository;

    public SectionDto create(SectionDto dto) {
        Section entity = new Section();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setKey(dto.getKey());

        sectionRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public Boolean update(Integer id, SectionDto dto) {
        Section entity = sectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Section not found"));

        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setKey(dto.getKey());

        sectionRepository.save(entity);
        return true;
    }

    public List<SectionShortInfoDTO> getByLanguage(String lang) {
        List<Section> entityList = sectionRepository.findAllByVisibleTrueOrderByOrderNumber();
        return entityList.stream().map(entity -> {
            SectionShortInfoDTO dto = new SectionShortInfoDTO();
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());
            dto.setName(LanguageUtil.pick(lang, entity.getNameUz(), entity.getNameRu(), entity.getNameEn()));
            return dto;
        }).collect(Collectors.toList());
    }
}
