package it.epicode.Gestione_Eventi.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
