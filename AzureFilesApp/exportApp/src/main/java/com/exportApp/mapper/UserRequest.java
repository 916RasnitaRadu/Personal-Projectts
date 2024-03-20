package com.exportApp.mapper;

import lombok.*;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String password;
}
