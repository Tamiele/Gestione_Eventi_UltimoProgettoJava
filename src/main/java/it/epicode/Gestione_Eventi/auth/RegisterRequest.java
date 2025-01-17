package it.epicode.Gestione_Eventi.auth;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
}
