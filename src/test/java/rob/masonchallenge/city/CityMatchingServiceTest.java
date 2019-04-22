package rob.masonchallenge.city;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CityMatchingServiceTest {
    @Mock CityRepository mockCityRepository;

    private CityMatchingService subject;
    private City city1;
    private City city2;

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

        when(mockCityRepository.findMatchingCities(anyString())).thenReturn(asList(city1, city2));

        subject = new CityMatchingService(mockCityRepository);
    }

    @Test
    public void findMatchingCities_returnsMatchingCities() {
        List<CityMatchResult> result = subject.findMatchingCities("searchTerm");

        List<CityMatchResult> expectedCityMatches = asList(
                CityMatchResult.builder().city(city1).build(),
                CityMatchResult.builder().city(city2).build()
        );

        assertThat(result).isEqualTo(expectedCityMatches);
    }

    @Test
    public void findMatchingCitiesNearLocation_returnsMatchingCities_orderedByDistanceToLocation() {
        List<CityMatchResult> result = subject.findMatchingCitiesNearLocation("searchTerm", 8.0, 16.0);

        double city1ExpectedDistance = CityMatchingService.calculateDistanceInMeters(city1, 8.0, 16.0);
        double city2ExpectedDistance = CityMatchingService.calculateDistanceInMeters(city2, 8.0, 16.0);

        CityMatchResult expectedCity1Match = CityMatchResult.builder()
                .city(city1)
                .distance(city1ExpectedDistance)
                .build();
        CityMatchResult expectedCity2Match = CityMatchResult.builder()
                .city(city2)
                .distance(city2ExpectedDistance)
                .build();

        assertThat(result).containsExactly(expectedCity2Match, expectedCity1Match);
    }
}