package spring.cloudparking.restApi.parking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.cloudparking.system.facade.ParkingFacade;
import spring.cloudparking.system.facade.parking.dto.CreateParkingDto;
import spring.cloudparking.system.facade.parking.dto.ParkingDto;

import java.util.List;

@RestController
@RequestMapping({"/parking", "/Parking", "/PARKING"})
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
    @GetMapping
    public ResponseEntity<List<ParkingDto>> get()
    {
        List<ParkingDto> parkingDtoList = parkingFacade.findAll();

        return ResponseEntity.ok(parkingDtoList);
    }

    //------------------------------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<ParkingDto> getById(@PathVariable Long id)
    {
        ParkingDto parkingDto = parkingFacade.getById(id);

        return ResponseEntity.ok(parkingDto);
    }

    //------------------------------------------------------------------------------------
    @PostMapping("/checkin")
    public ResponseEntity<ParkingDto> postCheckin(@RequestBody CreateParkingDto dto)
    {
        ParkingDto newParkingDto = parkingFacade.checkin(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(newParkingDto);
    }

    //------------------------------------------------------------------------------------
    @PostMapping("/{id}/checkout")
    public ResponseEntity<ParkingDto> postCheckout(@PathVariable Long id)
    {
        ParkingDto parkingDto = parkingFacade.checkout(id);

        return ResponseEntity.ok(parkingDto);
    }

    //------------------------------------------------------------------------------------
    @PostMapping("/checkout")
    public ResponseEntity<ParkingDto> postCheckout(@RequestBody ParkingDto dto)
    {
        ParkingDto parkingDto = parkingFacade.checkout(dto.getId());

        return ResponseEntity.ok(parkingDto);
    }


    //------------------------------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<ParkingDto> put(@PathVariable Long id, @RequestBody ParkingDto parkingDto)
    {
        ParkingDto updatedParkingDto = this.parkingFacade.updateById(id, parkingDto);

        return ResponseEntity.ok(updatedParkingDto);
    }

    //------------------------------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<ParkingDto> delete(@PathVariable Long id)
    {
        ParkingDto parkingDto = this.parkingFacade.deleteById(id);

        return ResponseEntity.ok(parkingDto);
    }

    //------------------------------------------------------------------------------------
    @GetMapping("/BillingTypes")
    public ResponseEntity<String[]> getBillingTypes()
    {
        return ResponseEntity.ok(this.parkingFacade.getBillingTypes());
    }
}
