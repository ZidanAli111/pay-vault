package entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_profile")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfile {

    @Id
    @Column(updatable = false)
    private String userId;

    @Column(nullable = false)
    private String username;

    @Column
    private String bio;

    @Column
    private String profilePictureUrl;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;
}
