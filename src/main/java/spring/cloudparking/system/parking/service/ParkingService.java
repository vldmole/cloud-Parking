package spring.cloudparking.system.parking.service;

import org.springframework.stereotype.Service;
import spring.cloudparking.system.parking.model.Parking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ParkingService
{

    public final
    List<Parking> findAll()
    {
        return lstParking;
    }

    public final
    Parking add(Parking parking)
    {
        parking.setId(nextId());
        lstParking.add(parking);

        return parking;
    }

    static int idCounter = 0;
    static String nextId()
    {
        return "" + ++idCounter;
    };
    static private List<Parking> lstParking = new ArrayList<>(Arrays.asList(
            createParking("Fusca", "Pr", "1975", "preta"),
            createParking( "Variant", "Pr", "1972", "branca"),
            createParking("Brasilia", "Pr", "1980", "azul"),
            createParking( "Scort", "Pr", "1995", "prata")
    ));

    static Parking createParking(String license, String state,
                                 String model, String color)
    {
        Parking park = new Parking();

        park.setId(nextId());
        park.setLicense(license);
        park.setState(state);
        park.setModel(model);
        park.setColor(color);
        park.setEntry(LocalDateTime.now().minus(((int)(Math.random()*12)), ChronoUnit.HOURS));
        park.setExit(LocalDateTime.now());
        park.setBill(getBill(park.getEntry(), park.getExit()));

        return park;
    }

    private static double getBill(LocalDateTime dob, LocalDateTime now)
    {
        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        long hours = seconds / 3600;
        long minutes = ((seconds % 3600) / 60);
        return (10 * (hours + (minutes/15)/4.0) );
    }
}
