package com.deloop.application.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class UserDto implements Serializable {
    private long id;

    @Builder.Default
    private String username = "";

    @Builder.Default
    private String firstname = "";

    @Builder.Default
    private String lastname = "";

    @Builder.Default
    private String email = "";

    @Builder.Default
    private String telephone = "";

}
