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
import models.*;


public class Search implements QueryStringBindable<Search> {
    public Long queryId;
    public String queryString;

    /* Result lists for each type of data. */
    private static List<Platformdata> platformdataList;
    private static List<Valuedata> valuedataList;
    private static List<Designationdata> designationdataList;

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
    public static List<Platformdata> queryAllPlatforms(String queryString) {
        //platformdataList = Ebean.find(Platformdata.class).orderBy("platformName asc").findList();
        platformdataList = Ebean.find(Platformdata.class).setUseDocStore(true).where()
                .ilike("platformName" ,"%" + queryString + "%").orderBy("platformName asc").findList();
        System.out.println("Platform Query List: " + platformdataList);
        System.out.println("Query String: " + queryString);
        //if (platformdataList.isEmpty()) {
        //    System.out.println("Platform List is empty");
        //}
        ArrayList<Platformdata> platformdataArrayList = new ArrayList<Platformdata>(platformdataList);
        System.out.println("Platform Query ArrayList: " + platformdataArrayList);
        return platformdataArrayList;
    }

    public static List<Valuedata> queryAllValues(String queryString) {
        valuedataList = Ebean.find(Valuedata.class).setUseDocStore(true).where()
                .ilike("valuedataContent" ,"%" + queryString + "%").findList();
        System.out.println("Value Query List: " + valuedataList);
        ArrayList<Valuedata> valuedataArrayList = new ArrayList<Valuedata>(valuedataList);
        System.out.println("Value Query ArrayList: " + valuedataArrayList);
        return valuedataArrayList;
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
