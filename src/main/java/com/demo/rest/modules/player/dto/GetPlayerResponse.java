package com.demo.rest.modules.player.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class GetPlayerResponse {

    private UUID id;

    private String login;
    private String username;
    private LocalDate birthDate;

    private String heroName;
}
