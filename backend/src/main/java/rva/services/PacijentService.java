package rva.services;
import java.util.List;
import java.time.LocalDate;
import org.springframework.stereotype.Service;
import rva.models.Dijagnoza;
import rva.models.Odeljenje;
import rva.models.Pacijent;

@Service
public interface PacijentService extends CrudService<Pacijent> {
    List<Pacijent> getPacijentsByIme(String ime);
    List<Pacijent> getPacijentsByPrezime(String prezime);
    List<Pacijent> getPacijentsByOdeljenje(Odeljenje odeljenje);
    List<Pacijent> getPacijentsByDijagnoza(Dijagnoza dijagnoza);

}
