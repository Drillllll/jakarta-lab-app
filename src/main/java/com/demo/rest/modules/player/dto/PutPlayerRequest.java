package com.demo.rest.modules.player.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class PutPlayerRequest {

    private String login;
    private String username;
    private LocalDate birthDate;
    private String heroName;
    private String password;
}
