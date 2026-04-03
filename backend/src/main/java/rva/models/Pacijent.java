package rva.models;
import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Pacijent {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "PACIJENT_ID_GEN", sequenceName = "PACIJENT_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PACIJENT_ID_GEN")
    private int id;

    private String ime;
    private String prezime;
    private boolean zdr_osiguranje;
    private LocalDate datum_rodjenja;

    @ManyToOne
    private Odeljenje odeljenje;

    @ManyToOne
    private Dijagnoza dijagnoza;

    public Pacijent() {
        super();
    }

    public Pacijent(int id, String ime, String prezime, boolean zdr_osiguranje, LocalDate datum_rodjenja, Odeljenje odeljenje, Dijagnoza dijagnoza) {
        super();
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.zdr_osiguranje = zdr_osiguranje;
        this.datum_rodjenja = datum_rodjenja;
        this.odeljenje = odeljenje;
        this.dijagnoza = dijagnoza;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public boolean isZdr_osiguranje() {
        return zdr_osiguranje;
    }

    public void setZdr_osiguranje(boolean zdr_osiguranje) {
        this.zdr_osiguranje = zdr_osiguranje;
    }

    public LocalDate getDatum_rodjenja() {
        return datum_rodjenja;
    }

    public void setDatum_rodjenja(LocalDate datum_rodjenja) {
        this.datum_rodjenja = datum_rodjenja;
    }

    public Odeljenje getOdeljenje() {
        return odeljenje;
    }

    public void setOdeljenje(Odeljenje odeljenje) {
        this.odeljenje = odeljenje;
    }

    public Dijagnoza getDijagnoza() {
        return dijagnoza;
    }

    public void setDijagnoza(Dijagnoza dijagnoza) {
        this.dijagnoza = dijagnoza;
    }
}
