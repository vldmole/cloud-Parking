package spring.cloudparking.system.parking.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Parking
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Pk_id")
    private Long id;
    @Column(name="Pk_License", nullable = false)
    private String license;
    @Column(name="Pk_State", nullable = false)
    private String state;
    @Column(name="Pk_model", nullable = false)
    private String model;
    @Column(name="Pk_color", nullable = false)
    private String color;
    @Column(name="Pk_EntryDateTime", nullable = false)
    private LocalDateTime entry;
    private LocalDateTime exit;
    private Double bill;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }

    public String getLicense()
    {
        return license;
    }

    public void setLicense(String license)
    {
        this.license = license;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public LocalDateTime getEntry()
    {
        return entry;
    }

    public void setEntry(LocalDateTime entry)
    {
        this.entry = entry;
    }

    public LocalDateTime getExit()
    {
        return exit;
    }

    public void setExit(LocalDateTime exit)
    {
        this.exit = exit;
    }

    public Double getBill()
    {
        return bill;
    }

    public void setBill(Double bill)
    {
        this.bill = bill;
    }
}
