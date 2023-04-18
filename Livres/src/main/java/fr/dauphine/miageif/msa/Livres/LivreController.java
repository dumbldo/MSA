package fr.dauphine.miageif.msa.Livres;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/livres")

public class LivreController {

    @Autowired

    private LivreService livreService;

    @PostMapping
    public ResponseEntity<Livre> createLivre(@RequestBody Livre livre) {
        Livre createdLivre = livreService.createLivre(livre);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdLivre.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdLivre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Livre> getLivreById(@PathVariable Long id) {
        Optional<Livre> livre = livreService.getLivreById(id);
        return livre.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Livre> getAllLivres() {
        return livreService.getAllLivres();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateLivre(@PathVariable Long id, @RequestBody Livre updatedLivre) {
        Livre updated = livreService.updateLivre(id, updatedLivre);
        if (updated == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Le livre avec l'id " + id + " est introuvable.");
        }
        return ResponseEntity.ok("Livre avec id " + id + " modifié !");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLivre(@PathVariable Long id) {
        livreService.deleteLivreById(id);
        return ResponseEntity.noContent().build();
    }
}

