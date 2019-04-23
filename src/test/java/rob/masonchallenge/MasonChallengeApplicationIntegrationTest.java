package rob.masonchallenge;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import rob.masonchallenge.city.City;
import rob.masonchallenge.city.CityRepository;

import java.io.File;
import java.nio.file.Files;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MasonChallengeApplicationIntegrationTest {

    @Autowired CityRepository cityRepository;
    @Autowired MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }

    @Test
    public void correctlyLoadsCities15000FileOnStartup() {
        assertThat(cityRepository.getCities().size()).isEqualTo(24201);
    }

    @Test
    public void findMatchingCities_returnsListOfCities_thatStartWithSearchTerm_ignoringCase() {
        assertThat(cityRepository.findMatchingCities("sEa").stream().map((City::getName)))
                .contains("Seaford",
                        "Seabra",
                        "Seaham",
                        "Seaford",
                        "Searcy",
                        "Seabrook",
                        "Seagoville",
                        "Seaford",
                        "Seal Beach",
                        "Seaside",
                        "SeaTac",
                        "Seattle");
    }

    @Test
    public void GET_suggestions() throws Exception {

        String expectedJson = new String(
                Files.readAllBytes(
                        new File("src/test/resources/exampleResponse.json").toPath()
                )
        );

        mockMvc.perform(
                get("/suggestions")
                        .param("q", "sea")
                        .param("latitude", String.valueOf(47.5))
                        .param("longitude", String.valueOf(-121.33)))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json(expectedJson, true));
    }
}
