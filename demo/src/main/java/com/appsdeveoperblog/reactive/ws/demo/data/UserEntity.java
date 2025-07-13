package com.appsdeveoperblog.reactive.ws.demo.data;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    private UUID id;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    private String email;

    private String password;
}
