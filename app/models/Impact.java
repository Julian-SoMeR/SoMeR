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
@DocStore
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
     * @param impactId Unique identifier of impacts.
     * @return Model object filled with data from the impact table.
     */
    public static Impact findByImpactId(Long impactId) {
        return Ebean.find(Impact.class).where().and(
                Expr.eq("impactId", impactId),
                Expr.eq("deleteStatus", 0)
        ).findOne();
    }

    /**
     * Uses the name of the impact to look up data in the database.
     * @param impactName Name of the corresponding impact.
     * @return The impact object filled with the data found in the database.
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

    /**
     * Get all distinct categories from the Impact entity and generate a usable url string from them.
     * The url string is saved in the description field since the entity itself will never be saved and
     * the unchanged category names are needed to display in a view.
     *
     * @return List of Impacts with all distinct categories and their url strings.
     */
    public static List<Impact> findDistinctCategories() {
        impactList = Ebean.find(Impact.class).select("impactCategory").where()
                .eq("deleteStatus", 0).setDistinct(true).findList();
        for (Impact currentElement : impactList) {
            String currentImpactCategory = currentElement.impactCategory;
            currentImpactCategory = currentImpactCategory.replaceAll("[^A-Za-z0-9]", "");
            currentImpactCategory = currentImpactCategory.toLowerCase();
            // As long as this is never saved, this is the easiest way to have the pathname and
            // the category in the same object
            currentElement.setImpactDescription(currentImpactCategory);
        }
        return impactList;
    }

    /**
     * Get one category from the Impact entity and generate a usable url string. This is necessary when
     * routing to the view where the categories are displayed for the first time.
     *
     * @return Generated category url string.
     */
    public static String generateFirstCategoryUrlString() {
        impactList = Ebean.find(Impact.class).select("impactCategory").setDistinct(true).findList();
        String categoryUrlString = impactList.get(1).impactCategory;
        categoryUrlString = categoryUrlString.replaceAll("[^A-Za-z0-9]", "");
        categoryUrlString = categoryUrlString.toLowerCase();
        return categoryUrlString;
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
