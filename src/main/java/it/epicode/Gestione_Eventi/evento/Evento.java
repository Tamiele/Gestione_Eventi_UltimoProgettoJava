package it.epicode.Gestione_Eventi.evento;

import it.epicode.Gestione_Eventi.auth.AppUser;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "eventi")
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String titolo;
    private String descrizione;
    private LocalDate data;
    private String luogo;

    @Column(name = "posti_disponibili")
    private int postiDisponibili;

    @ManyToOne
    @JoinColumn(name = "organizzatore_id")
    private AppUser organizzatore;


}