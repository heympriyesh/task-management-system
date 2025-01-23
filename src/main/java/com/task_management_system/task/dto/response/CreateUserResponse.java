package com.task_management_system.task.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {
    private Long id;
    private String name;
    private String email;
    private String userName;
    private String phoneNumber;
    private String role;
}
