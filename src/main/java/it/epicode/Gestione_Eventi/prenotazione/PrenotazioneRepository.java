package it.epicode.Gestione_Eventi.prenotazione;

import it.epicode.Gestione_Eventi.evento.Evento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {

}
