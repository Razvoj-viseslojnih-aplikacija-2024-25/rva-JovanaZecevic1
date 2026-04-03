package rva.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rva.models.Bolnica;

public interface BolnicaRepository extends JpaRepository<Bolnica, Integer> {
    List<Bolnica> findByNazivContainingIgnoreCase(String naziv);
    List<Bolnica> findByAdresaContainingIgnoreCase(String adresa);
    List<Bolnica> findByBudzetGreaterThanEqual(double budzet);
}
