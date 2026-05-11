package dasturlash.uz.service;

import dasturlash.uz.dto.ProfileDto;
import dasturlash.uz.entity.Profile;
import dasturlash.uz.enums.ProfileStatus;
import dasturlash.uz.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ProfileDto create(ProfileDto dto) {
        profileRepository.findByUsername(dto.getUsername()).ifPresent(p -> {
            throw new RuntimeException("Username already exists");
        });

        Profile entity = new Profile();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setUsername(dto.getUsername());

        entity.setPassword(passwordEncoder.encode(dto.getPassword()));

        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setCreatedDate(LocalDateTime.now());

        profileRepository.save(entity);
        dto.setId(entity.getId());
        dto.setPassword(null); // Xavfsizlik uchun parolni qaytarmaymiz
        return dto;
    }

    public Boolean updateDetail(Integer id, ProfileDto dto) {
        Profile entity = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());

        profileRepository.save(entity);
        return true;
    }

    public ProfileDto getById(Integer id) {
        Profile entity = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        ProfileDto dto = new ProfileDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setUsername(entity.getUsername());
        dto.setStatus(entity.getStatus());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public Boolean updatePassword(Integer id, String newPassword) {
        Profile entity = profileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        entity.setPassword(passwordEncoder.encode(newPassword));
        profileRepository.save(entity);
        return true;
    }
}
