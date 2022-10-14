package spring.cloudparking.restApi.parking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring.cloudparking.restApi.parking.dto.ParkingDto;
import spring.cloudparking.restApi.parking.dto.ParkingDtoHelper;
import spring.cloudparking.system.parking.model.Parking;
import spring.cloudparking.system.parking.service.ParkingService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping({"/parking", "/Parking", "/PARKING"})
public class ParkingController
{
    ParkingService parkingService;
    ParkingDtoHelper parkingDtoHelper;

    @Autowired
    public ParkingController(ParkingService parkingService, ParkingDtoHelper parkingDtoHelper)
    {
        this.parkingService = parkingService;
        this.parkingDtoHelper = parkingDtoHelper;
    }

    @GetMapping
    public List<ParkingDto> get()
    {
        List<Parking> parkingList = parkingService.findAll();

        List<ParkingDto> parkingDtoList = this.parkingDtoHelper.dtoFromParking(parkingList);

        return parkingDtoList;
    }

    @PostMapping
    public ParkingDto post(ParkingDto dto)
    {
        Parking parking = parkingDtoHelper.parkingFromDto(dto);
        parking = parkingService.add(parking);

        dto = this.parkingDtoHelper.dtoFromParking(parking);
        return dto;
    }
}
