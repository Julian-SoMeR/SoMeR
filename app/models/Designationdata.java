package models;

import java.util.ArrayList;
import java.util.List;
import play.data.validation.Constraints;

import io.ebean.*;
import javax.persistence.*;

@Entity
public class Designationdata {
    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
     to generate the tables, contents and relations of the database and provide evolutions. */
    @Id
    public Long designationdataId;
    public String designationName;
    public String designationCategory;
    public String designationSubcategory;
    @Column(columnDefinition = "TEXT")
    public String designationDescription;

    @OneToMany(cascade=CascadeType.ALL)
    public List<Valuedata> valuedata = new ArrayList<>();


    public static Designationdata findByDesignationdataId(Long designationdataId) {
        return Ebean.find(Designationdata.class, designationdataId);
    }


    /* ---- Getters, Setters, ToString Method ---- */
    public Long getDesignationdataId() {
        return designationdataId;
    }

    public void setDesignationdataId(Long designationdataId) {
        this.designationdataId = designationdataId;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getDesignationCategory() {
        return designationCategory;
    }

    public void setDesignationCategory(String designationCategory) {
        this.designationCategory = designationCategory;
    }

    public String getDesignationSubcategory() {
        return designationSubcategory;
    }

    public void setDesignationSubcategory(String designationSubcategory) {
        this.designationSubcategory = designationSubcategory;
    }

    public String getDesignationDescription() {
        return designationDescription;
    }

    public void setDesignationDescription(String designationDescription) {
        this.designationDescription = designationDescription;
    }

    public List<Valuedata> getValuedata() {
        return valuedata;
    }

    public void setValuedata(List<Valuedata> valuedata) {
        this.valuedata = valuedata;
    }

    public String toString() {
        return String.format("%s - %s", designationdataId, designationName);
    }
}
