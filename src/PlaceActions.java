import java.util.ArrayList;
public interface PlaceActions {
    public int getDegree();
    public ArrayList<Thing> getEnvironment();
    public Place.Weather getStateWeather();
    public Season getSeason();
}
