package models.history;

import models.Function;
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
 * This model contains all the function history data of the SoMeR.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
@Entity
@Table(name = "function_history")
public class FunctionHistory extends Model {
    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
    to generate the tables, contents and relations of the database and provide evolutions. The other annotations
    used tell the html form helper what constraints should be there when a form is submitted. */
    @Id
    public Long functionHistoryId;

    @ManyToOne
    @JoinColumn(name = "function_function_id")
    public Function function;

    @NotNull
    public String functionName;

    @NotNull
    @Column(columnDefinition = "TEXT default ''")
    public String functionCategory;

    Timestamp historyCreationDate;

    // Same flag as in all other entities. Except in history tables it can be used to detect the
    // kind of change that occurred. E.g. 0 -> 1: Deletion, 1 -> 0: Recovery from Deletion. 0 -> 0: Update.
    @Column(columnDefinition = "TINYINT default '0'")
    @NotNull
    Boolean deleteStatus;

    // List to collect all functionHistory objects to render them on the history subpage.
    private static List<FunctionHistory> functionHistoryList;

    /* ----- Constructors ----- */
    public FunctionHistory() {
    }

    public FunctionHistory(Long functionHistoryId, Function function, String functionName, String functionCategory, Timestamp historyCreationDate, Boolean deleteStatus) {
        this.functionHistoryId = functionHistoryId;
        this.function = function;
        this.functionName = functionName;
        this.functionCategory = functionCategory;
        this.historyCreationDate = historyCreationDate;
        this.deleteStatus = deleteStatus;
    }

    /* ---- Methods ---- */
    public static List<FunctionHistory> findAllHistory() {
        List<FunctionHistory> historyList =
                Ebean.find(FunctionHistory.class).orderBy("historyCreationDate desc").findList();
        ArrayList<FunctionHistory> historyArrayList = new ArrayList<FunctionHistory>(historyList);
        return historyArrayList;
    }

    /* ----Getters Setters ---- */
    public Long getFunctionHistoryId() {
        return functionHistoryId;
    }

    public void setFunctionHistoryId(Long functionHistoryId) {
        this.functionHistoryId = functionHistoryId;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionCategory() {
        return functionCategory;
    }

    public void setFunctionCategory(String functionCategory) {
        this.functionCategory = functionCategory;
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

    public static List<FunctionHistory> getFunctionHistoryList() {
        return functionHistoryList;
    }

    public static void setFunctionHistoryList(List<FunctionHistory> functionHistoryList) {
        FunctionHistory.functionHistoryList = functionHistoryList;
    }

    public String toString() {
        return "FunctionHistory{" +
                "functionHistoryId=" + functionHistoryId +
                ", function=" + function +
                ", functionName='" + functionName + '\'' +
                ", functionCategory='" + functionCategory + '\'' +
                ", historyCreationDate=" + historyCreationDate +
                ", deleteStatus=" + deleteStatus +
                '}';
    }
}
