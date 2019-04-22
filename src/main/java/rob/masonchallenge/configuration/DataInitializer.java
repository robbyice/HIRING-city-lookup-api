package rob.masonchallenge.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import rob.masonchallenge.city.CityRepository;

import java.io.File;
import java.io.FileNotFoundException;

@Configuration
public class DataInitializer {

    @Bean
    public CityRepository cityRepository() throws FileNotFoundException {
        File cityData = ResourceUtils.getFile("classpath:cities15000.txt");

        return new CityRepository(cityData);
    }
}
