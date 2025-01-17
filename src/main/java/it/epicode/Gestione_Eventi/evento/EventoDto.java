package it.epicode.Gestione_Eventi.evento;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDto {

    private String titolo;
    private String descrizione;
    private LocalDate data;
    private String luogo;
    private int postiDisponibili;




}
