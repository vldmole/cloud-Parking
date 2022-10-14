package spring.cloudparking.restApi.parking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.cloudparking.restApi.parking.dto.CreateParkingDto;
import spring.cloudparking.restApi.parking.dto.ParkingDto;
import spring.cloudparking.restApi.parking.dto.ParkingDtoHelper;
import spring.cloudparking.system.parking.model.Parking;
import spring.cloudparking.system.parking.service.ParkingService;

import javax.websocket.server.PathParam;
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
    public ResponseEntity<List<ParkingDto>> get()
    {
        List<Parking> parkingList = parkingService.findAll();

        List<ParkingDto> parkingDtoList = this.parkingDtoHelper.dtoFromParking(parkingList);

        return ResponseEntity.ok(parkingDtoList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDto> getById(@PathVariable String id)
    {
        Parking parking = this.parkingService.getById(id);
        ParkingDto dto  = this.parkingDtoHelper.dtoFromParking(parking);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ParkingDto> post(@RequestBody CreateParkingDto dto)
    {
        Parking parking = parkingDtoHelper.parkingFromDto(dto);
        Parking newParking = parkingService.add(parking);

        ParkingDto resultDto = this.parkingDtoHelper.dtoFromParking(newParking);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultDto);
    }
}
