package com.example.AlumniInternProject.chat.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
public class UsersToGroupDTO {
    private UUID groupId;
    private Set<UUID> userIds;
}
