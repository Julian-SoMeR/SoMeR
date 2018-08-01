package models;

import java.util.ArrayList;
import java.util.List;
import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;
import javax.persistence.*;
import play.mvc.*;
//import models.finder.DesignationdataFinder;

/**
 *  This model contains all the designation data for the functions/services data of the SoMeR.
 *  The JPA/Ebean annotations are used to tell Play how
 *  to generate the tables, contents and relations of the database and provide evolutions.
 */
@DocStore
@Entity
@Table(name = "designationdata")
public class Designationdata extends BaseDomain implements PathBindable<Designationdata> {

    //public static final DesignationdataFinder find = new DesignationdataFinder();

    /* These are all attributes that are mapped for the database. */
    @Id
    public Long designationdataId;

    @DocSortable
    public String designationName;
    public String designationCategory;
    public String designationSubcategory;
    @Column(columnDefinition = "TEXT")
    public String designationDescription;

    @DocEmbedded
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "designationdata")
    public List<Valuedata> valuedata = new ArrayList<>();


    /* ----- Constructors ----- */
    public Designationdata() {}

    public Designationdata(String designationName, String designationCategory, String designationSubcategory,
                           String designationDescription, List<Valuedata> valuedata) {
        this.designationName = designationName;
        this.designationCategory = designationCategory;
        this.designationSubcategory = designationSubcategory;
        this.designationDescription = designationDescription;
        this.valuedata = valuedata;
    }


    /* Methods necessary for the interface Pathbindable */
    @Override
    public Designationdata bind(String key, String id) {
        Designationdata designationdata = findByDesignationdataId(new Long(id));
        if (designationdata == null) {
            throw new IllegalArgumentException("Value data with desginationdataId " + id + " not found");
        }
        return designationdata;
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(designationdataId);
    }

    @Override
    public String javascriptUnbind() {
        return this.designationName;
    }

    /* ---- Methods ---- */
    /**
     * This method uses the platformdataId of the designationdata entity to find data in the database and bind in to the
     * corresponding model object.
     * @param designationdataId Unique identifier of designationdata.
     * @return Model object filled with data from the designationdata table.
     */
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
