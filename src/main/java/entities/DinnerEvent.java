package entities;

import dtos.DinnerEventDTO;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dinnerevent")
public class DinnerEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "event_name")
    private String eventname;
    @Column(name = "time")
    private String time;
    @Column(name = "location")
    private String location;
    @Column(name = "dish")
    private String dish;
    @Column(name = "price_pr_person")
    private double priceprperson;

    @OneToMany(mappedBy = "dinnerEvent",cascade = CascadeType.PERSIST)
    List<Assignment> assignments;

    public DinnerEvent(String eventname, String time, String location, String dish, double priceprperson) {
        this.eventname = eventname;
        this.time = time;
        this.location = location;
        this.dish = dish;
        this.priceprperson = priceprperson;
        this.assignments = new ArrayList<>();
    }

    public DinnerEvent(DinnerEventDTO dinnerEventDTO) {
        this.eventname = dinnerEventDTO.getEventname();
        this.time = dinnerEventDTO.getTime();
        this.location = dinnerEventDTO.getLocation();
        this.dish = dinnerEventDTO.getDish();
        this.priceprperson = dinnerEventDTO.getPriceprperson();
    }

    public DinnerEvent() {
    }

    public List<Assignment> getAssignments() {
        return assignments;
    }

    public void addAssignment(Assignment assignment){
        this.assignments.add(assignment);
        if (assignment != null){
            assignment.setDinnerEvent(this);
        }
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