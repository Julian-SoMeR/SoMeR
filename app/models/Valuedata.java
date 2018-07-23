package models;

import java.util.ArrayList;
import java.util.List;
import play.data.validation.Constraints;

import io.ebean.*;
import javax.persistence.*;


@Entity
public class Valuedata extends Model {
    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
     to generate the tables, contents and relations of the database and provide evolutions. */
    @Column(columnDefinition = "TEXT")
    public String valuedataContent;

    @Id
    public Long valuedataId;

    @ManyToOne
    public Designationdata designationdata;
    @ManyToOne
    public Platformdata platformdata;


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
