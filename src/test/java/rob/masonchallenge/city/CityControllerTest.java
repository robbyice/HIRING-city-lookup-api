package rob.masonchallenge.city;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static java.util.Arrays.asList;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CityController.class)
public class CityControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired WebApplicationContext webApplicationContext;

    @MockBean CityMatchingService mockCityMatchingService;
    private List<CityMatchResult> expectedCities;

    @Before
    public void setUp() {
        City city1 = City.builder()
                .name("name1")
                .alternateNames("alternateName1")
                .latitude(10.0)
                .longitude(20.0)
                .build();
        City city2 = City.builder()
                .name("name2")
                .alternateNames("alternateName2")
                .latitude(20.0)
                .longitude(40.0)
                .build();

        expectedCities = asList(
                CityMatchResult.builder()
                        .city(city1)
                        .score(1.0)
                        .distance(10.0)
                        .build(),
                CityMatchResult.builder()
                        .city(city2)
                        .score(0.0)
                        .distance(20.0)
                        .build()
        );
    }

    @Test
    public void GET_suggestions_findsMatchingCities() throws Exception {
        when(mockCityMatchingService.findMatchingCities(anyString())).thenReturn(expectedCities);

        mockMvc.perform(get("/suggestions").param("q", "searchTerm"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content()
                        .json("[" +
                                "{\"name\":\"name1\",\"alternateNames\":\"alternateName1\",\"latitude\":10.0,\"longitude\":20.0}," +
                                "{\"name\":\"name2\",\"alternateNames\":\"alternateName2\",\"latitude\":20.0,\"longitude\":40.0}" +
                                "]"));


        verify(mockCityMatchingService).findMatchingCities("searchTerm");
    }

    @Test
    public void GET_suggestions_findsMatchingCitiesNearLocation() throws Exception {
        when(mockCityMatchingService.findMatchingCitiesNearLocation(anyString(), anyDouble(), anyDouble())).thenReturn(expectedCities);

        mockMvc.perform(get("/suggestions")
                    .param("q", "searchTerm")
                    .param("latitude", "20.0")
                    .param("longitude", "40.0"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content()
                        .json("[" +
                                "{\"name\":\"name1\",\"alternateNames\":\"alternateName1\",\"latitude\":10.0,\"longitude\":20.0, \"score\": 1.0}," +
                                "{\"name\":\"name2\",\"alternateNames\":\"alternateName2\",\"latitude\":20.0,\"longitude\":40.0, \"score\": 0.0}" +
                                "]"));

        verify(mockCityMatchingService).findMatchingCitiesNearLocation("searchTerm", 20.0, 40.0);
    }

    @Test
    public void GET_suggestions_returns400_whenOnlyLatitudeOrLongitudeProvided() throws Exception {
        mockMvc.perform(get("/suggestions")
                .param("q", "searchTerm")
                .param("latitude", "20.0"))
                .andExpect(status().isBadRequest());

        mockMvc.perform(get("/suggestions")
                .param("q", "searchTerm")
                .param("longitude", "40.0"))
                .andExpect(status().isBadRequest());
    }
}