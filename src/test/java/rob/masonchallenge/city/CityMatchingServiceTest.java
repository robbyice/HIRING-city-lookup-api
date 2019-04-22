package rob.masonchallenge.city;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.DecimalFormat;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityMatchingServiceTest {
    @Mock CityRepository mockCityRepository;

    private CityMatchingService subject;
    private City city1, city2, city3;

    @Before
    public void setUp() {
        city1 = City.builder()
                .name("name1")
                .alternateName("alternateName1")
                .latitude(10.0)
                .longitude(20.0)
                .build();
        city2 = City.builder()
                .name("name2")
                .alternateName("alternateName2")
                .latitude(20.0)
                .longitude(40.0)
                .build();
        city3 = City.builder()
                .name("name3")
                .alternateName("alternateName3")
                .latitude(40.0)
                .longitude(80.0)
                .build();

        when(mockCityRepository.findMatchingCities(anyString())).thenReturn(asList(city1, city2, city3));

        subject = new CityMatchingService(mockCityRepository);
    }

    @Test
    public void findMatchingCities_returnsMatchingCities() {
        List<CityMatchResult> result = subject.findMatchingCities("searchTerm");

        List<CityMatchResult> expectedCityMatches = asList(
                CityMatchResult.builder().city(city1).build(),
                CityMatchResult.builder().city(city2).build(),
                CityMatchResult.builder().city(city3).build()
        );

        assertThat(result).isEqualTo(expectedCityMatches);
    }

    @Test
    public void findMatchingCitiesNearLocation_returnsMatchingCities_orderedByDistanceToLocation_includesScore() {
        List<CityMatchResult> result = subject.findMatchingCitiesNearLocation("searchTerm", 8.0, 16.0);

        double city1ExpectedDistance = CityMatchingService.calculateDistanceInMeters(city1, 8.0, 16.0);
        double city2ExpectedDistance = CityMatchingService.calculateDistanceInMeters(city2, 8.0, 16.0);
        double city3ExpectedDistance = CityMatchingService.calculateDistanceInMeters(city3, 8.0, 16.0);

        CityMatchResult expectedCity1Match = CityMatchResult.builder()
                .city(city1)
                .distance(city1ExpectedDistance)
                .score(1.0)
                .build();

        CityMatchResult expectedCity2Match = CityMatchResult.builder()
                .city(city2)
                .distance(city2ExpectedDistance)
                .score(0.6417313743503897)
                .build();

        CityMatchResult expectedCity3Match = CityMatchResult.builder()
                .city(city3)
                .distance(city3ExpectedDistance)
                .score(0.0)
                .build();

        assertThat(result).containsExactly(expectedCity1Match, expectedCity2Match, expectedCity3Match);
    }
}