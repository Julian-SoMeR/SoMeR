package models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import play.data.validation.Constraints;

import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Platform {
    @Id@Constraints.Required
    public String platformID;

    @Constraints.Required
    public String platformName;
    /* The odd names result from keeping the original column names in the database. They are auto-generated in the db.*/
    public String platformOrServiceType;
    public String targetGroup;
    public String commercialOrNoncommercialService;
    public String freeOrSubscriptionSource;
    public String primaryPurpose;
    public String geographicalReach;
    public String numberActiveOrRegisteredUsers;
    public String contentCoverage;
    public String websiteURL;
    public String usageRanking;
    public String description;

    /* Mocking data for testing purposes: */
    private static List<Platform> platforms;

    static {
        platforms = new ArrayList<Platform>();
        platforms.add(new Platform("1", "Academia.edu"));
        platforms.add(new Platform("2", "Airity Library"));
        platforms.add(new Platform("3", "Arnetminer"));
        platforms.add(new Platform("4", "Bibsonomy"));
        platforms.add(new Platform("5", "CiteULike"));
        platforms.add(new Platform("6", "Dailymotion"));
        platforms.add(new Platform("7", "del.icio.us"));
        platforms.add(new Platform("8", "Dryar (repository)"));
        platforms.add(new Platform("9", "Epernicus"));
        platforms.add(new Platform("10", "EBSCO"));
    }

    public Platform() {
    }

    public Platform(String platformID, String platformName) {
        this.platformID = platformID;
        this.platformName = platformName;
    }

    public Platform(String platformID, String platformName, String platformOrServiceType, String targetGroup,
                    String commercialOrNoncommercialService, String freeOrSubscriptionSource, String primaryPurpose,
                    String geographicalReach, String numberActiveOrRegisteredUsers, String contentCoverage,
                    String websiteURL, String usageRanking, String description) {
        this.platformID = platformID;
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

    /* Some data manipulation methods */
    public static List<Platform> findAllPlatforms() {
        return new ArrayList<Platform>(platforms);
    }

    public static Platform findByPlatformID(String platformID) {
        for (Platform currentElement : platforms) {
            if (currentElement.platformID.equals(platformID)) {
                return currentElement;
            }
        }
        return null;
    }

    public List<Platform> findByPlatformName(String searchTerm) {
        final List<Platform> resultPlatformList = new ArrayList<Platform>();
        for (Platform currentElement : platforms) {
            if (currentElement.platformName.toLowerCase().contains(searchTerm.toLowerCase())) {
                resultPlatformList.add(currentElement);
            }
        }
        return resultPlatformList;
    }

    public static boolean remove(Platform platform) {
        return platforms.remove(platform);
    }

    public void save() {
        platforms.remove(findByPlatformID(this.platformID));
        platforms.add(this);
    }

    /* Apparently bind requests need getters and setters. So does Ebean if Play ByteCode Enhancer is not used. */

    public String getPlatformID() {
        return platformID;
    }

    public void setPlatformID(String platformID) {
        this.platformID = platformID;
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
        return String.format("%s - %s", platformID, platformName);
    }
}

