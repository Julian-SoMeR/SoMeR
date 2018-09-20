package models.history;

import models.Impact;
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
 * This model contains all the Impact History data of the SoMeR.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
@Entity
@Table(name = "impact_history")
public class ImpactHistory extends Model {
    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
    to generate the tables, contents and relations of the database and provide evolutions. The other annotations
    used tell the html form helper what constraints should be there when a form is submitted. */
    @Id
    public Long impactHistoryId;

    @ManyToOne
    @JoinColumn(name = "impact_impact_id")
    public Impact impact;

    @NotNull
    public String impactName;

    @NotNull
    @Column(columnDefinition = "TEXT default ''")
    public String impactCategory;

    Timestamp historyCreationDate;

    // Same flag as in all other entities. Except in history tables it can be used to detect the
    // kind of change that occurred. E.g. 0 -> 1: Deletion, 1 -> 0: Recovery from Deletion. 0 -> 0: Update.
    @Column(columnDefinition = "TINYINT default '0'")
    @NotNull
    Boolean deleteStatus;

    // List to collect all impactHistory objects to render them on the history subpage.
    private static List<ImpactHistory> impactHistoryList;

    /* ----- Constructors ----- */
    public ImpactHistory() {
    }

    public ImpactHistory(Long impactHistoryId, Impact impact, String impactName, String impactCategory, Timestamp historyCreationDate, Boolean deleteStatus) {
        this.impactHistoryId = impactHistoryId;
        this.impact = impact;
        this.impactName = impactName;
        this.impactCategory = impactCategory;
        this.historyCreationDate = historyCreationDate;
        this.deleteStatus = deleteStatus;
    }

    /* ---- Methods ---- */
    public static List<ImpactHistory> findAllHistory() {
        List<ImpactHistory> historyList =
                Ebean.find(ImpactHistory.class).orderBy("historyCreationDate desc").findList();
        ArrayList<ImpactHistory> historyArrayList = new ArrayList<ImpactHistory>(historyList);
        return historyArrayList;
    }


    /* ----Getters Setters ---- */
    public Long getImpactHistoryId() {
        return impactHistoryId;
    }

    public void setImpactHistoryId(Long impactHistoryId) {
        this.impactHistoryId = impactHistoryId;
    }

    public Impact getImpact() {
        return impact;
    }

    public void setImpact(Impact impact) {
        this.impact = impact;
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

    public static List<ImpactHistory> getImpactHistoryList() {
        return impactHistoryList;
    }

    public static void setImpactHistoryList(List<ImpactHistory> impactHistoryList) {
        ImpactHistory.impactHistoryList = impactHistoryList;
    }

    public String toString() {
        return "ImpactHistory{" +
                "impactHistoryId=" + impactHistoryId +
                ", impact=" + impact +
                ", impactName='" + impactName + '\'' +
                ", impactCategory='" + impactCategory + '\'' +
                ", historyCreationDate=" + historyCreationDate +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
