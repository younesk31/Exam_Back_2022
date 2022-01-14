package dtos;

import entities.Assignment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AssignmentDTO {

    private String familyName;
    private Date created;
    private String contactInfo;

    public static List<AssignmentDTO> getDtos(List<Assignment> a){
        List<AssignmentDTO> assignmentDTOS = new ArrayList();
        a.forEach(dtotoentity -> assignmentDTOS.add(new AssignmentDTO(dtotoentity)));
        return assignmentDTOS;
    }

    public AssignmentDTO(Assignment assignment) {
        this.familyName = assignment.getFamilyName();
        this.created = assignment.getCreated();
        this.contactInfo = assignment.getContactInfo();
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
