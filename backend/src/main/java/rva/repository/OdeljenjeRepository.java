package rva.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rva.models.Bolnica;
import rva.models.Odeljenje;

public interface OdeljenjeRepository extends JpaRepository<Odeljenje, Integer> {
    List<Odeljenje> findByNazivContainingIgnoreCase(String naziv);
    List<Odeljenje> findByBolnica(Bolnica bolnica);
    List<Odeljenje> findByLokacijaContainingIgnoreCase(String lokacija);
}
