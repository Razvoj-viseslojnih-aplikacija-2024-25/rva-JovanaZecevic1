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
import rva.models.Odeljenje;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class OdeljenjeControllerIntegrationTest {

    @Autowired
    TestRestTemplate template;

    int getHighestId() {
        int highestId = 0;
        ResponseEntity<List<Odeljenje>> response =
                template.exchange("/odeljenja", HttpMethod.GET, null, new ParameterizedTypeReference<List<Odeljenje>>() {});
        if (response.getBody().isEmpty()) return highestId;
        for (Odeljenje o : response.getBody()) {
            if (highestId < o.getId()) {
                highestId = o.getId();
            }
        }
        return highestId;
    }

    @Test
    @Order(1)
    void testGetAllOdeljenja() {
        ResponseEntity<List<Odeljenje>> response =
                template.exchange("/odeljenja", HttpMethod.GET, null, new ParameterizedTypeReference<List<Odeljenje>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
    }

    @Test
    @Order(2)
    void testGetOdeljenjaByNaziv() {
        String naziv = "Kardiologija";
        ResponseEntity<List<Odeljenje>> response =
                template.exchange("/odeljenje/naziv/" + naziv, HttpMethod.GET, null, new ParameterizedTypeReference<List<Odeljenje>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
        assertTrue(response.getBody().get(0).getNaziv().contains(naziv));
    }

    @Test
    @Order(3)
    void testGetOdeljenjeById() {
        int id = 1;
        ResponseEntity<Odeljenje> response = null;
        try {
            response = template.exchange("/odeljenje/id/" + id, HttpMethod.GET, null, Odeljenje.class);
        } catch (RestClientException e) {
            fail("No data found with primary key: " + id);
        }
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(4)
    void testGetOdeljenjaByBolnica() {
        int bolnicaId = 1;
        ResponseEntity<List<Odeljenje>> response =
                template.exchange("/odeljenje/bolnica/" + bolnicaId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Odeljenje>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody().get(0));
        for (Odeljenje o : response.getBody()) {
            assertEquals(bolnicaId, o.getBolnica().getId());
        }
    }

    @Test
    @Order(5)
    void testCreateOdeljenje() {
        Odeljenje odeljenje = new Odeljenje();
        odeljenje.setNaziv("Test odeljenje");
        odeljenje.setLokacija("Test lokacija");

        HttpEntity<Odeljenje> entity = new HttpEntity<>(odeljenje);
        ResponseEntity<Odeljenje> response =
                template.exchange("/odeljenje", HttpMethod.POST, entity, Odeljenje.class);
        int highestId = getHighestId();

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals("/odeljenje/id/" + highestId, response.getHeaders().getLocation().toString());
        assertEquals(odeljenje.getNaziv(), response.getBody().getNaziv());
        assertEquals(odeljenje.getLokacija(), response.getBody().getLokacija());
    }

    @Test
    @Order(6)
    void testUpdateOdeljenje() {
        Odeljenje odeljenje = new Odeljenje();
        odeljenje.setNaziv("Izmenjeno odeljenje");
        odeljenje.setLokacija("Izmenjena lokacija");

        int highestId = getHighestId();
        HttpEntity<Odeljenje> entity = new HttpEntity<>(odeljenje);
        ResponseEntity<Odeljenje> response =
                template.exchange("/odeljenje/" + highestId, HttpMethod.PUT, entity, Odeljenje.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(odeljenje.getNaziv(), response.getBody().getNaziv());
        assertEquals(odeljenje.getLokacija(), response.getBody().getLokacija());
    }

    @Test
    @Order(7)
    void testDeleteOdeljenje() {
        int highestId = getHighestId();
        ResponseEntity<String> response =
                template.exchange("/odeljenje/" + highestId, HttpMethod.DELETE, null, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Department with id: " + highestId + " has been deleted", response.getBody());

        ResponseEntity<String> responseGet =
                template.exchange("/odeljenje/id/" + highestId, HttpMethod.GET, null, String.class);
        assertEquals(404, responseGet.getStatusCode().value());
        assertEquals("No department found with id: " + highestId, responseGet.getBody());
    }
}
