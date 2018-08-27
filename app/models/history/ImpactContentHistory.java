package models.history;

import models.ImpactContent;
import play.data.validation.Constraints;

import java.util.*;

import io.ebean.*;
import io.ebean.annotation.*;
import io.ebean.Finder;
import io.ebean.Query;
import io.ebean.DocumentStore;
import io.ebean.EbeanServer;
import io.ebean.Ebean;
import play.data.DynamicForm;

import javax.persistence.*;

import play.mvc.*;

import java.sql.Timestamp;

/**
 * This model contains all the platform data of the SoMeR.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
@Entity
@Table(name = "impact_content_history")
public class ImpactContentHistory extends Model {
    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
    to generate the tables, contents and relations of the database and provide evolutions. The other annotations
    used tell the html form helper what constraints should be there when a form is submitted. */
    @Id
    public Long impactContentHistoryId;

    @NotNull
    @Column(columnDefinition = "TEXT default ''")
    public String impactContent;

    @ManyToOne
    @JoinColumn(name = "impact_content_impact_content_id")
    @NotNull
    public ImpactContent impactContentId;

    Timestamp historyCreationDate;

    // Same flag as in all other entities. Except in history tables it can be used to detect the
    // kind of change that occurred. E.g. 0 -> 1: Deletion, 1 -> 0: Recovery from Deletion. 0 -> 0: Update.
    @Column(columnDefinition = "TINYINT default '0'")
    @NotNull
    Boolean deleteStatus;

    // List to collect all platform objects to render them on the platforms page.
    private static List<ImpactContentHistory> impactContentHistoryList;

    /* ----- Constructors ----- */
    public ImpactContentHistory() {
    }

    public ImpactContentHistory(Long impactContentHistoryId, String impactContent, ImpactContent impactContentId, Timestamp historyCreationDate, Boolean deleteStatus) {
        this.impactContentHistoryId = impactContentHistoryId;
        this.impactContent = impactContent;
        this.impactContentId = impactContentId;
        this.historyCreationDate = historyCreationDate;
        this.deleteStatus = deleteStatus;
    }

    /* ---- Methods ---- */
    public static List<ImpactContentHistory> findAllHistory() {
        List<ImpactContentHistory> historyList =
                Ebean.find(ImpactContentHistory.class).orderBy("historyCreationDate desc").findList();
        ArrayList<ImpactContentHistory> historyArrayList = new ArrayList<ImpactContentHistory>(historyList);
        return historyArrayList;
    }

    /* ----Getters Setters ---- */
    public Long getImpactContentHistoryId() {
        return impactContentHistoryId;
    }

    public void setImpactContentHistoryId(Long impactContentHistoryId) {
        this.impactContentHistoryId = impactContentHistoryId;
    }

    public String getImpactContent() {
        return impactContent;
    }

    public void setImpactContent(String impactContent) {
        this.impactContent = impactContent;
    }

    public ImpactContent getImpactContentId() {
        return impactContentId;
    }

    public void setImpactContentId(ImpactContent impactContentId) {
        this.impactContentId = impactContentId;
    }

    public Timestamp getHistoryCreationDate() {
        return historyCreationDate;
    }

    public void setHistoryCreationDate(Timestamp historyCreationDate) {
        this.historyCreationDate = historyCreationDate;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public static List<ImpactContentHistory> getImpactContentHistoryList() {
        return impactContentHistoryList;
    }

    public static void setImpactContentHistoryList(List<ImpactContentHistory> impactContentHistoryList) {
        ImpactContentHistory.impactContentHistoryList = impactContentHistoryList;
    }

    public String toString() {
        return "ImpactContentHistory{" +
                "impactContentHistoryId=" + impactContentHistoryId +
                ", impactContent='" + impactContent + '\'' +
                ", impactContentId=" + impactContentId +
                ", historyCreationDate=" + historyCreationDate +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
