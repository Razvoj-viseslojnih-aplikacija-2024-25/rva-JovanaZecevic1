package rva.services;
import java.util.List;
import org.springframework.stereotype.Service;
import rva.models.Bolnica;

@Service
public interface BolnicaService extends CrudService<Bolnica> {
    List<Bolnica> getBolnicasByNaziv(String naziv);
    List<Bolnica> getBolnicasByAdresa(String adresa);
    List<Bolnica> getBolnicasByBudzet(double budzet);
}
