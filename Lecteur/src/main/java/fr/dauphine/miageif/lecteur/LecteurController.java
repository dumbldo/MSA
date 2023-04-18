package fr.dauphine.miageif.lecteur;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lecteurs")
public class LecteurController {

    @Autowired
    private LecteurService lecteurService;

    @PostMapping
    public ResponseEntity<Lecteur> createLecteur(@RequestBody Lecteur lecteur) {
        Lecteur createdLecteur = lecteurService.createLecteur(lecteur);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdLecteur.getId())
                .toUri();
        return ResponseEntity.created(location).body(createdLecteur);
    }

    
    @PostMapping("/multiple")
    public ResponseEntity<List<Lecteur>> createLecteurs(@RequestBody List<Lecteur> lecteurs) {
        List<Lecteur> createdLecteurs = lecteurService.createLecteur(lecteurs);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLecteurs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Lecteur> getLecteurById(@PathVariable Long id) {
        Optional<Lecteur> lecteur = lecteurService.getLecteurById(id);
        return lecteur.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Lecteur> getAllLecteurs() {
        return lecteurService.getAllLecteurs();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Lecteur> updateLecteur(@PathVariable Long id, @RequestBody Lecteur updatedLecteur) {
        Lecteur updated = lecteurService.updateLecteur(id, updatedLecteur);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLecteur(@PathVariable Long id) {
        lecteurService.deleteLecteurById(id);
        return ResponseEntity.noContent().build();
    }
}
