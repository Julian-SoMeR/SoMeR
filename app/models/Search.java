package models;

import play.mvc.*;
import io.ebean.DocumentStore;
import io.ebean.EbeanServer;
import io.ebean.Ebean;
import io.ebean.*;
import io.ebean.annotation.*;
import io.ebean.Finder;
import io.ebean.Query;
import java.util.Map;
import java.util.Optional;


public class Search implements QueryStringBindable<Search> {
    public Long queryId;
    public String queryString;


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
}
