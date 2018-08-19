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
        System.out.println("TEST: " + informationContents);
        ArrayList<InformationContent> informationContentArrayList = new ArrayList<InformationContent>(informationContents);
        //System.out.println("InformationContent ArrayList: " + informationContentArrayList);
        return informationContentArrayList;
    }

    /**
     * This method is used to insert attribute values that need to be displayed in a view
     * because an InformationContent object does only contain the id of the included platform or information object.
     *
     * @param informationContents Given list contain all the necessary InformationContent objects.
     * @return List of InformationContent objects with all necessary attributes inserted.
     */
    public static List<InformationContent> fillForeignKeyObjects(List<InformationContent> informationContents) {
        for (InformationContent currentElement : informationContents) {
            currentElement.platform.setPlatformName(
                    Platform.findByPlatformId(currentElement.platform.platformId).getPlatformName());
            currentElement.information.setInformationName(
                    Information.findByInformationId(currentElement.information.informationId).getInformationName());
        }
        return informationContents;
    }

    //Get list of informationIds -> run through Id

    public static List<InformationContent> formToInformationContents(DynamicForm requestForm, Platform platform) {
        List<InformationContent> informationContents = new LinkedList<>();
        List<Information> informationList = Information.findAllInformation();
        for (Information currentElement : informationList) {
            String informationContentIdString = requestForm.get("informationContentId-" + currentElement.informationId);
            if (informationContentIdString != null && !informationContentIdString.isEmpty()) {
                Long informationContentId = Long.parseLong(informationContentIdString);
                InformationContent existingInformationContent =
                        InformationContent.findByInformationContentId(informationContentId);
                String contentString = requestForm.get("informationContent-" + currentElement.informationId);
                existingInformationContent.setInformationContent(contentString);
                informationContents.add(existingInformationContent);
            } else {
                InformationContent newInformationContent = new InformationContent();
                newInformationContent.setPlatform(platform);
                newInformationContent.setInformation(currentElement);
                String contentString = requestForm.get("informationContent-" + currentElement.informationId);
                newInformationContent.setInformationContent(contentString);
                informationContents.add(newInformationContent);
            }
                /*
                Information informationObject = Information.findByInformationId(informationId);
                List<InformationContent> informationContentsOfInformationObject = currentElement.getInformationContents();
                informationContentsOfInformationObject.add(informationContent);
                currentElement.setInformationContents(informationContentsOfInformationObject);
                */
        }
        System.out.println("INFOCONTENT: " + informationContents);
        return informationContents;
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
