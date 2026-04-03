package rva.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rva.models.Dijagnoza;

public interface DijagnozaRepository extends JpaRepository<Dijagnoza, Integer> {
    List<Dijagnoza> findByNazivContainingIgnoreCase(String naziv);
    List<Dijagnoza> findByOznakaContainingIgnoreCase(String oznaka);
}
