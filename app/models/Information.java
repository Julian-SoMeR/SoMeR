package models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;

import javax.persistence.*;

import play.mvc.*;
import play.data.DynamicForm;

/**
 * This model contains all the general platform information data for the platforms of the SoMeR.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
@DocStore
@Entity
@Table(name = "information")
public class Information extends BaseDomain implements PathBindable<Information> {
    /* These are all attributes that are mapped for the database. */
    @Id
    public Long informationId;
    @DocSortable
    public String informationName;
    //@DocEmbedded
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "information")
    public List<InformationContent> informationContents = new ArrayList<>();

    // List to collect all information objects to render them on the platforms -> general information page.
    private static List<Information> informationList;
    // List to collect all information ids.
    private static List<Information> informationIds;


    /* ----- Constructors ----- */
    public Information() {
    }

    public Information(Long informationId, String informationName, List<InformationContent> informationContents) {
        this.informationId = informationId;
        this.informationName = informationName;
        this.informationContents = informationContents;
    }

    /* Methods necessary for the interface Pathbindable */
    @Override
    public Information bind(String key, String id) {
        Information information = findByInformationId(new Long(id));
        if (information == null) {
            throw new IllegalArgumentException("Information data with informationId " + id + " not found");
        }
        return information;
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(informationId);
    }

    @Override
    public String javascriptUnbind() {
        return this.informationName;
    }


    /* ---- Methods ---- */

    /**
     * This method uses the informationId of the information entity to find data in the database and bind in to the
     * corresponding model object.
     *
     * @param informationId Unique identifier of information.
     * @return Model object filled with data from the information table.
     */
    public static Information findByInformationId(Long informationId) {
        return Ebean.find(Information.class).where().and(
                Expr.eq("informationId", informationId),
                Expr.eq("deleteStatus", 0)
        ).findOne();
    }

    /**
     * Uses the name of the function to look up data in the database.
     *
     * @param informationName Name of the corresponding function.
     * @return The information object filled with the data found in the database.
     */
    public static Information findByInformationName(String informationName) {
        return Ebean.find(Information.class).where().and(
                Expr.eq("informationName", informationName),
                Expr.eq("deleteStatus", 0)
        ).findOne();
    }

    /**
     * Looks up all information data in the database, sorts this data and saves it in a list.
     *
     * @return List of all information objects filled by the data from the database.
     */
    public static List<Information> findAllInformation() {
        informationList = Ebean.find(Information.class).where().eq("deleteStatus", 0).findList();
        ArrayList<Information> informationArrayList = new ArrayList<Information>(informationList);
        return informationArrayList;
    }

    /**
     * Map all the data of the request form to Platform and InformationContent objects. If the data is to be updated,
     * load the existing data into the objects. Else create new objects to save everything.
     *
     * @param requestForm Form object containing all the data form an html form.
     * @param platform    Platform object for use on update.
     * @return List of InformationContents containing all the necessary data to render a page or save the objects.
     */
    public static List<Information> formToInformation(DynamicForm requestForm) {
        List<Information> informationList = Information.findAllInformation();
        List<Information> resultInformationList = new LinkedList<Information>();
        System.out.println("INFO LIST:" + informationList);
        for (Information currentElement : informationList) {
            String informationIdString = requestForm.get("informationId-" + currentElement.informationId);
            System.out.println("INFO LIST:" + informationList);
            if (informationIdString != null && !informationIdString.isEmpty()) {
                Long informationId = Long.parseLong(informationIdString);
                Information existingInformation =
                        Information.findByInformationId(informationId);
                String informationNameString = requestForm.get("informationName-" + currentElement.informationId);
                existingInformation.setInformationName(informationNameString);
                resultInformationList.add(existingInformation);
            } else {
                Information newInformation = new Information();

                String informationNameString = requestForm.get("informationName-" + currentElement.informationId);
                newInformation.setInformationName(informationNameString);
                resultInformationList.add(newInformation);
            }
        }
        String createNewInformationString = requestForm.get("createNewInformation");
        if (createNewInformationString != null || !(createNewInformationString.isEmpty())) {
            Information newInformation = new Information();
            newInformation.setInformationName(createNewInformationString);
            resultInformationList.add(newInformation);
        }
        return resultInformationList;
    }

    /* ---- Getters, Setters, ToString Method ---- */

    public Long getInformationId() {
        return informationId;
    }

    public void setInformationId(Long informationId) {
        this.informationId = informationId;
    }

    public String getInformationName() {
        return informationName;
    }

    public void setInformationName(String informationName) {
        this.informationName = informationName;
    }

    public List<InformationContent> getInformationContents() {
        return informationContents;
    }

    public void setInformationContents(List<InformationContent> informationContents) {
        this.informationContents = informationContents;
    }

    public static List<Information> getInformationList() {
        return informationList;
    }

    public static void setInformationList(List<Information> informationList) {
        Information.informationList = informationList;
    }

    public String toString() {
        return "Information{" +
                "informationId=" + informationId +
                ", informationName='" + informationName + '\'' +
                '}';
    }
}
