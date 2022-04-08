package uws.ah.bustrackergeo;

public class BusStop {
    private CustomLatLng LatitudeLongitude;
    private String Name;

    public BusStop() {
    }

    public BusStop(CustomLatLng latitudeLongitude, String name) {
        this.LatitudeLongitude = latitudeLongitude;
        Name = name;
    }

    public CustomLatLng getLatitudeLongitude() {
        return LatitudeLongitude;
    }

    public void setLatitudeLongitude(CustomLatLng latitudeLongitude) {
        LatitudeLongitude = latitudeLongitude;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
