package it.epicode.Gestione_Eventi.prenotazione;


import it.epicode.Gestione_Eventi.auth.AppUser;
import it.epicode.Gestione_Eventi.evento.Evento;
import it.epicode.Gestione_Eventi.evento.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class PrenotazioneService {


    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private EventoRepository eventoRepository;

    @Transactional
    public Prenotazione prenotazioneEvento(PrenotazioneDto prenotazioneDto, AppUser currentUser) {
        Evento evento = eventoRepository.findById(prenotazioneDto.getEventoId())
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));

        if (evento.getPostiDisponibili() < prenotazioneDto.getNumeroPostiPrenotati()) {
            throw new RuntimeException("Numero di posti insufficienti.");
        }

        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setUtente(currentUser);  // Assegna l'utente che ha effettuato la prenotazione
        prenotazione.setEvento(evento);  // Assegna l'evento alla prenotazione
        prenotazione.setNumeroPosti(prenotazioneDto.getNumeroPostiPrenotati());
        prenotazione.setDataPrenotazione(LocalDateTime.now());

        evento.setPostiDisponibili(evento.getPostiDisponibili() - prenotazioneDto.getNumeroPostiPrenotati());
        eventoRepository.save(evento);  // Aggiorna l'evento con il nuovo numero di posti disponibili

        return prenotazioneRepository.save(prenotazione);  // Salva la prenotazione
    }


    @Transactional
    public void cancellaPrenotazione(Long id, AppUser currentUser) {

        Prenotazione prenotazione = prenotazioneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prenotazione non trovata"));


        if (!prenotazione.getUtente().equals(currentUser)) {
            throw new RuntimeException("Permesso negato: Non hai effettuato questa prenotazione.");
        }


        Evento evento = prenotazione.getEvento();


        evento.setPostiDisponibili(evento.getPostiDisponibili() + prenotazione.getNumeroPosti());


        eventoRepository.save(evento);


        prenotazioneRepository.delete(prenotazione);
    }


}
