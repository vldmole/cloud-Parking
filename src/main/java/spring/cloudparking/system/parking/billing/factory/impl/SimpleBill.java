package spring.cloudparking.system.parking.billing.factory.impl;

import spring.cloudparking.system.parking.billing.Bill;

import java.time.LocalDateTime;
import java.util.*;

class SimpleBill implements Bill
{
    private LocalDateTime startDateTime = null;
    private LocalDateTime endDateTime = null;
    private Map<String, Float> descriptionMap = new HashMap<>();

    //--------------------------------------------------------------------------
    public SimpleBill(LocalDateTime startDateTime, LocalDateTime endDateTime)
    {
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    //--------------------------------------------------------------------------
    public void addDescriptionItem(String itemDescription, float itemValue)
    {
        this.descriptionMap.put(itemDescription.toUpperCase(), itemValue);
    }

    //--------------------------------------------------------------------------
    public void removeDescriptionItem(String itemDescription)
    {
        this.descriptionMap.remove(itemDescription.toUpperCase());
    }

    //--------------------------------------------------------------------------
    @Override
    public LocalDateTime getStartDateTime()
    {
        return startDateTime;
    }

    //--------------------------------------------------------------------------
    @Override
    public LocalDateTime getEndDateTime()
    {
        return endDateTime;
    }

    //--------------------------------------------------------------------------
    @Override
    public float getValue()
    {
        float sum = 0.0f;

        for(float value : descriptionMap.values())
            sum += value;

        return sum;
    }

    //--------------------------------------------------------------------------
    @Override
    public Map<String, Float> getDescription()
    {
        return Collections.unmodifiableMap(descriptionMap);
    }
}
