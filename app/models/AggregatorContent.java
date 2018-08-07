package models;

import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;
import javax.persistence.*;
import play.mvc.*;

/**
 *  This model contains all the aggregator data contents of the SoMeR. It can only be obtained via
 *  a cross reference between its aggregators and a platform.
 *  The JPA/Ebean annotations are used to tell Play how
 *  to generate the tables, contents and relations of the database and provide evolutions.
 */
@DocStore
@Entity
@Table(name = "aggregator_content")
public class AggregatorContent extends BaseDomain implements PathBindable<AggregatorContent> {
    /* These are all attributes that are mapped for the database. */
    @Id
    public Long aggregatorContentId;
    @Column(columnDefinition = "TEXT")
    public String aggregatorContent;
    @DocEmbedded
    @ManyToOne
    public Aggregator aggregator;
    @DocEmbedded
    @ManyToOne
    public Platform platform;

    /* ----- Constructors ----- */
    public AggregatorContent() {}

    public AggregatorContent(Long aggregatorContentId, String aggregatorContent, Aggregator aggregator, Platform platform) {
        this.aggregatorContentId = aggregatorContentId;
        this.aggregatorContent = aggregatorContent;
        this.aggregator = aggregator;
        this.platform = platform;
    }

    /* Methods necessary for the interface Pathbindable */
    @Override
    public AggregatorContent bind(String key, String id) {
        AggregatorContent aggregatorContent = findByAggregatorContentId(new Long(id));
        if (aggregatorContent == null) {
            throw new IllegalArgumentException("Aggregator content data with aggregatorContentID " + id + " not found");
        }
        return aggregatorContent;
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(aggregatorContentId);
    }

    @Override
    public String javascriptUnbind() {
        return this.aggregatorContent;
    }


    /* ---- Methods ---- */
    /**
     * This method uses the aggregatorContentId of the aggregatorContent entity to find data in the database and bind
     * it to the corresponding model object.
     * @param aggregatorContentId Unique identifier of aggregator content.
     * @return Model object filled with data from the aggregatorContent table.
     */
    public static AggregatorContent findByAggregatorContentId(Long aggregatorContentId) {
        return Ebean.find(AggregatorContent.class, aggregatorContentId);
    }

    /* ---- Getters, Setters, ToString Method ---- */

    public Long getAggregatorContentId() {
        return aggregatorContentId;
    }

    public void setAggregatorContentId(Long aggregatorContentId) {
        this.aggregatorContentId = aggregatorContentId;
    }

    public String getAggregatorContent() {
        return aggregatorContent;
    }

    public void setAggregatorContent(String aggregatorContent) {
        this.aggregatorContent = aggregatorContent;
    }

    public Aggregator getAggregator() {
        return aggregator;
    }

    public void setAggregator(Aggregator aggregator) {
        this.aggregator = aggregator;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String toString() {
        return "AggregatorContent{" +
                "aggregatorContentId=" + aggregatorContentId +
                ", aggregator=" + aggregator +
                ", platform=" + platform +
                '}';
    }
}
