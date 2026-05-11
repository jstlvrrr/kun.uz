package dasturlash.uz.service;

import dasturlash.uz.dto.RegionDto;
import dasturlash.uz.dto.RegionShortInfoDTO;
import dasturlash.uz.entity.Region;
import dasturlash.uz.repository.RegionRepository;
import dasturlash.uz.util.LanguageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    public RegionDto create(RegionDto dto) {
        Region entity = new Region();
        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setKey(dto.getKey());

        regionRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public List<RegionShortInfoDTO> getByLanguage(String lang) {
        List<Region> entityList = regionRepository.findAllByVisibleTrueOrderByOrderNumber();
        List<RegionShortInfoDTO> dtoList = new ArrayList<>();

        for (Region entity : entityList) {
            RegionShortInfoDTO dto = new RegionShortInfoDTO();
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());

            dto.setName(LanguageUtil.pick(lang, entity.getNameUz(), entity.getNameRu(), entity.getNameEn()));
            dtoList.add(dto);
        }
        return dtoList;
    }
}
