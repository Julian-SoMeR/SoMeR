package models.history;

import models.AggregatorContent;
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
@Table(name = "aggregator_content_history")
public class AggregatorContentHistory extends Model {
    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
    to generate the tables, contents and relations of the database and provide evolutions. The other annotations
    used tell the html form helper what constraints should be there when a form is submitted. */
    @Id
    public Long aggregatorContentHistoryId;

    @NotNull
    @Column(columnDefinition = "TEXT default ''")
    public String aggregatorContent;

    @ManyToOne
    @JoinColumn(name = "aggregator_content_aggregator_content_id")
    @NotNull
    public AggregatorContent aggregatorContentId;

    Timestamp historyCreationDate;

    // Same flag as in all other entities. Except in history tables it can be used to detect the
    // kind of change that occurred. E.g. 0 -> 1: Deletion, 1 -> 0: Recovery from Deletion. 0 -> 0: Update.
    @Column(columnDefinition = "TINYINT default '0'")
    @NotNull
    Boolean deleteStatus;

    // List to collect all platform objects to render them on the platforms page.
    private static List<AggregatorContentHistory> aggregatorContentHistoryList;

    /* ----- Constructors ----- */
    public AggregatorContentHistory() {
    }

    public AggregatorContentHistory(Long aggregatorContentHistoryId, String aggregatorContent, AggregatorContent aggregatorContentId, Timestamp historyCreationDate, Boolean deleteStatus) {
        this.aggregatorContentHistoryId = aggregatorContentHistoryId;
        this.aggregatorContent = aggregatorContent;
        this.aggregatorContentId = aggregatorContentId;
        this.historyCreationDate = historyCreationDate;
        this.deleteStatus = deleteStatus;
    }

    /* ---- Methods ---- */
    public static List<AggregatorContentHistory> findAllHistory() {
        List<AggregatorContentHistory> historyList =
                Ebean.find(AggregatorContentHistory.class).orderBy("historyCreationDate desc").findList();
        ArrayList<AggregatorContentHistory> historyArrayList = new ArrayList<AggregatorContentHistory>(historyList);
        return historyArrayList;
    }

    /* ----Getters Setters ---- */
    public Long getAggregatorContentHistoryId() {
        return aggregatorContentHistoryId;
    }

    public void setAggregatorContentHistoryId(Long aggregatorContentHistoryId) {
        this.aggregatorContentHistoryId = aggregatorContentHistoryId;
    }

    public String getAggregatorContent() {
        return aggregatorContent;
    }

    public void setAggregatorContent(String aggregatorContent) {
        this.aggregatorContent = aggregatorContent;
    }

    public AggregatorContent getAggregatorContentId() {
        return aggregatorContentId;
    }

    public void setAggregatorContentId(AggregatorContent aggregatorContentId) {
        this.aggregatorContentId = aggregatorContentId;
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

    public static List<AggregatorContentHistory> getAggregatorContentHistoryList() {
        return aggregatorContentHistoryList;
    }

    public static void setAggregatorContentHistoryList(List<AggregatorContentHistory> aggregatorContentHistoryList) {
        AggregatorContentHistory.aggregatorContentHistoryList = aggregatorContentHistoryList;
    }

    public String toString() {
        return "AggregatorContentHistory{" +
                "aggregatorContentHistoryId=" + aggregatorContentHistoryId +
                ", aggregatorContent='" + aggregatorContent + '\'' +
                ", aggregatorContentId=" + aggregatorContentId +
                ", historyCreationDate=" + historyCreationDate +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
