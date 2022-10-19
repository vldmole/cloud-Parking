package spring.cloudparking.system.parking.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Parking
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="Pk_id", unique = true)
    private long id;
    @Column(name="Pk_License", nullable = false)
    private String license;
    @Column(name="Pk_State", nullable = false)
    private String state;
    @Column(name="Pk_model", nullable = false)
    private String model;
    @Column(name="Pk_color", nullable = false)
    private String color;
    @Column(name="Pk_entryDateTime", nullable = false)
    private LocalDateTime entry;
    @Column(name="Pk_exitDateTime", nullable = true)
    private LocalDateTime exit;
    @Column(name="Pk_billDescription", nullable = true)
    private String billDescription;
    @Column(name="Pk_billValue", nullable = true)
    private Float billValue;

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

    public Float getBillValue()
    {
        return billValue;
    }

    public void setBillValue(Float bill)
    {
        this.billValue = bill;
    }

    public String getBillDescription()
    {
        return this.billDescription;
    }
    public void setBillDescription(String billDescription)
    {
        this.billDescription = billDescription;
    }
}
