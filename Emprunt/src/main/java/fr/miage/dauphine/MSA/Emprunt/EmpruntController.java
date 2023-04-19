package fr.miage.dauphine.MSA.Emprunt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class EmpruntController {

  @Autowired
  private EmpruntRepository empruntRepository;

  @Autowired
  private EmpruntService service;

  public EmpruntController(EmpruntService service) {
    this.service = service;
  }

  @PostMapping(path = "/create-emprunt")
  public void createEmprunt(@RequestBody Emprunt emprunt){
    service.create(emprunt);
  }

  @GetMapping("/get-emprunt-by-id/{id}")
  public Optional<Emprunt> getEmpruntById(@PathVariable Long id){
    Optional<Emprunt> emprunt = empruntRepository.findOneById(id);
    return emprunt;
  }

  @GetMapping("/get-all-emprunts")
  public List<Emprunt> getAllEmprunts(){
    List<Emprunt> emprunts = empruntRepository.findAll();
    return emprunts;
  }

  @PutMapping("/update-emprunt/{id}")
  public ResponseEntity<Emprunt> updateEmpruntById(@PathVariable("id") Long id, @RequestBody Emprunt updatedItem) {

    Optional<Emprunt> updated = service.update(id, updatedItem);

    return updated
            .map(value -> ResponseEntity.ok().body(value))
            .orElseGet(() -> {
              Emprunt created = empruntRepository.save(updatedItem);
              URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                      .path("/{id}")
                      .buildAndExpand(created.getId())
                      .toUri();
              return ResponseEntity.created(location).body(created);
            });
  }

  @DeleteMapping("/delete-emprunt/{id}")
  public void deleteEmprunt(@PathVariable Long id){
    service.deleteEmpruntById(id);
  }

  @DeleteMapping("/delete-emprunts")
  public void deleteEmprunts(@RequestBody Map<String, List<Long>> map){
    List<Long> idList = map.get("ids");
    service.deleteEmpruntByIds(idList);
  }
}
