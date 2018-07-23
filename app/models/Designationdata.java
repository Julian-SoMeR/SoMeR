package models;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints;

import io.ebean.Model;
import javax.persistence.*;

@Entity
public class Designationdata {
    @Id
    public Long designationdataId;
    public String designationdataName;
    public String designationdataCategory;
    public String designationdataSubcategory;
    @Column(columnDefinition = "TEXT")
    public String designationdataDescription;

    public List<Valuedata> valuedata = new ArrayList<>();

    public String toString() {
        return String.format("%s - %s", designationdataId, designationdataName);
    }
}
