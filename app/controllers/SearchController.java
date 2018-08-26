package controllers;

import play.*;
import play.mvc.*;
import views.html.*;
import models.*;

import javax.inject.*;
import java.util.List;

public class SearchController extends Controller {
    public Result search(Search searchObject) {
            List<Platform> platforms = Search.queryAllPlatforms(searchObject.queryString);
            List<InformationContent> informationContents = Search.queryAllInformationContents(searchObject.queryString);
            List<FunctionContent> functionContents = Search.queryAllFunctionContents(searchObject.queryString);
            List<ImpactContent> impactContents = Search.queryAllImpactContents(searchObject.queryString);
            searchObject.setResultCount(platforms.size() + informationContents.size()
                    + functionContents.size() + impactContents.size());
            return ok(search.render(searchObject, platforms, informationContents, functionContents, impactContents));
    }

}
