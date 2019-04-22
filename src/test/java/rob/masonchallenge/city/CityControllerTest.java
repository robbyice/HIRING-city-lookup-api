package rob.masonchallenge.city;

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

    @MockBean CityRepository mockCityRepository;

    @Test
    public void GET_suggestions_returnsListOfMatchingCities() throws Exception {
        List<City> expectedCities = asList(
                new City("name1", "alternateName1", 10.0, 20.0),
                new City("name2", "alternateName2", 20.0, 40.0)
        );
        when(mockCityRepository.findMatchingCities(anyString())).thenReturn(expectedCities);

        mockMvc.perform(get("/suggestions").param("q", "searchTerm"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content()
                        .json("[" +
                                "{\"name\":\"name1\",\"alternateName\":\"alternateName1\",\"latitude\":10.0,\"longitude\":20.0}," +
                                "{\"name\":\"name2\",\"alternateName\":\"alternateName2\",\"latitude\":20.0,\"longitude\":40.0}" +
                                "]"));

        verify(mockCityRepository).findMatchingCities("searchTerm");
    }
}