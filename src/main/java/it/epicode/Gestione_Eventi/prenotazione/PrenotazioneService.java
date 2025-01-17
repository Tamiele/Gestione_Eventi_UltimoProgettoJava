package it.epicode.Gestione_Eventi.prenotazione;


import it.epicode.Gestione_Eventi.auth.AppUser;
import it.epicode.Gestione_Eventi.evento.Evento;
import it.epicode.Gestione_Eventi.evento.EventoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));

        if (evento.getPostiDisponibili() < prenotazioneDto.getNumeroPostiPrenotati()) {
            throw new EntityNotFoundException("Numero di posti insufficienti.");
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
                .orElseThrow(() -> new EntityNotFoundException("Prenotazione non trovata"));


        if (!prenotazione.getUtente().equals(currentUser)) {
            throw new AccessDeniedException("Permesso negato: Non hai effettuato tu questa prenotazione.");
        }


        Evento evento = prenotazione.getEvento();


        evento.setPostiDisponibili(evento.getPostiDisponibili() + prenotazione.getNumeroPosti());


        eventoRepository.save(evento);


        prenotazioneRepository.delete(prenotazione);
    }


}
