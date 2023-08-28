package com.example.AlumniInternProject.connectionRequest;

import com.example.AlumniInternProject.entity.ConnectionRequestStatus;
import com.example.AlumniInternProject.entity.IdBaseEntity;
import com.example.AlumniInternProject.user.DTOs.ConnectUserDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConnectionRequestDTO extends IdBaseEntity {
    private ConnectUserDTO requester;
    private ConnectUserDTO requestee;
    private ConnectionRequestStatus status;

    public ConnectionRequestDTO(ConnectUserDTO requester, ConnectUserDTO requestee, ConnectionRequestStatus status) {
        this.requester = requester;
        this.requestee = requestee;
        this.status = status;
    }

    public ConnectionRequestDTO() {
    }
}
