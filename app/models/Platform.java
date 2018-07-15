package models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import play.data.validation.Constraints;

public class Platform {
    @Constraints.Required
    public String platformID;

    @Constraints.Required
    public String platformName;
    /*
    public String platformType;
    public String targetGroup;
    public String commercialityType;
    public String subscriptionType;
    public String primaryPurpose;
    public String geographicalReach;
    public String userCount;
    public String contentCoverage;
    public String websiteURL;
    public String usageRanking;
    public String description;
    */

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

    /*
    public Platform(String platformID, String platformName, String platformType,
                    String targetGroup, String commercialityType, String subscriptionType,
                    String primaryPurpose, String geographicalReach, String userCount,
                    String contentCoverage, String websiteURL, String usageRanking, String description) {

        this.platformID = platformID;
        this.platformName = platformName;
        this.platformType = platformType;
        this.targetGroup = targetGroup;
        this.commercialityType = commercialityType;
        this.subscriptionType = subscriptionType;
        this.primaryPurpose = primaryPurpose;
        this.geographicalReach = geographicalReach;
        this.userCount = userCount;
        this.contentCoverage = contentCoverage;
        this.websiteURL = websiteURL;
        this.usageRanking = usageRanking;
        this.description = description;
    }
    */

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

    /* Apparently bind requests need getters and setters though they really shouldn't... */
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

    public String toString() {
        return String.format("%s - %s", platformID, platformName);
    }
}

