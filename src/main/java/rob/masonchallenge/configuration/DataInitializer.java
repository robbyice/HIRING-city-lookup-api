package rob.masonchallenge.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import rob.masonchallenge.city.CityRepository;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class DataInitializer {

    final ResourceLoader resourceLoader;

    public DataInitializer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Bean
    public CityRepository cityRepository() throws IOException {
        InputStream cityDataStream = this.resourceLoader.getResource("classpath:cities15000.txt").getInputStream();

        return new CityRepository(cityDataStream);
    }
}
