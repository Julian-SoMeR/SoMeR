package models;
import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;
import javax.persistence.*;
import play.mvc.*;

import java.util.ArrayList;
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
    public Impact impact;
    @DocEmbedded
    @ManyToOne
    @JoinColumn(name = "platform_platform_id")
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
                Expr.eq("impact_impact_id", platformId),
                Expr.eq("deleteStatus", 0)
        ).findList();
        ArrayList<ImpactContent> impactContentArrayList =
                new ArrayList<ImpactContent>(impactContents);
        return impactContentArrayList;
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
