package it.epicode.Gestione_Eventi.evento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EventoDto {

    @NotBlank(message = "Il Titolo dell'evento è obbligatorio")
    private String titolo;

    @NotBlank(message = "La descrizione dell'evento è obbligatorio")
    private String descrizione;

    @NotNull
    private LocalDate data;

    @NotBlank(message = "Il Luogo dell'evento è obbligatorio")
    private String luogo;

   @NotNull
    @Positive(message = "Deve essere un numero positivo")
    private int postiDisponibili;


}
