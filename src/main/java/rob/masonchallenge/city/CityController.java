package rob.masonchallenge.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

@RestController
public class CityController {
    @Autowired private CityMatchingService cityMatchingService;

    @GetMapping("/suggestions")
    public List<CityMatchResult> getCitySuggestions(@RequestParam String q,
                                                    @RequestParam Optional<Double> latitude,
                                                    @RequestParam Optional<Double> longitude,
                                                    HttpServletResponse response) {

        if (latitude.isPresent() && longitude.isPresent()) {
            return cityMatchingService.findMatchingCitiesNearLocation(q, latitude.get(), longitude.get());
        } else if (!latitude.isPresent() && !longitude.isPresent()) {
            return cityMatchingService.findMatchingCities(q);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
    }
}
