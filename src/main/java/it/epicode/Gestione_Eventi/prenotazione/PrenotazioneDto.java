package it.epicode.Gestione_Eventi.prenotazione;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PrenotazioneDto {

    @NotBlank(message = "l'id dell'evento è obbligatorio")
    @Positive(message = "l'id dell'evento deve essere un numero positivo")
    private Long eventoId;

    @NotBlank(message = "Il numero di posti che vuoi prenotare è obbligatorio")
    @Positive(message = "Il numero di posti da prenotare deve essere positivo")
    private int numeroPostiPrenotati;


}
