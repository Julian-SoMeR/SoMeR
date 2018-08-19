package models;

import play.mvc.*;
import io.ebean.DocumentStore;
import io.ebean.EbeanServer;
import io.ebean.Ebean;
import io.ebean.*;
import io.ebean.annotation.*;
import io.ebean.Finder;
import io.ebean.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Search implements QueryStringBindable<Search> {
    public Long queryId;
    public String queryString;

    /* Result lists for each type of data. */
    private static List<Platform> platformList;
    private static List<FunctionContent> functionContents;
    private static List<ImpactContent> impactContents;

    /* Methods necessary for the interface QueryStringBindable. */
    @Override
    public Optional<Search> bind(String key, Map<String, String[]> data) {

        try{
            queryString = new String(data.get("query")[0]);
            return Optional.of(this);

        } catch (Exception e){ // no parameter match return None
            return Optional.empty();
        }
    }

    @Override
    public String unbind(String key) {
        return new StringBuilder()
                .append("query=")
                .append(queryString)
                .toString();
    }

    @Override public String javascriptUnbind() {
        return this.queryString;
    }

    /* ---- Methods ---- */
    public static List<Platform> queryAllPlatforms(String queryString) {
        platformList = Ebean.find(Platform.class).setUseDocStore(true).where()
                .ilike("platformName" ,"%" + queryString + "%").orderBy("platformName asc").findList();
        System.out.println("Query String: " + queryString);
        //if (platformList.isEmpty()) {
        //    System.out.println("Platform List is empty");
        //}
        ArrayList<Platform> platformArrayList = new ArrayList<Platform>(platformList);
        System.out.println("Platform Query ArrayList: " + platformArrayList);
        return platformArrayList;
    }

    public static List<FunctionContent> queryAllFunctionContents(String queryString) {
        functionContents = Ebean.find(FunctionContent.class).setUseDocStore(true).where()
                .ilike("functionContent" ,"%" + queryString + "%").findList();
        ArrayList<FunctionContent> functionContentArrayList = new ArrayList<FunctionContent>(functionContents);
        System.out.println("Value Query ArrayList: " + functionContentArrayList);
        return functionContentArrayList;
    }

    public static List<ImpactContent> queryAllImpactContents(String queryString) {
        impactContents = Ebean.find(ImpactContent.class).setUseDocStore(true).where()
                .ilike("impactContent" ,"%" + queryString + "%").findList();
        ArrayList<ImpactContent> impactContentArrayList = new ArrayList<ImpactContent>(impactContents);
        System.out.println("Impact Query ArrayList: " + impactContentArrayList);
        return impactContentArrayList;
    }



    /* ---- Getters, Setters, ToString Method ---- */
    public Long getQueryId() {
        return queryId;
    }

    public void setQueryId(Long queryId) {
        this.queryId = queryId;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public String toString() {
        return "Search{" +
                "queryId=" + queryId +
                ", queryString='" + queryString + '\'' +
                '}';
    }
}
