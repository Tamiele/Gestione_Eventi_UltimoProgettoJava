package it.epicode.Gestione_Eventi.prenotazione;


import it.epicode.Gestione_Eventi.auth.AppUser;
import it.epicode.Gestione_Eventi.auth.AppUserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/prenotazioni")

public class PrenotazioneController {

    @Autowired
    private PrenotazioneService prenotazioneService;

    @Autowired
    private AppUserRepository appUserRepository;

    @PostMapping("/prenotazioni")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Prenotazione> prenotaEvento( @RequestBody PrenotazioneDto prenotazioneDto, Principal principal) {
        // Recupera l'utente attualmente loggato tramite il Principal
        AppUser currentUser = appUserRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        // Passa l'oggetto AppUser al servizio
        Prenotazione prenotazione = prenotazioneService.prenotazioneEvento(prenotazioneDto, currentUser);
        return ResponseEntity.ok(prenotazione);
    }

    @DeleteMapping("/prenotazioni/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> cancellaPrenotazione(@PathVariable Long id, Principal principal) {
        // Recupera l'utente attualmente loggato tramite il Principal
        AppUser currentUser = appUserRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

        // Passa l'utente e l'ID della prenotazione al servizio per cancellarla
        prenotazioneService.cancellaPrenotazione(id, currentUser);
        return ResponseEntity.ok("Prenotazione cancellata con successo.");
    }


}
