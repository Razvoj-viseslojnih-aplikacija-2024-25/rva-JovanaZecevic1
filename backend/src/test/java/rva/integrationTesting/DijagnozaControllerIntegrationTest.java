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
import rva.models.Dijagnoza;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DijagnozaControllerIntegrationTest {

    @Autowired
    TestRestTemplate template;

    int getHighestId() {
        int highestId = 0;
        ResponseEntity<List<Dijagnoza>> response =
                template.exchange("/dijagnoze", HttpMethod.GET, null, new ParameterizedTypeReference<List<Dijagnoza>>() {});
        if (response.getBody().isEmpty()) return highestId;
        for (Dijagnoza d : response.getBody()) {
            if (highestId < d.getId()) {
                highestId = d.getId();
            }
        }
        return highestId;
    }

    @Test
    @Order(1)
    void testGetAllDijagnoze() {
        ResponseEntity<List<Dijagnoza>> response =
                template.exchange("/dijagnoze", HttpMethod.GET, null, new ParameterizedTypeReference<List<Dijagnoza>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
    }

    @Test
    @Order(2)
    void testGetDijagnozasByNaziv() {
        String naziv = "Hipertenzija";
        ResponseEntity<List<Dijagnoza>> response =
                template.exchange("/dijagnoza/naziv/" + naziv, HttpMethod.GET, null, new ParameterizedTypeReference<List<Dijagnoza>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
        assertTrue(response.getBody().get(0).getNaziv().contains(naziv));
    }

    @Test
    @Order(3)
    void testGetDijagnozaById() {
        int id = 1;
        ResponseEntity<Dijagnoza> response = null;
        try {
            response = template.exchange("/dijagnoza/id/" + id, HttpMethod.GET, null, Dijagnoza.class);
        } catch (RestClientException e) {
            fail("No data found with primary key: " + id);
        }
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    @Order(4)
    void testGetDijagnozasByOznaka() {
        String oznaka = "I10";
        ResponseEntity<List<Dijagnoza>> response =
                template.exchange("/dijagnoza/oznaka/" + oznaka, HttpMethod.GET, null, new ParameterizedTypeReference<List<Dijagnoza>>() {});
        assertEquals(200, response.getStatusCode().value());
        assertTrue(!response.getBody().isEmpty());
        assertTrue(response.getBody().get(0).getOznaka().contains(oznaka));
    }

    @Test
    @Order(5)
    void testCreateDijagnoza() {
        Dijagnoza dijagnoza = new Dijagnoza();
        dijagnoza.setNaziv("Test dijagnoza");
        dijagnoza.setOpis("Test opis");
        dijagnoza.setOznaka("T00");

        HttpEntity<Dijagnoza> entity = new HttpEntity<>(dijagnoza);
        ResponseEntity<Dijagnoza> response =
                template.exchange("/dijagnoza", HttpMethod.POST, entity, Dijagnoza.class);
        int highestId = getHighestId();

        assertEquals(201, response.getStatusCode().value());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals("/dijagnoza/id/" + highestId, response.getHeaders().getLocation().toString());
        assertEquals(dijagnoza.getNaziv(), response.getBody().getNaziv());
        assertEquals(dijagnoza.getOznaka(), response.getBody().getOznaka());
    }

    @Test
    @Order(6)
    void testUpdateDijagnoza() {
        Dijagnoza dijagnoza = new Dijagnoza();
        dijagnoza.setNaziv("Izmenjena dijagnoza");
        dijagnoza.setOpis("Izmenjeni opis");
        dijagnoza.setOznaka("T01");

        int highestId = getHighestId();
        HttpEntity<Dijagnoza> entity = new HttpEntity<>(dijagnoza);
        ResponseEntity<Dijagnoza> response =
                template.exchange("/dijagnoza/" + highestId, HttpMethod.PUT, entity, Dijagnoza.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(dijagnoza.getNaziv(), response.getBody().getNaziv());
        assertEquals(dijagnoza.getOznaka(), response.getBody().getOznaka());
    }

    @Test
    @Order(7)
    void testDeleteDijagnoza() {
        int highestId = getHighestId();
        ResponseEntity<String> response =
                template.exchange("/dijagnoza/" + highestId, HttpMethod.DELETE, null, String.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("Diagnosis with id: " + highestId + " has been deleted", response.getBody());

        ResponseEntity<String> responseGet =
                template.exchange("/dijagnoza/id/" + highestId, HttpMethod.GET, null, String.class);
        assertEquals(404, responseGet.getStatusCode().value());
        assertEquals("No diagnosis found with id: " + highestId, responseGet.getBody());
    }
}
