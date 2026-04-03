package rva.controllers;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rva.implementation.BolnicaServiceImpl;
import rva.models.Bolnica;

@CrossOrigin
@RestController
public class BolnicaController {

    @Autowired
    private BolnicaServiceImpl service;

    @GetMapping("/bolnice")
    public ResponseEntity<?> getAllBolnice() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/bolnica/id/{id}")
    public ResponseEntity<?> getBolnicaById(@PathVariable int id) {
        Optional<Bolnica> bolnica = service.findById(id);
        if (bolnica.isEmpty())
            return new ResponseEntity<>("No hospital found with id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(bolnica.get());
    }

    @GetMapping("/bolnica/naziv/{naziv}")
    public ResponseEntity<?> getBolnicasByNaziv(@PathVariable String naziv) {
        List<Bolnica> bolnice = service.getBolnicasByNaziv(naziv);
        if (bolnice.isEmpty())
            return new ResponseEntity<>("No hospitals found with name: " + naziv, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(bolnice);
    }

    @GetMapping("/bolnica/adresa/{adresa}")
    public ResponseEntity<?> getBolnicasByAdresa(@PathVariable String adresa) {
        List<Bolnica> bolnice = service.getBolnicasByAdresa(adresa);
        if (bolnice.isEmpty())
            return new ResponseEntity<>("No hospitals found with address: " + adresa, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(bolnice);
    }

    @GetMapping("/bolnica/budzet/{budzet}")
    public ResponseEntity<?> getBolnicasByBudzet(@PathVariable double budzet) {
        List<Bolnica> bolnice = service.getBolnicasByBudzet(budzet);
        if (bolnice.isEmpty())
            return new ResponseEntity<>("No hospitals found with budget >= " + budzet, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(bolnice);
    }

    @PostMapping("/bolnica")
    public ResponseEntity<?> createBolnica(@RequestBody Bolnica bolnica) {
        if (service.existsById(bolnica.getId()))
            return new ResponseEntity<>("Hospital with id: " + bolnica.getId() + " already exists", HttpStatus.CONFLICT);
        Bolnica created = service.create(bolnica);
        URI uri = URI.create("/bolnica/id/" + created.getId());
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/bolnica/{id}")
    public ResponseEntity<?> updateBolnica(@PathVariable int id, @RequestBody Bolnica bolnica) {
        Optional<Bolnica> updated = service.update(bolnica, id);
        if (updated.isEmpty())
            return new ResponseEntity<>("No hospital found with id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(updated.get());
    }

    @DeleteMapping("/bolnica/{id}")
    public ResponseEntity<?> deleteBolnica(@PathVariable int id) {
        if (!service.existsById(id))
            return new ResponseEntity<>("No hospital found with id: " + id, HttpStatus.NOT_FOUND);
        service.delete(id);
        return ResponseEntity.ok("Hospital with id: " + id + " has been deleted");
    }
}
