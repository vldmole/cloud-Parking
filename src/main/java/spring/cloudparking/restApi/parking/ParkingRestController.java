package spring.cloudparking.restApi.parking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.*;
import spring.cloudparking.system.facade.ParkingFacade;
import spring.cloudparking.system.facade.parking.dto.CreateParkingDto;
import spring.cloudparking.system.facade.parking.dto.ParkingDto;

import java.util.List;

@RestController
//@RequestMapping({"/parking", "/Parking", "/PARKING"})
@EnableWebSecurity
public class ParkingRestController
{
    ParkingFacade parkingFacade;

    //------------------------------------------------------------------------------------
    @Autowired
    public ParkingRestController(ParkingFacade parkingFacade)
    {
        this.parkingFacade = parkingFacade;
    }

    //------------------------------------------------------------------------------------
    @GetMapping("/parking")
    public ResponseEntity<List<ParkingDto>> get()
    {
        List<ParkingDto> parkingDtoList = parkingFacade.findAll();

        return ResponseEntity.ok(parkingDtoList);
    }

    //------------------------------------------------------------------------------------
    @GetMapping("/parking/{id}")
    public ResponseEntity<ParkingDto> getById(@PathVariable Long id)
    {
        ParkingDto parkingDto = parkingFacade.getById(id);

        return ResponseEntity.ok(parkingDto);
    }

    //------------------------------------------------------------------------------------
    @PostMapping("/parking/checkin")
    public ResponseEntity<ParkingDto> postCheckin(@RequestBody CreateParkingDto dto)
    {
        System.out.println("checkin");
        ParkingDto newParkingDto = parkingFacade.checkin(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newParkingDto);
    }

    //------------------------------------------------------------------------------------
    @PostMapping("/parking/{id}/checkout")
    public ResponseEntity<ParkingDto> postCheckout(@PathVariable Long id)
    {
        ParkingDto parkingDto = parkingFacade.checkout(id);

        return ResponseEntity.ok(parkingDto);
    }

    //------------------------------------------------------------------------------------
    @PostMapping("/parking/checkout")
    public ResponseEntity<ParkingDto> postCheckout(@RequestBody ParkingDto dto)
    {
        ParkingDto parkingDto = parkingFacade.checkout(dto.getId());

        return ResponseEntity.ok(parkingDto);
    }


    //------------------------------------------------------------------------------------
    @PutMapping("/parking/{id}")
    public ResponseEntity<ParkingDto> put(@PathVariable Long id, @RequestBody ParkingDto parkingDto)
    {
        ParkingDto updatedParkingDto = this.parkingFacade.updateById(id, parkingDto);

        return ResponseEntity.ok(updatedParkingDto);
    }

    //------------------------------------------------------------------------------------
    @DeleteMapping("/parking/{id}")
    public ResponseEntity<ParkingDto> delete(@PathVariable Long id)
    {
        ParkingDto parkingDto = this.parkingFacade.deleteById(id);

        return ResponseEntity.ok(parkingDto);
    }

    //------------------------------------------------------------------------------------
    @GetMapping("/parking/BillingTypes")
    public ResponseEntity<String[]> getBillingTypes()
    {
        return ResponseEntity.ok(this.parkingFacade.getBillingTypes());
    }
}
