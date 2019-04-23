package rob.masonchallenge.city;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
public class CityController {
    private final CityMatchingService cityMatchingService;

    public CityController(CityMatchingService cityMatchingService) {
        this.cityMatchingService = cityMatchingService;
    }

    @GetMapping(value = "/suggestions", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<CityMatchResult> getCitySuggestions(@RequestParam String q,
                                                    @RequestParam Optional<Double> latitude,
                                                    @RequestParam Optional<Double> longitude,
                                                    HttpServletResponse response) throws IOException {

        if (latitude.isPresent() && longitude.isPresent()) {
            return cityMatchingService.findMatchingCitiesNearLocation(q, latitude.get(), longitude.get());
        } else if (!latitude.isPresent() && !longitude.isPresent()) {
            return cityMatchingService.findMatchingCities(q);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "When either latitude and longitude are " +
                    "specified, both are required.");
            return null;
        }
    }
}
