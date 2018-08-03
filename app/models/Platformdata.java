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



import javax.persistence.*;
import play.mvc.*;

/**
 *  This model contains all the platform data of the SoMeR.
 *  The JPA/Ebean annotations are used to tell Play how
 *  to generate the tables, contents and relations of the database and provide evolutions.
 */
@DocStore
@Entity
@Table(name = "platformdata")
public class Platformdata extends BaseDomain implements PathBindable<Platformdata> {

    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
        to generate the tables, contents and relations of the database and provide evolutions. The other annotations
        used tell the html form helper what constraints should be there when a form is submitted. */
    @Id
    public Long platformdataId;

    @Constraints.Required
    @DocSortable
    public String platformName;

    /* The odd names result from keeping the original column names in the database. They are auto-generated in the db.*/
    public String platformOrServiceType;
    public String targetGroup;
    public String commercialOrNoncommercialService;
    @Column(columnDefinition = "TEXT")
    public String freeOrSubscriptionSource;
    @Column(columnDefinition = "TEXT")
    public String primaryPurpose;
    @Column(columnDefinition = "TEXT")
    public String geographicalReach;
    @Column(columnDefinition = "TEXT")
    public String numberActiveOrRegisteredUsers;
    @Column(columnDefinition = "TEXT")
    public String contentCoverage;
    public String websiteURL;
    @Column(columnDefinition = "TEXT")
    public String usageRanking;
    @Column(columnDefinition = "TEXT")
    public String description;

    @DocEmbedded
    @OneToMany(cascade=CascadeType.ALL, mappedBy = "platformdata")
    public List<Valuedata> valuedata = new ArrayList<>();

    // List to collect all platform objects in to render them on the platforms page.
    private static List<Platformdata> platformdataList;

    /* ----- Constructors ----- */
    public Platformdata() {}

    public Platformdata(Long platformdataId, String platformName) {
        this.platformdataId = platformdataId;
        this.platformName = platformName;
    }

    public Platformdata(Long platformdataId, String platformName, String platformOrServiceType, String targetGroup,
                        String commercialOrNoncommercialService, String freeOrSubscriptionSource, String primaryPurpose,
                        String geographicalReach, String numberActiveOrRegisteredUsers, String contentCoverage,
                        String websiteURL, String usageRanking, String description) {
        this.platformdataId = platformdataId;
        this.platformName = platformName;
        this.platformOrServiceType = platformOrServiceType;
        this.targetGroup = targetGroup;
        this.commercialOrNoncommercialService = commercialOrNoncommercialService;
        this.freeOrSubscriptionSource = freeOrSubscriptionSource;
        this.primaryPurpose = primaryPurpose;
        this.geographicalReach = geographicalReach;
        this.numberActiveOrRegisteredUsers = numberActiveOrRegisteredUsers;
        this.contentCoverage = contentCoverage;
        this.websiteURL = websiteURL;
        this.usageRanking = usageRanking;
        this.description = description;
    }


    /* Methods necessary for the interface Pathbindable */
    @Override
    public Platformdata bind(String key, String id) {
        Platformdata platformdata = findByPlatformdataId(new Long(id));
        if (platformdata == null) {
            throw new IllegalArgumentException("Platform with platformdataId " + id + " not found");
        }
        return platformdata;
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(platformdataId);
    }

    @Override
    public String javascriptUnbind() {
        return this.platformName;
    }


    /* ---- Methods ---- */
    /**
     * Uses the platformdataId of the platform to look up data in the database.
     * @param platformdataId Id of the corresponding platform.
     * @return The platform object filled with the data found in the database.
     */
    public static Platformdata findByPlatformdataId(Long platformdataId) {
        return Ebean.find(Platformdata.class, platformdataId);
    }

    /**
     * Uses the name of the platform to look up data in the database.
     * @param platformName Name of the corresponding platform.
     * @return The platform object filled with the data found in the database.
     */
    public static Platformdata findByPlatformName(String platformName) {
        return Ebean.find(Platformdata.class).where().eq("platformName", platformName).findOne();
    }

    /**
     * Looks up all platform data in the database, sorts this data and saves it in a list.
     * @return List of all platform objects filled by the data from the database.
     */
    //public static List<Platformdata> findAllPlatforms() {
    public static List<Platformdata> findAllPlatforms() {
        platformdataList = Ebean.find(Platformdata.class).orderBy("platformName asc").findList();
        //platformdataList = Ebean.find(Platformdata.class).text().match("platformName", "a").order().asc("platformName").findList();
        //platformdataList = Ebean.find(Platformdata.class).setUseDocStore(true).where().ilike("platformName", "%a%").order().asc("platformName").findList();
        System.out.println("List: " + platformdataList);
        ArrayList <Platformdata> platformdataArrayList = new ArrayList<Platformdata>(platformdataList);
        System.out.println("ArrayList: " + platformdataArrayList);
        return platformdataArrayList;
    }


    /* ---- Getters, Setters, ToString Method ---- */
    /* Apparently bind requests need getters and setters. So does Ebean if Play ByteCode Enhancer is not used. */
    public Long getPlatformdataId() {
        return platformdataId;
    }

    public void setPlatformdataId(Long platformdataId) {
        this.platformdataId = platformdataId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getPlatformOrServiceType() {
        return platformOrServiceType;
    }

    public void setPlatformOrServiceType(String platformOrServiceType) {
        this.platformOrServiceType = platformOrServiceType;
    }

    public String getTargetGroup() {
        return targetGroup;
    }

    public void setTargetGroup(String targetGroup) {
        this.targetGroup = targetGroup;
    }

    public String getCommercialOrNoncommercialService() {
        return commercialOrNoncommercialService;
    }

    public void setCommercialOrNoncommercialService(String commercialOrNoncommercialService) {
        this.commercialOrNoncommercialService = commercialOrNoncommercialService;
    }

    public String getFreeOrSubscriptionSource() {
        return freeOrSubscriptionSource;
    }

    public void setFreeOrSubscriptionSource(String freeOrSubscriptionSource) {
        this.freeOrSubscriptionSource = freeOrSubscriptionSource;
    }

    public String getPrimaryPurpose() {
        return primaryPurpose;
    }

    public void setPrimaryPurpose(String primaryPurpose) {
        this.primaryPurpose = primaryPurpose;
    }

    public String getGeographicalReach() {
        return geographicalReach;
    }

    public void setGeographicalReach(String geographicalReach) {
        this.geographicalReach = geographicalReach;
    }

    public String getNumberActiveOrRegisteredUsers() {
        return numberActiveOrRegisteredUsers;
    }

    public void setNumberActiveOrRegisteredUsers(String numberActiveOrRegisteredUsers) {
        this.numberActiveOrRegisteredUsers = numberActiveOrRegisteredUsers;
    }

    public String getContentCoverage() {
        return contentCoverage;
    }

    public void setContentCoverage(String contentCoverage) {
        this.contentCoverage = contentCoverage;
    }

    public String getWebsiteURL() {
        return websiteURL;
    }

    public void setWebsiteURL(String websiteURL) {
        this.websiteURL = websiteURL;
    }

    public String getUsageRanking() {
        return usageRanking;
    }

    public void setUsageRanking(String usageRanking) {
        this.usageRanking = usageRanking;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String toString() {
        return String.format("%s - %s", platformdataId, platformName);
    }
}

