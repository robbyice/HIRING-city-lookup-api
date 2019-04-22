package rob.masonchallenge.city;

public class City {
    private String name;
    private String alternateName;
    private double latitude;
    private double longitude;

    public City(String name, String alternateName, double latitude, double longitude) {
        this.name = name;
        this.alternateName = alternateName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getAlternateName() {
        return alternateName;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
