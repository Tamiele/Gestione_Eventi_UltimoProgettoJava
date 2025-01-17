package it.epicode.Gestione_Eventi.prenotazione;

import it.epicode.Gestione_Eventi.auth.AppUser;
import it.epicode.Gestione_Eventi.evento.Evento;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "prenotazioni")
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Long id;

    @ManyToOne
    private AppUser utente;

    @ManyToOne
    private Evento evento;

    private LocalDateTime dataPrenotazione;


}