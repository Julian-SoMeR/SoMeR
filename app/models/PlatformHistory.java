package models;

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
@Table(name = "platform_history")
public class PlatformHistory extends Model {
    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
    to generate the tables, contents and relations of the database and provide evolutions. The other annotations
    used tell the html form helper what constraints should be there when a form is submitted. */
    @Id
    public Long platformHistoryId;

    @ManyToOne
    @JoinColumn(name = "platform_platform_id")
    public Platform platform;

    public String platformName;

    Timestamp historyCreationDate;

    // Same flag as in all other entities. Except in history tables it can be used to detect the
    // kind of change that occurred. E.g. 0 -> 1: Deletion, 1 -> 0: Recovery from Deletion. 0 -> 0: Update.
    Boolean deleteStatus;

    // List to collect all platform objects to render them on the platforms page.
    private static List<PlatformHistory> platformHistoryList;

    /* ----- Constructors ----- */
    public PlatformHistory() {
    }

    public PlatformHistory(Long platformHistoryId, Platform platform, String platformName) {
        this.platformHistoryId = platformHistoryId;
        this.platform = platform;
        this.platformName = platformName;
    }

    public Long getPlatformHistoryId() {
        return platformHistoryId;
    }

    public void setPlatformHistoryId(Long platformHistoryId) {
        this.platformHistoryId = platformHistoryId;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public static List<PlatformHistory> getPlatformHistoryList() {
        return platformHistoryList;
    }

    public static void setPlatformHistoryList(List<PlatformHistory> platformHistoryList) {
        PlatformHistory.platformHistoryList = platformHistoryList;
    }
}