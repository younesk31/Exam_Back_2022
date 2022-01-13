package dtos;

import entities.Assignment;
import entities.DinnerEvent;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DinnerEventDTO {

    private Integer id;
    private String eventname;
    private String time;
    private String location;
    private String dish;
    private double priceprperson;

    public static List<DinnerEventDTO> getDtos(List<DinnerEvent> d){
        List<DinnerEventDTO> dinnerEventDTOS = new ArrayList();
        d.forEach(dtotoentity -> dinnerEventDTOS.add(new DinnerEventDTO(dtotoentity)));
        return dinnerEventDTOS;
    }

    public DinnerEventDTO(DinnerEvent dinnerEvent) {
        this.id = dinnerEvent.getId();
        this.eventname = dinnerEvent.getEventname();
        this.time = dinnerEvent.getTime();
        this.location = dinnerEvent.getLocation();
        this.dish = dinnerEvent.getDish();
        this.priceprperson = dinnerEvent.getPriceprperson();
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    public double getPriceprperson() {
        return priceprperson;
    }

    public void setPriceprperson(double priceprperson) {
        this.priceprperson = priceprperson;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
