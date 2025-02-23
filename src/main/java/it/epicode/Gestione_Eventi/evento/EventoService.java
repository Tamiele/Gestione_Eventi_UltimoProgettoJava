package it.epicode.Gestione_Eventi.evento;

import it.epicode.Gestione_Eventi.auth.AppUser;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@RequiredArgsConstructor
@Validated
public class EventoService {

    private final EventoRepository eventoRepository;


    @Transactional
    public Evento createEvento(@Valid EventoDto eventoDto, AppUser currentUser) {
        Evento evento = new Evento();
        BeanUtils.copyProperties(eventoDto, evento);
        evento.setOrganizzatore(currentUser);
        return eventoRepository.save(evento);
    }


    @Transactional
    public Evento updateEvento(Long id, @Valid EventoDto eventoDto, AppUser currentUser) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
        if (!evento.getOrganizzatore().equals(currentUser)) {
            throw new AccessDeniedException("Permesso negato: Non sei l'organizzatore di questo evento.");
        }
        BeanUtils.copyProperties(eventoDto, evento);
        return eventoRepository.save(evento);
    }

    @Transactional
    public void deleteEvento(Long id, AppUser currentUser) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Evento non trovato"));
        if (!evento.getOrganizzatore().equals(currentUser)) {
            throw new AccessDeniedException("Permesso negato: Non sei l'organizzatore di questo evento.");
        }
        eventoRepository.delete(evento);
    }


}
