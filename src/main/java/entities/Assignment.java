package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table(name = "assignment")
@Entity
public class Assignment {
    @Id
    @Basic(optional = false)
    @Column(name = "family_name", nullable = false)
    private String familyName;
    @Column(name = "created")
    @Temporal(TemporalType.DATE)
    private Date created;
    @Column(name = "contact_info", nullable = false)
    private String contactInfo;

    @ManyToMany
    private List<User> users;

    @ManyToOne
    private DinnerEvent dinnerEvent;

    public Assignment()  {}

    public Assignment(String familyName, String contactInfo) {
        this.familyName = familyName;
        this.created = new Date();
        this.contactInfo = contactInfo;
        this.users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public DinnerEvent getDinnerEvent() {
        return dinnerEvent;
    }

    public void setDinnerEvent(DinnerEvent dinnerEvent) {
        this.dinnerEvent = dinnerEvent;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}