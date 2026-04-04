package rva.integrationTesting;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import rva.models.Bolnica;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BolnicaControllerIntegrationTest {

    @Autowired
    TestRestTemplate template;

    int getHighestId() {
        int highestId = 0;
        ResponseEntity<List<Bolnica>> response =
                template.exchange("/bolnice", HttpMethod.GET, null, new ParameterizedTypeReference<List<Bolnica>>() {});
        if (response.getBody().isEmpty()) return highestId;
        for (Bolnica b : response.getBody()) {
            if (highestId < b.getId()) {
                highestId = b.getId();
            }
        }
        return highestId;
    }

    @Test
    @Order(1)
    void testGetAllBolnice() {
        ResponseEntity<List<Bolnica>> response =
                template.exchange("/bolnice", HttpMethod.GET, null, new ParameterizedTypeReference<List<Bolnica>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
    }

    @Test
    @Order(2)
    void testGetBolnicasByNaziv() {
        String naziv = "Klinički";
        ResponseEntity<List<Bolnica>> response =
                template.exchange("/bolnica/naziv/" + naziv, HttpMethod.GET, null, new ParameterizedTypeReference<List<Bolnica>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
        assertTrue(response.getBody().get(0).getNaziv().contains(naziv));
    }

    @Test
    @Order(3)
    void testGetBolnicaById() {
        int id = 1;
        ResponseEntity<Bolnica> response = null;
        try {
            response = template.exchange("/bolnica/id/" + id, HttpMethod.GET, null, Bolnica.class);
        } catch (RestClientException e) {
            fail("No data found with primary key: " + id);
        }
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(4)
    void testCreateBolnica() {
        Bolnica bolnica = new Bolnica();
        bolnica.setNaziv("Test bolnica");
        bolnica.setAdresa("Test adresa");
        bolnica.setBudzet(1000000);

        HttpEntity<Bolnica> entity = new HttpEntity<>(bolnica);
        ResponseEntity<Bolnica> response =
                template.exchange("/bolnica", HttpMethod.POST, entity, Bolnica.class);
        int highestId = getHighestId();

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals("/bolnica/id/" + highestId, response.getHeaders().getLocation().toString());
        assertEquals(bolnica.getNaziv(), response.getBody().getNaziv());
        assertEquals(bolnica.getAdresa(), response.getBody().getAdresa());
    }

    @Test
    @Order(5)
    void testUpdateBolnica() {
        Bolnica bolnica = new Bolnica();
        bolnica.setNaziv("Izmenjena bolnica");
        bolnica.setAdresa("Izmenjena adresa");
        bolnica.setBudzet(2000000);

        int highestId = getHighestId();
        HttpEntity<Bolnica> entity = new HttpEntity<>(bolnica);
        ResponseEntity<Bolnica> response =
                template.exchange("/bolnica/" + highestId, HttpMethod.PUT, entity, Bolnica.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(bolnica.getNaziv(), response.getBody().getNaziv());
        assertEquals(bolnica.getAdresa(), response.getBody().getAdresa());
    }

    @Test
    @Order(6)
    void testDeleteBolnica() {
        int highestId = getHighestId();
        ResponseEntity<String> response =
                template.exchange("/bolnica/" + highestId, HttpMethod.DELETE, null, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Hospital with id: " + highestId + " has been deleted", response.getBody());

        ResponseEntity<String> responseGet =
                template.exchange("/bolnica/id/" + highestId, HttpMethod.GET, null, String.class);
        assertEquals(404, responseGet.getStatusCode().value());
        assertEquals("No hospital found with id: " + highestId, responseGet.getBody());
    }
}
