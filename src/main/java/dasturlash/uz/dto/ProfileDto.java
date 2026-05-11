package dasturlash.uz.dto;

import dasturlash.uz.enums.ProfileRole;
import dasturlash.uz.enums.ProfileStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ProfileDto {
    private Integer id;
    private String name;
    private String surname;
    private String username;
    private String password;
    private ProfileStatus status;
    private List<ProfileRole> roleList;
    private LocalDateTime createdDate;
    private String photoId;
}
