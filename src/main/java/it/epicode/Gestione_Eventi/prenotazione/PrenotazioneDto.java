package it.epicode.Gestione_Eventi.prenotazione;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PrenotazioneDto {

    @NotNull
    @Positive(message = "l'id dell'evento deve essere un numero positivo")
    private Long eventoId;

    @NotNull
    @Positive(message = "Il numero di posti da prenotare deve essere positivo")
    private int numeroPostiPrenotati;


}
