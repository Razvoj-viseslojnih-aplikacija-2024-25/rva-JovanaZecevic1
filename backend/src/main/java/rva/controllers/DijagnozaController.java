package rva.controllers;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rva.implementation.DijagnozaServiceImpl;
import rva.models.Dijagnoza;

@CrossOrigin
@RestController
public class DijagnozaController {

    @Autowired
    private DijagnozaServiceImpl service;

    @GetMapping("/dijagnoze")
    public ResponseEntity<?> getAllDijagnoze() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/dijagnoza/id/{id}")
    public ResponseEntity<?> getDijagnozaById(@PathVariable int id) {
        Optional<Dijagnoza> dijagnoza = service.findById(id);
        if (dijagnoza.isEmpty())
            return new ResponseEntity<>("No diagnosis found with id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(dijagnoza.get());
    }

    @GetMapping("/dijagnoza/naziv/{naziv}")
    public ResponseEntity<?> getDijagnozasByNaziv(@PathVariable String naziv) {
        List<Dijagnoza> dijagnoze = service.getDijagnozasByNaziv(naziv);
        if (dijagnoze.isEmpty())
            return new ResponseEntity<>("No diagnoses found with name: " + naziv, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(dijagnoze);
    }

    @GetMapping("/dijagnoza/oznaka/{oznaka}")
    public ResponseEntity<?> getDijagnozasByOznaka(@PathVariable String oznaka) {
        List<Dijagnoza> dijagnoze = service.getDijagnozasByOznaka(oznaka);
        if (dijagnoze.isEmpty())
            return new ResponseEntity<>("No diagnoses found with code: " + oznaka, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(dijagnoze);
    }

    @PostMapping("/dijagnoza")
    public ResponseEntity<?> createDijagnoza(@RequestBody Dijagnoza dijagnoza) {
        if (service.existsById(dijagnoza.getId()))
            return new ResponseEntity<>("Diagnosis with id: " + dijagnoza.getId() + " already exists", HttpStatus.CONFLICT);
        Dijagnoza created = service.create(dijagnoza);
        URI uri = URI.create("/dijagnoza/id/" + created.getId());
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/dijagnoza/{id}")
    public ResponseEntity<?> updateDijagnoza(@PathVariable int id, @RequestBody Dijagnoza dijagnoza) {
        Optional<Dijagnoza> updated = service.update(dijagnoza, id);
        if (updated.isEmpty())
            return new ResponseEntity<>("No diagnosis found with id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(updated.get());
    }

    @DeleteMapping("/dijagnoza/{id}")
    public ResponseEntity<?> deleteDijagnoza(@PathVariable int id) {
        if (!service.existsById(id))
            return new ResponseEntity<>("No diagnosis found with id: " + id, HttpStatus.NOT_FOUND);
        service.delete(id);
        return ResponseEntity.ok("Diagnosis with id: " + id + " has been deleted");
    }
}
