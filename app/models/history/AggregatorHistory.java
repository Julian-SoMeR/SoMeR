package models.history;

import models.Aggregator;
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
@Table(name = "aggregator_history")
public class AggregatorHistory extends Model {
    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
    to generate the tables, contents and relations of the database and provide evolutions. The other annotations
    used tell the html form helper what constraints should be there when a form is submitted. */
    @Id
    public Long aggregatorHistoryId;

    @ManyToOne
    @JoinColumn(name = "aggregator_aggregator_id")
    public Aggregator aggregator;

    @NotNull
    public String aggregatorName;

    @NotNull
    @Column(columnDefinition = "TEXT default ''")
    public String aggregatorCategory;

    Timestamp historyCreationDate;

    // Same flag as in all other entities. Except in history tables it can be used to detect the
    // kind of change that occurred. E.g. 0 -> 1: Deletion, 1 -> 0: Recovery from Deletion. 0 -> 0: Update.
    @Column(columnDefinition = "TINYINT default '0'")
    @NotNull
    Boolean deleteStatus;

    // List to collect all platform objects to render them on the platforms page.
    private static List<AggregatorHistory> aggregatorHistoryList;

    /* ----- Constructors ----- */
    public AggregatorHistory() {
    }

    public AggregatorHistory(Long aggregatorHistoryId, Aggregator aggregator, String aggregatorName,
                             String aggregatorCategory, Timestamp historyCreationDate, Boolean deleteStatus) {
        this.aggregatorHistoryId = aggregatorHistoryId;
        this.aggregator = aggregator;
        this.aggregatorName = aggregatorName;
        this.aggregatorCategory = aggregatorCategory;
        this.historyCreationDate = historyCreationDate;
        this.deleteStatus = deleteStatus;
    }

    /* ---- Methods ---- */
    public static List<AggregatorHistory> findAllHistory() {
        List<AggregatorHistory> historyList =
                Ebean.find(AggregatorHistory.class).orderBy("historyCreationDate desc").findList();
        ArrayList<AggregatorHistory> historyArrayList = new ArrayList<AggregatorHistory>(historyList);
        return historyArrayList;
    }

    /* ----Getters Setters ---- */
    public Long getAggregatorHistoryId() {
        return aggregatorHistoryId;
    }

    public void setAggregatorHistoryId(Long aggregatorHistoryId) {
        this.aggregatorHistoryId = aggregatorHistoryId;
    }

    public Aggregator getAggregator() {
        return aggregator;
    }

    public void setAggregator(Aggregator aggregator) {
        this.aggregator = aggregator;
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

    public static List<AggregatorHistory> getAggregatorHistoryList() {
        return aggregatorHistoryList;
    }

    public static void setAggregatorHistoryList(List<AggregatorHistory> aggregatorHistoryList) {
        AggregatorHistory.aggregatorHistoryList = aggregatorHistoryList;
    }

    public String toString() {
        return "AggregatorHistory{" +
                "aggregatorHistoryId=" + aggregatorHistoryId +
                ", aggregator=" + aggregator +
                ", aggregatorName='" + aggregatorName + '\'' +
                ", aggregatorCategory='" + aggregatorCategory + '\'' +
                ", historyCreationDate=" + historyCreationDate +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
