package models;
import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;
import javax.persistence.*;
import play.mvc.*;
import play.data.DynamicForm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *  This model contains all the impact data contents of the SoMeR. It can only be obtained via
 *  a cross reference between its impacts and a platform.
 *  The JPA/Ebean annotations are used to tell Play how
 *  to generate the tables, contents and relations of the database and provide evolutions.
 */
@DocStore
@Entity
@Table(name = "impact_content")
public class ImpactContent extends BaseDomain implements PathBindable<ImpactContent> {
    /* These are all attributes that are mapped for the database. */
    @Id
    public Long impactContentId;
    @NotNull
    @Column(columnDefinition = "TEXT default ''")
    public String impactContent;
    @DocEmbedded
    @ManyToOne
    @JoinColumn(name = "impact_impact_id")
    @NotNull
    public Impact impact;
    @DocEmbedded
    @ManyToOne
    @JoinColumn(name = "platform_platform_id")
    @NotNull
    public Platform platform;

    /* ----- Constructors ----- */
    public ImpactContent() {}

    public ImpactContent(Long impactContentId, String impactContent, Impact impact, Platform platform) {
        this.impactContentId = impactContentId;
        this.impactContent = impactContent;
        this.impact = impact;
        this.platform = platform;
    }


    /* Methods necessary for the interface Pathbindable */
    @Override
    public ImpactContent bind(String key, String id) {
        ImpactContent impactContent = findByImpactContentId(new Long(id));
        if (impactContent == null) {
            throw new IllegalArgumentException("Impact content data with impactContentId " + id + " not found");
        }
        return impactContent;
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(impactContentId);
    }

    @Override
    public String javascriptUnbind() {
        return this.impactContent;
    }


    /* ---- Methods ---- */
    /**
     * This method uses the impactContentId of the impactContent entity to find data in the database and bind
     * it to the corresponding model object.
     * @param impactContentId Unique identifier of impact content.
     * @return Model object filled with data from the impactContent table.
     */
    public static ImpactContent findByImpactContentId(Long impactContentId) {
        return Ebean.find(ImpactContent.class).where().and(
                Expr.eq("impactContentId", impactContentId),
                Expr.eq("deleteStatus", 0)
        ).findOne();
    }

    /**
     * This method uses the platformId of the Platform entity to find data in the database and bind
     * it to the ImpactContent model object.
     */
    public static List<ImpactContent> findAllByPlatformId(Long platformId) {
        List<ImpactContent> impactContents = Ebean.find(ImpactContent.class).where().and(
                Expr.eq("platform_platform_id", platformId),
                Expr.eq("deleteStatus", 0)
        ).findList();
        ArrayList<ImpactContent> impactContentArrayList =
                new ArrayList<ImpactContent>(impactContents);
        return impactContentArrayList;
    }

    /**
     * This method is used to insert attribute values that need to be displayed in a view
     * because a ImpactContent object does only contain the id of the included platform or impact object.
     *
     * @param impactContents Given list contain all the necessary ImpactContent objects.
     * @return List of ImpactContent objects with all necessary attributes inserted.
     */
    public static List<ImpactContent> fillForeignKeyObjects(List<ImpactContent> impactContents) {
        for (ImpactContent currentElement : impactContents) {
            currentElement.platform.setPlatformName(
                    Platform.findByPlatformId(currentElement.platform.platformId).getPlatformName());
            currentElement.impact.setImpactName(
                    Impact.findByImpactId(currentElement.impact.impactId).getImpactName());
        }
        return impactContents;
    }

    /**
     * Map all the data of the request form to Platform and ImpactContent objects. If the data is to be updated,
     * load the existing data into the objects. Else create new objects to save everything.
     *
     * @param requestForm Form object containing all the data form an html form.
     * @param platform    Platform object for use on update.
     * @return List of ImpactContents containing all the necessary data to render a page or save the objects.
     */
    public static List<ImpactContent> formToImpactContents(DynamicForm requestForm, Platform platform) {
        List<ImpactContent> impactContents = new LinkedList<>();
        String categoryString = requestForm.get("impactCategory");
        List<Impact> impactList = Impact.findAllImpactsOfCategory(categoryString);

        for (Impact currentElement : impactList) {
            String impactContentIdString = requestForm.get("impactContentId-" + currentElement.impactId);

            if (impactContentIdString != null && !impactContentIdString.isEmpty()) {
                Long impactContentId = Long.parseLong(impactContentIdString);
                ImpactContent existingImpactContent =
                        ImpactContent.findByImpactContentId(impactContentId);
                String contentString = requestForm.get("impactContent-" + currentElement.impactId);
                existingImpactContent.setImpactContent(contentString);
                impactContents.add(existingImpactContent);
            } else {
                ImpactContent newImpactContent = new ImpactContent();
                newImpactContent.setPlatform(platform);
                newImpactContent.setImpact(currentElement);
                String contentString = requestForm.get("impactContent-" + currentElement.impactId);
                newImpactContent.setImpactContent(contentString);
                impactContents.add(newImpactContent);
            }
        }
        return impactContents;
    }

    /**
     * Filter the ImpactContents by the category that is currently displayed in the view. The currently
     * active category is received by the category url string.
     *
     * @param impactContents Impact Content objects to filter.
     * @param categoryUrl Category url string as filter criteria.
     * @return Filtered list of Impact Content objects for display in a view.
     */
    public static List<ImpactContent> filterCurrentCategory(
            List<ImpactContent> impactContents,String categoryUrl) {
        List<ImpactContent> filteredImpactContents = new LinkedList<>();

        for (ImpactContent currentElement: impactContents) {
            String currentCategory = currentElement.impact.getImpactCategory();
            currentCategory = currentCategory.replaceAll("[^A-Za-z0-9]", "");
            currentCategory = currentCategory.toLowerCase();
            if (currentCategory.equals(categoryUrl)) {
                filteredImpactContents.add(currentElement);
            }
        }
        return  filteredImpactContents;
    }

    /* ---- Getters, Setters, ToString Method ---- */

    public Long getImpactContentId() {
        return impactContentId;
    }

    public void setImpactContentId(Long impactContentId) {
        this.impactContentId = impactContentId;
    }

    public String getImpactContent() {
        return impactContent;
    }

    public void setImpactContent(String impactContent) {
        this.impactContent = impactContent;
    }

    public Impact getImpact() {
        return impact;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    @Override
    public String toString() {
        return "ImpactContent{" +
                "impactContentId=" + impactContentId +
                ", impact=" + impact +
                ", platform=" + platform +
                '}';
    }
}
