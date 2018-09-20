package models.history;
import models.InformationContent;
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
 * This model contains all the InformationContentHistory data of the SoMeR.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
@Entity
@Table(name = "information_content_history")
public class InformationContentHistory extends Model {
    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
    to generate the tables, contents and relations of the database and provide evolutions. The other annotations
    used tell the html form helper what constraints should be there when a form is submitted. */
    @Id
    public Long informationContentHistoryId;

    @NotNull
    @Column(columnDefinition = "TEXT default ''")
    public String informationContent;

    @ManyToOne
    @JoinColumn(name = "information_content_information_content_id")
    @NotNull
    public InformationContent informationContentId;

    Timestamp historyCreationDate;

    // Same flag as in all other entities. Except in history tables it can be used to detect the
    // kind of change that occurred. E.g. 0 -> 1: Deletion, 1 -> 0: Recovery from Deletion. 0 -> 0: Update.
    @Column(columnDefinition = "TINYINT default '0'")
    @NotNull
    Boolean deleteStatus;

    // List to collect all informationContentHistory objects to render them on the history subpage.
    private static List<InformationContentHistory> informationContentHistoryList;

    /* ----- Constructors ----- */
    public InformationContentHistory() {
    }

    public InformationContentHistory(Long informationContentHistoryId, String informationContent, InformationContent informationContentId, Timestamp historyCreationDate, Boolean deleteStatus) {
        this.informationContentHistoryId = informationContentHistoryId;
        this.informationContent = informationContent;
        this.informationContentId = informationContentId;
        this.historyCreationDate = historyCreationDate;
        this.deleteStatus = deleteStatus;
    }

    /* ---- Methods ---- */
    public static List<InformationContentHistory> findAllHistory() {
        List<InformationContentHistory> historyList =
                Ebean.find(InformationContentHistory.class).orderBy("historyCreationDate desc").findList();
        ArrayList<InformationContentHistory> historyArrayList = new ArrayList<InformationContentHistory>(historyList);
        return historyArrayList;
    }

    /* ----Getters Setters ---- */

    public Long getInformationContentHistoryId() {
        return informationContentHistoryId;
    }

    public void setInformationContentHistoryId(Long informationContentHistoryId) {
        this.informationContentHistoryId = informationContentHistoryId;
    }

    public String getInformationContent() {
        return informationContent;
    }

    public void setInformationContent(String informationContent) {
        this.informationContent = informationContent;
    }

    public InformationContent getInformationContentId() {
        return informationContentId;
    }

    public void setInformationContentId(InformationContent informationContentId) {
        this.informationContentId = informationContentId;
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

    public static List<InformationContentHistory> getPlatformHistoryList() {
        return informationContentHistoryList;
    }

    public static void setPlatformHistoryList(List<InformationContentHistory> platformHistoryList) {
        InformationContentHistory.informationContentHistoryList = platformHistoryList;
    }

    public String toString() {
        return "InformationContentHistory{" +
                "informationContentHistoryId=" + informationContentHistoryId +
                ", informationContent='" + informationContent + '\'' +
                ", informationContentId=" + informationContentId +
                ", historyCreationDate=" + historyCreationDate +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
