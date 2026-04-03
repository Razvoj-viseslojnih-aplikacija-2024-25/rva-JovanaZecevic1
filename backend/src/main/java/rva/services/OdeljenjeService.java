package rva.services;
import java.util.List;
import org.springframework.stereotype.Service;
import rva.models.Bolnica;
import rva.models.Odeljenje;

@Service
public interface OdeljenjeService extends CrudService<Odeljenje> {
    List<Odeljenje> getOdeljenjaByNaziv(String naziv);
    List<Odeljenje> getOdeljenjaByBolnica(Bolnica bolnica);
    List<Odeljenje> getOdeljenjaByLokacija(String lokacija);
}
