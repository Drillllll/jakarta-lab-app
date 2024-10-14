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
public class PatchPlayerRequest {

    private String username;
    private String login;
    private LocalDate birthDate;
    private String heroName;

}
