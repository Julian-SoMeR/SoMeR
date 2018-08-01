package models;

import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;
import javax.persistence.*;
import play.mvc.*;

/**
 *  This model contains all the functions/services data values of the SoMeR. It can only be obtained via
 *  a cross reference between its designating data and a platform.
 *  The JPA/Ebean annotations are used to tell Play how
 *  to generate the tables, contents and relations of the database and provide evolutions.
 */
@DocStore
@Entity
@Table(name = "valuedata")
public class Valuedata extends BaseDomain implements PathBindable<Valuedata> {

    //EbeanServer server = Ebean.getDefaultServer();
    //public static ValuedataFinder find = new ValuedataFinder();

    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
     to generate the tables, contents and relations of the database and provide evolutions. */
    @Column(columnDefinition = "TEXT")
    public String valuedataContent;

    @Id
    public Long valuedataId;

    @DocEmbedded
    @ManyToOne
    public Designationdata designationdata;

    @DocEmbedded
    @ManyToOne
    public Platformdata platformdata;

    /* ----- Constructors ----- */
    public Valuedata() {}

    public Valuedata(String valuedataContent, Designationdata designationdata, Platformdata platformdata) {
        this.valuedataContent = valuedataContent;
        this.designationdata = designationdata;
        this.platformdata = platformdata;
    }

    /* Methods necessary for the interface Pathbindable */
    @Override
    public Valuedata bind(String key, String id) {
        Valuedata valuedata = findByValuedataId(new Long(id));
        if (valuedata == null) {
            throw new IllegalArgumentException("Value data with platformdataId " + id + " not found");
        }
        return valuedata;
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(valuedataId);
    }

    @Override
    public String javascriptUnbind() {
        return this.valuedataContent;
    }

    /* ---- Methods ---- */

    /**
     * This method uses the platformdataId of the valuedata entity to find data in the database and bind in to the
     * corresponding model object.
     * @param valuedataId Unique identifier of valuedata.
     * @return Model object filled with data from the valuedata table.
     */
    public static Valuedata findByValuedataId(Long valuedataId) {
        return Ebean.find(Valuedata.class, valuedataId);
    }


    /* ---- Getters, Setters, ToString Method ---- */
    public String getValuedataContent() {
        return valuedataContent;
    }

    public void setValuedataContent(String valuedataContent) {
        this.valuedataContent = valuedataContent;
    }

    public Long getValuedataId() {
        return valuedataId;
    }

    public void setValuedataId(Long valuedataId) {
        this.valuedataId = valuedataId;
    }

    public Designationdata getDesignationdata() {
        return designationdata;
    }

    public void setDesignationdata(Designationdata designationdata) {
        this.designationdata = designationdata;
    }

    public Platformdata getPlatformdata() {
        return platformdata;
    }

    public void setPlatformdata(Platformdata platformdata) {
        this.platformdata = platformdata;
    }

    public String toString() {
        return String.format("%s - %s - %s", valuedataContent, platformdata, designationdata);
    }
}
