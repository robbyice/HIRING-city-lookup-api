package rob.masonchallenge.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {
    @Autowired CityRepository cityRepository;

    @GetMapping("/suggestions")
    public List<City> getCitySuggestions(@RequestParam String q) {
        return cityRepository.findMatchingCities(q);
    }

}
