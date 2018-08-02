package controllers;

import play.*;
import play.mvc.*;
import views.html.*;
import models.*;
import javax.inject.*;

public class SearchController extends Controller {
    public Result search(Search searchObject) {
        System.out.println("Query: " + searchObject.queryString);
        return ok(search.render(searchObject));
    }

}
