package rob.masonchallenge;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rob.masonchallenge.city.City;
import rob.masonchallenge.city.CityRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MasonChallengeApplicationTests {

    @Autowired
    CityRepository cityRepository;

    @Test
    public void loadsFileOnStartup() {
        assertThat(cityRepository.getCities().size()).isEqualTo(24201);
    }

    @Test
    public void findMatchingCities_returnsListOfCities_thatStartWithSearchTerm() {
        assertThat(cityRepository.findMatchingCities("Sea").stream().map((City::getName)))
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
}
