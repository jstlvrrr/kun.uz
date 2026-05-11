package dasturlash.uz.controller;

import dasturlash.uz.dto.RegionDto;
import dasturlash.uz.dto.RegionShortInfoDTO;
import dasturlash.uz.service.RegionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region")
public class RegionController{
    @Autowired
    private RegionService regionService;

    @PostMapping("/admin/create")
    public ResponseEntity<RegionDto> create(@RequestBody RegionDto dto){
        return ResponseEntity.ok(regionService.create(dto));
    }

    @GetMapping("/public/get-by-lang")
    public ResponseEntity<List<RegionShortInfoDTO>> getByLang(@RequestParam("lang") String lang) {
        return ResponseEntity.ok(regionService.getByLanguage(lang));
    }
}
