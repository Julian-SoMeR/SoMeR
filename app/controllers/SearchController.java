package controllers;

import play.*;
import play.mvc.*;
import views.html.*;
import models.*;
import javax.inject.*;
import java.util.List;

public class SearchController extends Controller {
    public Result search(Search searchObject) {
        System.out.println("Query: " + searchObject.queryString);
        List<Platformdata> platformdataList = Search.queryAllPlatforms(searchObject.queryString);
        List<FunctionContent> functionContents = Search.queryAllFunctionContents(searchObject.queryString);
        return ok(search.render(searchObject, platformdataList, functionContents));
    }

}
