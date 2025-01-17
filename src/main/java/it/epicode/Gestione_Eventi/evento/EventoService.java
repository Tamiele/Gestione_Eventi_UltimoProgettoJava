package it.epicode.Gestione_Eventi.evento;

import it.epicode.Gestione_Eventi.auth.AppUser;
import it.epicode.Gestione_Eventi.auth.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;

    private final AppUserRepository appUserRepository;

    @Transactional
    public Evento createEvento(EventoDto eventoDto, AppUser currentUser) {
        Evento evento = new Evento();
        BeanUtils.copyProperties(eventoDto, evento);
        evento.setOrganizzatore(currentUser);  // L'organizzatore è quello attualmente loggato
        return eventoRepository.save(evento);
    }


    @Transactional
    public Evento updateEvento(Long id, EventoDto eventoDto, AppUser currentUser) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));
        if (!evento.getOrganizzatore().equals(currentUser)) {
            throw new RuntimeException("Permesso negato: Non sei l'organizzatore di questo evento.");
        }
        BeanUtils.copyProperties(eventoDto, evento);
        return eventoRepository.save(evento);
    }

    @Transactional
    public void deleteEvento(Long id, AppUser currentUser) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento non trovato"));
        if (!evento.getOrganizzatore().equals(currentUser)) {
            throw new RuntimeException("Permesso negato: Non sei l'organizzatore di questo evento.");
        }
        eventoRepository.delete(evento);
    }




}
