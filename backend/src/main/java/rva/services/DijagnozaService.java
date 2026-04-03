package rva.services;
import java.util.List;
import org.springframework.stereotype.Service;
import rva.models.Dijagnoza;

@Service
public interface DijagnozaService extends CrudService<Dijagnoza> {
    List<Dijagnoza> getDijagnozasByNaziv(String naziv);
    List<Dijagnoza> getDijagnozasByOznaka(String oznaka);
}
