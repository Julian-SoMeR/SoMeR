package models;

import java.util.ArrayList;
import java.util.List;
import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;
import javax.persistence.*;
import play.mvc.*;

/**
 *  This model contains all the general information data contents of the SoMeR. It can only be obtained via
 *  a cross reference between its information and a platform.
 *  The JPA/Ebean annotations are used to tell Play how
 *  to generate the tables, contents and relations of the database and provide evolutions.
 */
//@DocStore
@Entity
@Table(name = "information_content")
public class InformationContent extends BaseDomain implements PathBindable<InformationContent>  {
    /* These are all attributes that are mapped for the database. */
    @Id
    public Long informationContentId;
    @Column(columnDefinition = "TEXT")
    public String informationContent;
    //@DocEmbedded
    @ManyToOne
    public Information information;
    //@DocEmbedded
    @ManyToOne
    public Platformdata platformdata;

    /* ----- Constructors ----- */
    public InformationContent() {}

    public InformationContent(Long informationContentId, String informationContent, Information information, Platformdata platformdata) {
        this.informationContentId = informationContentId;
        this.informationContent = informationContent;
        this.information = information;
        this.platformdata = platformdata;
    }

    /* Methods necessary for the interface Pathbindable */
    @Override
    public InformationContent bind(String key, String id) {
        InformationContent informationContent = findByInformationContentId(new Long(id));
        if (informationContent == null) {
            throw new IllegalArgumentException("Information content data with informationContentId " + id + " not found");
        }
        return informationContent;
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(informationContentId);
    }

    @Override
    public String javascriptUnbind() {
        return this.informationContent;
    }


    /* ---- Methods ---- */
    /**
     * This method uses the functionContentId of the functionContent entity to find data in the database and bind
     * it to the corresponding model object.
     * @param informationContentId Unique identifier of information content.
     * @return Model object filled with data from the information_content table.
     */
    public static InformationContent findByInformationContentId(Long informationContentId) {
            return Ebean.find(InformationContent.class, informationContentId);
    }

    /* ---- Getters, Setters, ToString Method ---- */

    public Long getInformationContentId() {
        return informationContentId;
    }

    public void setInformationContentId(Long informationContentId) {
        this.informationContentId = informationContentId;
    }

    public String getInformationContent() {
        return informationContent;
    }

    public void setInformationContent(String informationContent) {
        this.informationContent = informationContent;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public Platformdata getPlatformdata() {
        return platformdata;
    }

    public void setPlatformdata(Platformdata platformdata) {
        this.platformdata = platformdata;
    }

    public String toString() {
        return "InformationContent{" +
                "informationContentId=" + informationContentId +
                ", information=" + information +
                ", platformdata=" + platformdata +
                '}';
    }
}
