package models;

import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;
import javax.persistence.*;
import play.mvc.*;
import java.util.*;
import play.data.DynamicForm;

/**
 * This model contains all the general information data contents of the SoMeR. It can only be obtained via
 * a cross reference between its information and a platform.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
@DocStore
@Entity
@Table(name = "information_content")
public class InformationContent extends BaseDomain implements PathBindable<InformationContent> {
    /* These are all attributes that are mapped for the database. */
    @Id
    public Long informationContentId;
    @Column(columnDefinition = "TEXT")
    public String informationContent;
    @DocEmbedded
    @ManyToOne
    @JoinColumn(name = "information_information_id")
    public Information information;
    @DocEmbedded
    @ManyToOne
    @JoinColumn(name = "platform_platform_id")
    public Platform platform;

    /* ----- Constructors ----- */
    public InformationContent() {
    }

    public InformationContent(Long informationContentId, String informationContent, Information information, Platform platform) {
        this.informationContentId = informationContentId;
        this.informationContent = informationContent;
        this.information = information;
        this.platform = platform;
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
     *
     * @param informationContentId Unique identifier of information content.
     * @return Model object filled with data from the information_content table.
     */
    public static InformationContent findByInformationContentId(Long informationContentId) {
        return Ebean.find(InformationContent.class, informationContentId);
    }

    /**
     * This method uses the functionContentId of the functionContent entity to find data in the database and bind
     * it to the corresponding model object.
     */
    public static List<InformationContent> findAllByPlatformId(Long platformId) {
        List<InformationContent> informationContents = Ebean.find(InformationContent.class).where()
                .eq("platform_platform_id", platformId).findList();
        ArrayList<InformationContent> informationContentArrayList = new ArrayList<InformationContent>(informationContents);
        //System.out.println("InformationContent ArrayList: " + informationContentArrayList);
        return informationContentArrayList;
    }

    //Get list of informationIds -> run through Id

    public static List<InformationContent> formToInformationContents(DynamicForm requestForm) {
        List<InformationContent> informationContents;
        Long platformId = Long.parseLong(requestForm.get("platformId"));
        List<Information> informationIds = Information.findAllInformationIds();
        for(Information currentElement: informationIds) {
            InformationContent informationContent = new InformationContent();
            Long currentInformationId = currentElement.getInformationId();
            String requestInformationContent = requestForm.get("informationId(" + currentInformationId + ")");
            System.out.println("" + requestInformationContent);

        }
        //for(Long currentElement: informationIds) {
//
 //       }

        return null;
    }

    public static void saveInformationContent(List<InformationContent> informationContents) {
        for (InformationContent currentElement : informationContents) {
            if (currentElement != null) {
                Ebean.save(currentElement);
            }
        }

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

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String toString() {
        return "InformationContent{" +
                "informationContentId=" + informationContentId +
                ", informationContent='" + informationContent + '\'' +
                ", information=" + information +
                ", platform=" + platform +
                '}';
    }
}
