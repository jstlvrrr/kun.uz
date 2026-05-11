package dasturlash.uz.service;

import dasturlash.uz.dto.RegionDto;
import dasturlash.uz.dto.RegionShortInfoDTO;
import dasturlash.uz.entity.Region;
import dasturlash.uz.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegionService {
    @Autowired
    private RegionRepository regionRepository;

    // Create (ADMIN)
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

    // Get List By Language [cite: 27]
    public List<RegionShortInfoDTO> getByLanguage(String lang) {
        List<Region> entityList = regionRepository.findAllByVisibleTrueOrderByOrderNumber();
        List<RegionShortInfoDTO> dtoList = new ArrayList<>();

        for (Region entity : entityList) {
            RegionShortInfoDTO dto = new RegionShortInfoDTO();
            dto.setId(entity.getId());
            dto.setKey(entity.getKey());

            // Tilga qarab nomni tanlash
            switch (lang.toLowerCase()) {
                case "uz" -> dto.setName(entity.getNameUz());
                case "ru" -> dto.setName(entity.getNameRu());
                case "en" -> dto.setName(entity.getNameEn());
                default -> dto.setName(entity.getNameUz());
            }
            dtoList.add(dto);
        }
        return dtoList;
    }
}
