package it.epicode.Gestione_Eventi.evento;

import it.epicode.Gestione_Eventi.auth.AppUser;
import it.epicode.Gestione_Eventi.auth.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/eventi")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/eventi")
    @PreAuthorize("hasRole('ROLE_ORGANIZZATORE')")
    public ResponseEntity<Evento> createEvento(@RequestBody EventoDto eventoDto, Principal principal) {
        AppUser currentUser = appUserRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Evento evento = eventoService.createEvento(eventoDto, currentUser);
        return ResponseEntity.ok(evento);
    }



//    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ORGANIZZATORE')")
//    public ResponseEntity<Evento> modificaEvento(@PathVariable Long id, @RequestBody EventoDto eventoDto) {
//        Evento evento = eventoService.updateEvento(id, eventoDto);
//        return new ResponseEntity<>(evento, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('ROLE_ORGANIZZATORE')")
//    public ResponseEntity<Evento> cancellaEvento(@PathVariable Long id) {
//        Evento evento = eventoService.deleteEvento(id);
//        return new ResponseEntity<>(evento, HttpStatus.OK);
//    }
}
