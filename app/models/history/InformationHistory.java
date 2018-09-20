package models.history;

import models.Information;
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
 * This model contains all the InformationHistory data of the SoMeR.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
@Entity
@Table(name = "information_history")
public class InformationHistory extends Model {
    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
    to generate the tables, contents and relations of the database and provide evolutions. The other annotations
    used tell the html form helper what constraints should be there when a form is submitted. */
    @Id
    public Long informationHistoryId;

    @ManyToOne
    @JoinColumn(name = "information_information_id")
    public Information information;

    @NotNull
    public String informationName;

    Timestamp historyCreationDate;

    // Same flag as in all other entities. Except in history tables it can be used to detect the
    // kind of change that occurred. E.g. 0 -> 1: Deletion, 1 -> 0: Recovery from Deletion. 0 -> 0: Update.
    @Column(columnDefinition = "TINYINT default '0'")
    @NotNull
    Boolean deleteStatus;

    // List to collect all InformationHistory objects to render them on the history subpage.
    private static List<InformationHistory> informationHistoryList;

    /* ----- Constructors ----- */
    public InformationHistory() {
    }

    public InformationHistory(Long informationHistoryId, Information information, String informationName, Timestamp historyCreationDate, Boolean deleteStatus) {
        this.informationHistoryId = informationHistoryId;
        this.information = information;
        this.informationName = informationName;
        this.historyCreationDate = historyCreationDate;
        this.deleteStatus = deleteStatus;
    }

    /* ---- Methods ---- */
    public static List<InformationHistory> findAllHistory() {
        List<InformationHistory> historyList =
                Ebean.find(InformationHistory.class).orderBy("historyCreationDate desc").findList();
        ArrayList<InformationHistory> historyArrayList = new ArrayList<InformationHistory>(historyList);
        return historyArrayList;
    }

    /* ----Getters Setters ---- */

    public Long getInformationHistoryId() {
        return informationHistoryId;
    }

    public void setInformationHistoryId(Long informationHistoryId) {
        this.informationHistoryId = informationHistoryId;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public String getInformationName() {
        return informationName;
    }

    public void setInformationName(String informationName) {
        this.informationName = informationName;
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

    public static List<InformationHistory> getInformationHistoryList() {
        return informationHistoryList;
    }

    public static void setInformationHistoryList(List<InformationHistory> informationHistoryList) {
        InformationHistory.informationHistoryList = informationHistoryList;
    }

    public String toString() {
        return "InformationHistory{" +
                "informationHistoryId=" + informationHistoryId +
                ", information=" + information +
                ", informationName='" + informationName + '\'' +
                ", historyCreationDate=" + historyCreationDate +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
