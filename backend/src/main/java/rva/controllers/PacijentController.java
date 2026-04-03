package rva.controllers;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import rva.implementation.DijagnozaServiceImpl;
import rva.implementation.OdeljenjeServiceImpl;
import rva.implementation.PacijentServiceImpl;
import rva.models.Dijagnoza;
import rva.models.Odeljenje;
import rva.models.Pacijent;

@CrossOrigin
@RestController
public class PacijentController {

    @Autowired
    private PacijentServiceImpl service;

    @Autowired
    private OdeljenjeServiceImpl odeljenjeService;

    @Autowired
    private DijagnozaServiceImpl dijagnozaService;

    @GetMapping("/pacijenti")
    public ResponseEntity<?> getAllPacijenti() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/pacijent/id/{id}")
    public ResponseEntity<?> getPacijentById(@PathVariable int id) {
        Optional<Pacijent> pacijent = service.findById(id);
        if (pacijent.isEmpty())
            return new ResponseEntity<>("No patient found with id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(pacijent.get());
    }

    @GetMapping("/pacijent/ime/{ime}")
    public ResponseEntity<?> getPacijentsByIme(@PathVariable String ime) {
        List<Pacijent> pacijenti = service.getPacijentsByIme(ime);
        if (pacijenti.isEmpty())
            return new ResponseEntity<>("No patients found with name: " + ime, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(pacijenti);
    }

    @GetMapping("/pacijent/prezime/{prezime}")
    public ResponseEntity<?> getPacijentsByPrezime(@PathVariable String prezime) {
        List<Pacijent> pacijenti = service.getPacijentsByPrezime(prezime);
        if (pacijenti.isEmpty())
            return new ResponseEntity<>("No patients found with last name: " + prezime, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(pacijenti);
    }

    @GetMapping("/pacijent/osiguranje/{osiguranje}")
    public ResponseEntity<?> getPacijentsByZdrOsiguranje(@PathVariable boolean osiguranje) {
        List<Pacijent> pacijenti = service.getPacijentsByZdrOsiguranje(osiguranje);
        if (pacijenti.isEmpty())
            return new ResponseEntity<>("No patients found with insurance status: " + osiguranje, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(pacijenti);
    }

    @GetMapping("/pacijent/odeljenje/{foreignKey}")
    public ResponseEntity<?> getPacijentsByOdeljenje(@PathVariable int foreignKey) {
        Optional<Odeljenje> odeljenje = odeljenjeService.findById(foreignKey);
        if (odeljenje.isEmpty())
            return new ResponseEntity<>("No department found with id: " + foreignKey, HttpStatus.NOT_FOUND);
        List<Pacijent> pacijenti = service.getPacijentsByOdeljenje(odeljenje.get());
        if (pacijenti.isEmpty())
            return new ResponseEntity<>("No patients found for department with id: " + foreignKey, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(pacijenti);
    }

    @GetMapping("/pacijent/dijagnoza/{foreignKey}")
    public ResponseEntity<?> getPacijentsByDijagnoza(@PathVariable int foreignKey) {
        Optional<Dijagnoza> dijagnoza = dijagnozaService.findById(foreignKey);
        if (dijagnoza.isEmpty())
            return new ResponseEntity<>("No diagnosis found with id: " + foreignKey, HttpStatus.NOT_FOUND);
        List<Pacijent> pacijenti = service.getPacijentsByDijagnoza(dijagnoza.get());
        if (pacijenti.isEmpty())
            return new ResponseEntity<>("No patients found for diagnosis with id: " + foreignKey, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(pacijenti);
    }

    @PostMapping("/pacijent")
    public ResponseEntity<?> createPacijent(@RequestBody Pacijent pacijent) {
        if (service.existsById(pacijent.getId()))
            return new ResponseEntity<>("Patient with id: " + pacijent.getId() + " already exists", HttpStatus.CONFLICT);
        Pacijent created = service.create(pacijent);
        URI uri = URI.create("/pacijent/id/" + created.getId());
        return ResponseEntity.created(uri).body(created);
    }

    @PutMapping("/pacijent/{id}")
    public ResponseEntity<?> updatePacijent(@PathVariable int id, @RequestBody Pacijent pacijent) {
        Optional<Pacijent> updated = service.update(pacijent, id);
        if (updated.isEmpty())
            return new ResponseEntity<>("No patient found with id: " + id, HttpStatus.NOT_FOUND);
        return ResponseEntity.ok(updated.get());
    }

    @DeleteMapping("/pacijent/{id}")
    public ResponseEntity<?> deletePacijent(@PathVariable int id) {
        if (!service.existsById(id))
            return new ResponseEntity<>("No patient found with id: " + id, HttpStatus.NOT_FOUND);
        service.delete(id);
        return ResponseEntity.ok("Patient with id: " + id + " has been deleted");
    }
}
