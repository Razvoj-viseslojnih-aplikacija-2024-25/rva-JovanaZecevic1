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
import rva.models.Pacijent;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PacijentControllerIntegrationTest {
    @Autowired
    TestRestTemplate template;

    int getHighestId() {
        int highestId = 0;
        ResponseEntity<List<Pacijent>> response =
                template.exchange("/pacijenti", HttpMethod.GET, null, new ParameterizedTypeReference<List<Pacijent>>() {});
        if (response.getBody().isEmpty()) return highestId;
        for (Pacijent p : response.getBody()) {
            if (highestId < p.getId()) {
                highestId = p.getId();
            }
        }
        return highestId;
    }

    @Test
    @Order(1)
    void testGetAllPacijenti() {
        ResponseEntity<List<Pacijent>> response =
                template.exchange("/pacijenti", HttpMethod.GET, null, new ParameterizedTypeReference<List<Pacijent>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
    }

    @Test
    @Order(2)
    void testGetPacijentsByPrezime() {
        String prezime = "Nikolić";
        ResponseEntity<List<Pacijent>> response =
                template.exchange("/pacijent/prezime/" + prezime, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pacijent>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
        assertTrue(response.getBody().get(0).getPrezime().contains(prezime));
    }

    @Test
    @Order(3)
    void testGetPacijentById() {
        int id = 1;
        ResponseEntity<Pacijent> response = null;
        try {
            response = template.exchange("/pacijent/id/" + id, HttpMethod.GET, null, Pacijent.class);
        } catch (RestClientException e) {
            fail("No data found with primary key: " + id);
        }
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(4)
    void testGetPacijentsByOdeljenje() {
        int odeljenjeId = 1;
        ResponseEntity<List<Pacijent>> response =
                template.exchange("/pacijent/odeljenje/" + odeljenjeId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pacijent>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody().get(0));
        for (Pacijent p : response.getBody()) {
            assertEquals(odeljenjeId, p.getOdeljenje().getId());
        }
    }

    @Test
    @Order(5)
    void testGetPacijentsByDijagnoza() {
        int dijagnozaId = 1;
        ResponseEntity<List<Pacijent>> response =
                template.exchange("/pacijent/dijagnoza/" + dijagnozaId, HttpMethod.GET, null, new ParameterizedTypeReference<List<Pacijent>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody().get(0));
        for (Pacijent p : response.getBody()) {
            assertEquals(dijagnozaId, p.getDijagnoza().getId());
        }
    }

    @Test
    @Order(6)
    void testCreatePacijent() {
        Pacijent pacijent = new Pacijent();
        pacijent.setIme("Test");
        pacijent.setPrezime("Pacijent");
        pacijent.setZdr_osiguranje(true);

        HttpEntity<Pacijent> entity = new HttpEntity<>(pacijent);
        ResponseEntity<Pacijent> response =
                template.exchange("/pacijent", HttpMethod.POST, entity, Pacijent.class);
        int highestId = getHighestId();

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals("/pacijent/id/" + highestId, response.getHeaders().getLocation().toString());
        assertEquals(pacijent.getIme(), response.getBody().getIme());
        assertEquals(pacijent.getPrezime(), response.getBody().getPrezime());
    }

    @Test
    @Order(7)
    void testUpdatePacijent() {
        Pacijent pacijent = new Pacijent();
        pacijent.setIme("Izmenjeni");
        pacijent.setPrezime("Pacijent");
        pacijent.setZdr_osiguranje(false);

        int highestId = getHighestId();
        HttpEntity<Pacijent> entity = new HttpEntity<>(pacijent);
        ResponseEntity<Pacijent> response =
                template.exchange("/pacijent/" + highestId, HttpMethod.PUT, entity, Pacijent.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(pacijent.getIme(), response.getBody().getIme());
        assertEquals(pacijent.getPrezime(), response.getBody().getPrezime());
    }

    @Test
    @Order(8)
    void testDeletePacijent() {
        int highestId = getHighestId();
        ResponseEntity<String> response =
                template.exchange("/pacijent/" + highestId, HttpMethod.DELETE, null, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Patient with id: " + highestId + " has been deleted", response.getBody());

        ResponseEntity<String> responseGet =
                template.exchange("/pacijent/id/" + highestId, HttpMethod.GET, null, String.class);
        assertEquals(404, responseGet.getStatusCode().value());
        assertEquals("No patient found with id: " + highestId, responseGet.getBody());
    }

}
