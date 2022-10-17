package spring.cloudparking.restApi.parking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import spring.cloudparking.restApi.parking.dto.CreateParkingDto;
import spring.cloudparking.restApi.parking.dto.ParkingDto;
import spring.cloudparking.restApi.parking.dto.ParkingDtoHelper;
import spring.cloudparking.system.parking.model.Parking;
import spring.cloudparking.system.parking.service.ParkingService;

import java.util.List;

@RestController
@RequestMapping({"/parking", "/Parking", "/PARKING"})
public class ParkingRestController
{
    ParkingService parkingService;
    ParkingDtoHelper parkingDtoHelper;

    @Autowired
    public ParkingRestController(ParkingService parkingService, ParkingDtoHelper parkingDtoHelper)
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
    public ResponseEntity<ParkingDto> getById(@PathVariable Long id)
    {
        try
        {
            Parking parking = this.parkingService.getById(id);
            ParkingDto dto = this.parkingDtoHelper.dtoFromParking(parking);
            return ResponseEntity.ok(dto);
        }
        catch (RuntimeException re)
       {
          throw new ResponseStatusException(HttpStatus.NOT_FOUND, re.getMessage());
       }
    }

    @PostMapping
    public ResponseEntity<ParkingDto> post(@RequestBody CreateParkingDto dto)
    {
        Parking parking = parkingDtoHelper.parkingFromDto(dto);
        Parking newParking = parkingService.add(parking);

        ParkingDto resultDto = this.parkingDtoHelper.dtoFromParking(newParking);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultDto);
    }

    @PutMapping("{id}")
    public ResponseEntity<ParkingDto> put(@PathVariable Long id, @RequestBody ParkingDto dto)
    {
        Parking parking = this.parkingDtoHelper.parkingFromDto(dto);

        Parking updatedParking = this.parkingService.updateById(id, parking);

        ParkingDto resultParkingDto = this.parkingDtoHelper.dtoFromParking(updatedParking);

        return ResponseEntity.ok(resultParkingDto);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ParkingDto> delete(@PathVariable Long id)
    {
        Parking parking = this.parkingService.parkingExit(id);

        ParkingDto dto = this.parkingDtoHelper.dtoFromParking(parking);

        return ResponseEntity.ok(dto);
    }
}
