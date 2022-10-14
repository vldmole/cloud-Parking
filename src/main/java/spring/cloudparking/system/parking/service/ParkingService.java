package spring.cloudparking.system.parking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import spring.cloudparking.system.exception.ParkingExceptionFactory;
import spring.cloudparking.system.parking.model.Parking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingService
{
    ParkingExceptionFactory parkingExceptionFactory;

    //-------------------------------------------------------------------------------------
    @Autowired
    public ParkingService(ParkingExceptionFactory parkingExceptionFactory)
    {
        this.parkingExceptionFactory = parkingExceptionFactory;
    }

    //-------------------------------------------------------------------------------------
    public final
    List<Parking> findAll()
    {
        return lstParking;
    }

    //-------------------------------------------------------------------------------------
    public final
    Parking add(@NonNull Parking parking)
    {
        parking.setId(nextId());
        parking.setEntry(LocalDateTime.now());
        lstParking.add(parking);

        return parking;
    }

    //-------------------------------------------------------------------------------------
    public final
    Parking getById(@NonNull String id)
    {
        List<Parking> filtered = lstParking.stream()
                .filter((parking -> parking.getId().equals(id)))
                .collect(Collectors.toList());

        if(filtered.size() == 0)
            throw this.parkingExceptionFactory.createNotFoundException("Parking", "id", id);

        return filtered.get(0);
    }

    //-------------------------------------------------------------------------------------
    public final
    boolean existsById(@NonNull String id)
    {
        for(Parking parking : lstParking)
            if(parking.getId().equals(id))
                return true;
        return false;
    }

    //-------------------------------------------------------------------------------------
    public final
    Parking updateById(@NonNull String id, @NonNull Parking parking)
    {
        Parking oldParking = this.getById(id);
        oldParking.setEntry(parking.getEntry());
        oldParking.setColor(parking.getColor());
        oldParking.setState(parking.getState());
        oldParking.setModel(parking.getModel());
        oldParking.setBill(parking.getBill());

        return oldParking;
    }

    //-------------------------------------------------------------------------------------
    public final
    Parking parkingExit(@NonNull String id)
    {
        Parking parking = this.getById(id);
        parking.setExit(LocalDateTime.now());
        parking.setBill(getBill(parking.getEntry(), parking.getExit()));

        return parking;
    }

    //------------------------------------------------------------------------------------
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

        return ( 10 * hours + (10/4 * (minutes/15)) );
    }
}
