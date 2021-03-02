package edu.sjsu.cmpe295.userservice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="user")
public class User {

    @Schema(description = "Unique identifier of the user. Automatic generated.", example = "1", required = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "User email. Should be unique", example = "tom@abc.com", required = true)
    @NotNull
    @Email
    @Column(name="email")
    @Length(max = 128)
    private String email;

    @Schema(description = "User password.", example = "123456", required = true)
    @NotNull
    @Column(name="password")
    private String password;

    @Schema(description = "User first name.", example = "Tom", required = true)
    @NotNull
    @Column(name="first_name")
    @Length(max = 50)
    private String firstName;

    @Schema(description = "User last name.", example = "Ronaldo", required = true)
    @NotNull
    @Column(name="last_name")
    @Length(max = 50)
    private String lastName;

    @Schema(description = "User address.", example = "123 street, city, CA")
    @Column(name="address")
    @Length(max = 500)
    private String address;

    @Schema(description = "User phone number.", example = "555-555-5555")
    @Column(name="phone")
    @Length(max = 50)
    private String phone;

    @Schema(description = "User role in system. System generated.", example = "ROLE_USER")
    @Column(name="role")
    private String role;

    @Schema(description = "User active identifier. System generated", example = "true")
    @Column(name="active")
    private Boolean active;

    @Schema(description = "Unique token to enable user account", example = "1234567890")
    @Column(name="token")
    private String token;

    @Schema(description = "User created time", example = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name="creation_time")
    private LocalDateTime creationTime = LocalDateTime.now();

}
