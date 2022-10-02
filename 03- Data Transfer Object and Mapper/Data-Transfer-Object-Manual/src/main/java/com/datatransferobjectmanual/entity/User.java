package com.datatransferobjectmanual.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Created 02/10/2022 - 10:26
 * @Package com.datatransferobjectmanual.entity
 * @Project Data-Transfer-Object-Manual
 * @User LegendDZ
 * @Author Abdelaaziz Ouakala
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "first name can't be empty")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "last name can't be empty")
    @Column(name = "last_name")
    private String lastName;

    @NotNull(message = "username can't be null")
    @NotBlank(message = "username can't be empty")
    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Email
    @NotNull(message = "email can't be null")
    @NotBlank(message = "email can't be empty")
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotNull(message = "password can't be null")
    @NotBlank(message = "password can't be empty")
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    @JsonIgnore
    private List<Address> addressList;
}
