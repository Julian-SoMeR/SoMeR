package models;

import java.util.ArrayList;
import java.util.List;
import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;
import javax.persistence.*;
import play.mvc.*;

/**
 * This model contains all the designation data for the aggregator data of the SoMeR.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
@DocStore
@Entity
@Table(name = "aggregator")
public class Aggregator extends BaseDomain implements PathBindable<Aggregator> {
    /* These are all attributes that are mapped for the database. */
    @Id
    public Long aggregatorId;
    @DocSortable
    @NotNull
    public String aggregatorName;
    @NotNull
    @Column(columnDefinition = "TEXT default ''")
    public String aggregatorCategory;
    @Column(columnDefinition = "TEXT default ''")
    public String aggregatorDescription;

    //@DocEmbedded
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "aggregator")
    public List<AggregatorContent> aggregatorContents = new ArrayList<>();

    // List to collect all aggregator objects to render them on the aggregators page.
    private static List<Aggregator> aggregatorList;

    /* ----- Constructors ----- */
    public Aggregator() {}

    public Aggregator(Long aggregatorId, String aggregatorName, String aggregatorCategory, String aggregatorDescription, List<AggregatorContent> aggregatorContents) {
        this.aggregatorId = aggregatorId;
        this.aggregatorName = aggregatorName;
        this.aggregatorCategory = aggregatorCategory;
        this.aggregatorDescription = aggregatorDescription;
        this.aggregatorContents = aggregatorContents;
    }

    /* Methods necessary for the interface Pathbindable */
    @Override
    public Aggregator bind(String key, String id) {
        Aggregator aggregator = findByAggregatorId(new Long(id));
        if (aggregator == null) {
            throw new IllegalArgumentException("Aggregator data with aggregatorId " + id + " not found");
        }
        return aggregator;
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(aggregatorId);
    }

    @Override
    public String javascriptUnbind() {
        return this.aggregatorName;
    }

    /* ---- Methods ---- */
    /**
     * This method uses the aggregatorId of the aggregator entity to find data in the database and bind in to the
     * corresponding model object.
     * @param aggregatorId Unique identifier of aggregators.
     * @return Model object filled with data from the aggregator table.
     */
    public static Aggregator findByAggregatorId(Long aggregatorId) {
        return Ebean.find(Aggregator.class).where().and(
                Expr.eq("aggregatorId", aggregatorId),
                Expr.eq("deleteStatus", 0)
        ).findOne();
    }

    /**
     * Uses the name of the aggregator to look up data in the database.
     * @param aggregatorName Name of the corresponding aggregator.
     * @return The aggregator object filled with the data found in the database.
     */
    public static Aggregator findByAggregatorName(String aggregatorName) {
        return Ebean.find(Aggregator.class).where().and(
                Expr.eq("aggregatorName", aggregatorName),
                Expr.eq("deleteStatus", 0)
        ).findOne();
    }

    /**
     * Looks up all aggregator data in the database, sorts this data and saves it in a list.
     * @return List of all aggregator objects filled by the data from the database.
     */
    public static List<Aggregator> findAllAggregators() {
        aggregatorList =
                Ebean.find(Aggregator.class).where().eq("deleteStatus", 0).orderBy("aggregatorName asc").findList();
        ArrayList <Aggregator> aggregatorArrayList = new ArrayList<Aggregator>(aggregatorList);
        return aggregatorArrayList;
    }

    /* ---- Getters, Setters, ToString Method ---- */

    public Long getAggregatorId() {
        return aggregatorId;
    }

    public void setAggregatorId(Long aggregatorId) {
        this.aggregatorId = aggregatorId;
    }

    public String getAggregatorName() {
        return aggregatorName;
    }

    public void setAggregatorName(String aggregatorName) {
        this.aggregatorName = aggregatorName;
    }

    public String getAggregatorCategory() {
        return aggregatorCategory;
    }

    public void setAggregatorCategory(String aggregatorCategory) {
        this.aggregatorCategory = aggregatorCategory;
    }

    public String getAggregatorDescription() {
        return aggregatorDescription;
    }

    public void setAggregatorDescription(String aggregatorDescription) {
        this.aggregatorDescription = aggregatorDescription;
    }

    public List<AggregatorContent> getAggregatorContents() {
        return aggregatorContents;
    }

    public void setAggregatorContents(List<AggregatorContent> aggregatorContents) {
        this.aggregatorContents = aggregatorContents;
    }

    public String toString() {
        return "Aggregator{" +
                "aggregatorId=" + aggregatorId +
                ", aggregatorName='" + aggregatorName + '\'' +
                '}';
    }
}
