package dasturlash.uz.service;

import dasturlash.uz.dto.ProfileDto;
import dasturlash.uz.entity.Profile;
import dasturlash.uz.repository.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProfileServiceTest {

    @Test
    void createHashesPasswordBeforeSaving() {
        ProfileRepository repository = mock(ProfileRepository.class);
        when(repository.findByUsername("user1")).thenReturn(Optional.empty());
        when(repository.save(any(Profile.class))).thenAnswer(invocation -> {
            Profile profile = invocation.getArgument(0);
            profile.setId(99);
            return profile;
        });

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        ProfileService service = new ProfileService();
        ReflectionTestUtils.setField(service, "profileRepository", repository);
        ReflectionTestUtils.setField(service, "passwordEncoder", passwordEncoder);

        ProfileDto dto = new ProfileDto();
        dto.setName("Ali");
        dto.setSurname("Valiyev");
        dto.setUsername("user1");
        dto.setPassword("secret123");

        ProfileDto result = service.create(dto);

        ArgumentCaptor<Profile> captor = ArgumentCaptor.forClass(Profile.class);
        verify(repository).save(captor.capture());

        Profile savedProfile = captor.getValue();
        assertThat(result.getId()).isEqualTo(99);
        assertThat(result.getPassword()).isNull();
        assertThat(savedProfile.getPassword()).isNotEqualTo("secret123");
        assertThat(passwordEncoder.matches("secret123", savedProfile.getPassword())).isTrue();
    }
}
