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
 * This model contains all the platform data of the SoMeR.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
@DocStore
@Entity
@Table(name = "platform")
public class Platform extends BaseDomain implements PathBindable<Platform> {
    /* These are all attributes that are mapped for the database. The JPA/Ebean annotations are used to tell Play how
    to generate the tables, contents and relations of the database and provide evolutions. The other annotations
    used tell the html form helper what constraints should be there when a form is submitted. */
    @Id
    public Long platformId;
    @Constraints.Required
    @DocSortable
    public String platformName;

    @DocEmbedded
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "platform")
    public List<FunctionContent> functionContents = new ArrayList<>();

    @DocEmbedded
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "platform")
    public List<ImpactContent> impactContents = new ArrayList<>();

    @DocEmbedded
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "platform")
    public List<InformationContent> informationContents = new ArrayList<InformationContent>();

    // List to collect all platform objects to render them on the platforms page.
    private static List<Platform> platformList;

    /* ----- Constructors ----- */
    public Platform() {
    }
/*
    public Platform(Long platformId, String platformName) {
        this.platformId = platformId;
        this.platformName = platformName;
    }
*/
    public Platform(Long platformId, String platformName, List<FunctionContent> functionContents,
                    List<ImpactContent> impactContents, List<InformationContent> informationContents) {
        this.platformId = platformId;
        this.platformName = platformName;
        this.functionContents = functionContents;
        this.impactContents = impactContents;
        this.informationContents = informationContents;
    }

    /* Methods necessary for the interface Pathbindable */
    @Override
    public Platform bind(String key, String id) {
        Platform platform = findByPlatformId(new Long(id));
        if (platform == null) {
            throw new IllegalArgumentException("Platform with platformId " + id + " not found");
        }
        return platform;
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(platformId);
    }

    @Override
    public String javascriptUnbind() {
        return this.platformName;
    }


    /* ---- Methods ---- */
    /**
     * Uses the platformId of the platform to look up data in the database.
     * @param platformId Id of the corresponding platform.
     * @return The platform object filled with the data found in the database.
     */
    public static Platform findByPlatformId(Long platformId) {
        return Ebean.find(Platform.class, platformId);
    }

    /**
     * Uses the name of the platform to look up data in the database.
     * @param platformName Name of the corresponding platform.
     * @return The platform object filled with the data found in the database.
     */
    public static Platform findByPlatformName(String platformName) {
        return Ebean.find(Platform.class).where().eq("platformName", platformName).findOne();
    }

    /**
     * Looks up all platform data in the database, sorts this data and saves it in a list.
     * @return List of all platform objects filled by the data from the database.
     */
    public static List<Platform> findAllPlatforms() {
        platformList = Ebean.find(Platform.class).orderBy("platformName asc").findList();
        ArrayList <Platform> platformArrayList = new ArrayList<Platform>(platformList);
        System.out.println("Platform ArrayList: " + platformArrayList);
        return platformArrayList;
    }

    /* ---- Getters, Setters, ToString Method ---- */
    /* Bind requests need getters and setters. So does Ebean if Play ByteCode Enhancer is not used. */
    public Long getPlatformId() {
        return platformId;
    }

    public void setPlatformId(Long platformId) {
        this.platformId = platformId;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public List<FunctionContent> getFunctionContents() {
        return functionContents;
    }

    public void setFunctionContents(List<FunctionContent> functionContents) {
        this.functionContents = functionContents;
    }

    public List<ImpactContent> getImpactContents() {
        return impactContents;
    }

    public void setImpactContents(List<ImpactContent> impactContents) {
        this.impactContents = impactContents;
    }

    public List<InformationContent> getInformationContents() {
        return informationContents;
    }

    public void setInformationContents(List<InformationContent> informationContents) {
        this.informationContents = informationContents;
    }

    public static List<Platform> getPlatformList() {
        return platformList;
    }

    public static void setPlatformList(List<Platform> platformList) {
        Platform.platformList = platformList;
    }

    public String toString() {
        return "Platform{" +
                "platformId=" + platformId +
                ", platformName='" + platformName + '\'' +
                '}';
    }
}
