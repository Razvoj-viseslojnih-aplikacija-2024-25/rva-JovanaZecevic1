package rva.controllers;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rva.implementation.BolnicaServiceImpl;
import rva.implementation.OdeljenjeServiceImpl;
import rva.models.Bolnica;
import rva.models.Odeljenje;

@CrossOrigin
@RestController
public class OdeljenjeController {

    @Autowired
    private OdeljenjeServiceImpl service;

    @Autowired
    private BolnicaServiceImpl bolnicaService;

    @GetMapping("/odeljenja")
    public ResponseEntity<?> getAllOdeljenja() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/odeljenje/id/{id}")
    public ResponseEntity<?> getOdeljenjeById(@PathVariable int id) {
        Optional<Odeljenje> odeljenje = service.findById(id);
        if (odeljenje.isEmpty())
            return new ResponseEntity<>("No department found with id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(odeljenje.get());
    }

    @GetMapping("/odeljenje/naziv/{naziv}")
    public ResponseEntity<?> getOdeljenjaByNaziv(@PathVariable String naziv) {
        List<Odeljenje> odeljenja = service.getOdeljenjaByNaziv(naziv);
        if (odeljenja.isEmpty())
            return new ResponseEntity<>("No departments found with name: " + naziv, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(odeljenja);
    }

    @GetMapping("/odeljenje/lokacija/{lokacija}")
    public ResponseEntity<?> getOdeljenjaByLokacija(@PathVariable String lokacija) {
        List<Odeljenje> odeljenja = service.getOdeljenjaByLokacija(lokacija);
        if (odeljenja.isEmpty())
            return new ResponseEntity<>("No departments found with location: " + lokacija, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(odeljenja);
    }

    @GetMapping("/odeljenje/bolnica/{foreignKey}")
    public ResponseEntity<?> getOdeljenjaByBolnica(@PathVariable int foreignKey) {
        Optional<Bolnica> bolnica = bolnicaService.findById(foreignKey);
        if (bolnica.isEmpty())
            return new ResponseEntity<>("No hospital found with id: " + foreignKey, HttpStatus.NOT_FOUND);
        List<Odeljenje> odeljenja = service.getOdeljenjaByBolnica(bolnica.get());
        if (odeljenja.isEmpty())
            return new ResponseEntity<>("No departments found for hospital with id: " + foreignKey, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(odeljenja);
    }

    @PostMapping("/odeljenje")
    public ResponseEntity<?> createOdeljenje(@RequestBody Odeljenje odeljenje) {
        if (service.existsById(odeljenje.getId()))
            return new ResponseEntity<>("Department with id: " + odeljenje.getId() + " already exists", HttpStatus.CONFLICT);
        Odeljenje created = service.create(odeljenje);
        URI uri = URI.create("/odeljenje/id/" + created.getId());
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/odeljenje/{id}")
    public ResponseEntity<?> updateOdeljenje(@PathVariable int id, @RequestBody Odeljenje odeljenje) {
        Optional<Odeljenje> updated = service.update(odeljenje, id);
        if (updated.isEmpty())
            return new ResponseEntity<>("No department found with id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(updated.get());
    }

    @DeleteMapping("/odeljenje/{id}")
    public ResponseEntity<?> deleteOdeljenje(@PathVariable int id) {
        if (!service.existsById(id))
            return new ResponseEntity<>("No department found with id: " + id, HttpStatus.NOT_FOUND);
        service.delete(id);
        return ResponseEntity.ok("Department with id: " + id + " has been deleted");
    }
}
