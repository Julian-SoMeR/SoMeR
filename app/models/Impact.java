package models;

import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;
import javax.persistence.*;
import play.mvc.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This model contains all the designation data for the impact data of the SoMeR.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
//@DocStore
@Entity
@Table(name = "impact")
public class Impact extends BaseDomain implements PathBindable<Impact> {
    /* These are all attributes that are mapped for the database. */
    @Id
    public Long impactId;
    //@DocSortable
    public String impactName;
    public String impactCategory;
    @Column(columnDefinition = "TEXT")
    public String impactDescription;

    //@DocEmbedded
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "impact")
    public List<ImpactContent> impactContents = new ArrayList<>();

    // List to collect all impact objects to render them on the platforms -> impacts page.
    private static List<Impact> impactList;

    /* ----- Constructors ----- */
    public Impact() {}

    public Impact(Long impactId, String impactName, String impactCategory, String impactDescription, List<ImpactContent> impactContents) {
        this.impactId = impactId;
        this.impactName = impactName;
        this.impactCategory = impactCategory;
        this.impactDescription = impactDescription;
        this.impactContents = impactContents;
    }

    /* Methods necessary for the interface Pathbindable */
    @Override
    public Impact bind(String key, String id) {
        Impact impact = findByImpactId(new Long(id));
        if (impact == null) {
            throw new IllegalArgumentException("Impact data with impactId " + id + " not found");
        }
        return impact;
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(impactId);
    }

    @Override
    public String javascriptUnbind() {
        return this.impactName;
    }


    /* ---- Methods ---- */
    /**
     * This method uses the impactId of the impact entity to find data in the database and bind in to the
     * corresponding model object.
     * @param impactId Unique identifier of functions.
     * @return Model object filled with data from the impact table.
     */
    public static Impact findByImpactId(Long impactId) {
        return Ebean.find(Impact.class, impactId);
    }

    /**
     * Uses the name of the function to look up data in the database.
     * @param impactName Name of the corresponding impact.
     * @return The function object filled with the data found in the database.
     */
    public static Impact findByImpactName(String impactName) {
        return Ebean.find(Impact.class).where().eq("impactName", impactName).findOne();
    }

    /**
     * Looks up all impact data in the database, sorts this data and saves it in a list.
     * @return List of all impact objects filled by the data from the database.
     */
    public static List<Impact> findAllImpacts() {
        impactList = Ebean.find(Impact.class).orderBy("impactName asc").findList();
        ArrayList <Impact> impactArrayList = new ArrayList<Impact>(impactList);
        return impactArrayList;
    }

    /* ---- Getters, Setters, ToString Method ---- */
    public Long getImpactId() {
        return impactId;
    }

    public void setImpactId(Long impactId) {
        this.impactId = impactId;
    }

    public String getImpactName() {
        return impactName;
    }

    public void setImpactName(String impactName) {
        this.impactName = impactName;
    }

    public String getImpactCategory() {
        return impactCategory;
    }

    public void setImpactCategory(String impactCategory) {
        this.impactCategory = impactCategory;
    }

    public String getImpactDescription() {
        return impactDescription;
    }

    public void setImpactDescription(String impactDescription) {
        this.impactDescription = impactDescription;
    }

    public List<ImpactContent> getImpactContents() {
        return impactContents;
    }

    public void setImpactContents(List<ImpactContent> impactContents) {
        this.impactContents = impactContents;
    }

    public String toString() {
        return "Impact{" +
                "impactId=" + impactId +
                ", impactName='" + impactName + '\'' +
                '}';
    }
}
