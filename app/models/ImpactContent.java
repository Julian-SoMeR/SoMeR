package models;
import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;
import javax.persistence.*;
import play.mvc.*;

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
    @Column(columnDefinition = "TEXT")
    public String impactContent;
    @DocEmbedded
    @ManyToOne
    public Impact impact;
    @DocEmbedded
    @ManyToOne
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
        return Ebean.find(ImpactContent.class, impactContentId);
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
