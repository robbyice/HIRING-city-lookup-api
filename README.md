# City Lookup Service

(Backend developer coding project, inspired by https://github.com/busbud/coding-challenge-backend-c)

### Notes

For this challenge I elected to write a Spring Boot application - it is pretty quick to set up a basic web application.
Since I chose not to use a database, it was also easy to use Spring to package the cities data text file inside
the JAR being deployed to AWS. On startup the application reads from the cities15000.txt Geoname data file and loads the 
city data into memory. For this challenge and data at this scale this seemed fine - it only caused a minor slowdown in 
startup and response times even for the larger (1.4GB) data files. Naturally with further specfications clarified and
required by the challenge a more intentional solution will be required.

Initially I had intended to deploy the app onto PWS, but ended up using AWS ElasticBeanstalk instead.

**Test Curl Commands**
```
curl http://masonchallenge-env.xrgy8jsigy.us-east-2.elasticbeanstalk.com/suggestions?q=SEA
curl http://masonchallenge-env.xrgy8jsigy.us-east-2.elasticbeanstalk.com/suggestions?q=SEA&latitude=47.5&longitude=-121.33
```

**Running Locally**
```
./gradlew bootRun
```

**Running Tests**
```
./gradlew test
```


### Outstanding Questions
- I am not sure how much of the total available GeoName data we are expected to use - I ended up using the one for US 
cities with population > 15,000.
- It is unclear how the score for matches is supposed to be calculated, or if it is present at all when latitude and
longitude are not specified. I omitted score for non-location-based queries. For calculating the score I just came up
with some random algorithm.
- In the source data, cities can have a large number of "alternateNames". The specification only mentions that match 
results have a name to help disambiguate - it does not make clear whether this is a list of alternate names, or a single 
of many alternate names, or the regular city name, etc. I ended up just leaving the field as a comma-delimited list of 
all alternateNames provided for the city.

### References
- Geonames provides city lists Canada and the USA. See http://download.geonames.org/export/dump/readme.txt
- https://docs.spring.io/spring-boot/docs/current/reference/html/index.html
- https://medium.com/@ryanzhou7/running-spring-boot-on-amazon-web-services-for-free-f3b0aeec809
- https://stackoverflow.com/questions/3694380/calculating-distance-between-two-points-using-latitude-longitude
- https://smarterco.de/java-load-file-from-classpath-in-spring-boot/