package rva.repository;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import rva.models.Dijagnoza;
import rva.models.Odeljenje;
import rva.models.Pacijent;

public interface PacijentRepository  extends JpaRepository<Pacijent, Integer> {
    List<Pacijent> findByImeContainingIgnoreCase(String ime);
    List<Pacijent> findByPrezimeContainingIgnoreCase(String prezime);
    List<Pacijent> findByOdeljenje(Odeljenje odeljenje);
    List<Pacijent> findByDijagnoza(Dijagnoza dijagnoza);
}
