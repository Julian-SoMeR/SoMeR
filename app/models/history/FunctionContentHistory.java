package models.history;

import models.FunctionContent;
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
 * This model contains all the function content history data of the SoMeR.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
@Entity
@Table(name = "function_content_history")
public class FunctionContentHistory extends Model {
    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
    to generate the tables, contents and relations of the database and provide evolutions. The other annotations
    used tell the html form helper what constraints should be there when a form is submitted. */
    @Id
    public Long functionContentHistoryId;

    @NotNull
    @Column(columnDefinition = "TEXT default ''")
    public String functionContent;

    @ManyToOne
    @JoinColumn(name = "function_content_function_content_id")
    @NotNull
    public FunctionContent functionContentId;

    Timestamp historyCreationDate;

    // Same flag as in all other entities. Except in history tables it can be used to detect the
    // kind of change that occurred. E.g. 0 -> 1: Deletion, 1 -> 0: Recovery from Deletion. 0 -> 0: Update.
    @Column(columnDefinition = "TINYINT default '0'")
    @NotNull
    Boolean deleteStatus;

    // List to collect all functionContentHistory objects to render them on the history subpage.
    private static List<FunctionContentHistory> functionContentHistoryList;

    /* ----- Constructors ----- */
    public FunctionContentHistory() {
    }

    public FunctionContentHistory(Long functionContentHistoryId, String functionContent, FunctionContent functionContentId, Timestamp historyCreationDate, Boolean deleteStatus) {
        this.functionContentHistoryId = functionContentHistoryId;
        this.functionContent = functionContent;
        this.functionContentId = functionContentId;
        this.historyCreationDate = historyCreationDate;
        this.deleteStatus = deleteStatus;
    }

    /* ---- Methods ---- */
    public static List<FunctionContentHistory> findAllHistory() {
        List<FunctionContentHistory> historyList =
                Ebean.find(FunctionContentHistory.class).orderBy("historyCreationDate desc").findList();
        ArrayList<FunctionContentHistory> historyArrayList = new ArrayList<FunctionContentHistory>(historyList);
        return historyArrayList;
    }

    /* ----Getters Setters ---- */
    public Long getFunctionContentHistoryId() {
        return functionContentHistoryId;
    }

    public void setFunctionContentHistoryId(Long functionContentHistoryId) {
        this.functionContentHistoryId = functionContentHistoryId;
    }

    public String getFunctionContent() {
        return functionContent;
    }

    public void setFunctionContent(String functionContent) {
        this.functionContent = functionContent;
    }

    public FunctionContent getFunctionContentId() {
        return functionContentId;
    }

    public void setFunctionContentId(FunctionContent functionContentId) {
        this.functionContentId = functionContentId;
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

    public static List<FunctionContentHistory> getFunctionContentHistoryList() {
        return functionContentHistoryList;
    }

    public static void setFunctionContentHistoryList(List<FunctionContentHistory> functionContentHistoryList) {
        FunctionContentHistory.functionContentHistoryList = functionContentHistoryList;
    }

    public String toString() {
        return "FunctionContentHistory{" +
                "functionContentHistoryId=" + functionContentHistoryId +
                ", functionContent='" + functionContent + '\'' +
                ", functionContentId=" + functionContentId +
                ", historyCreationDate=" + historyCreationDate +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
