package controllers;

import play.*;
import play.mvc.*;
import views.html.*;
import models.*;
import javax.inject.*;
import play.routing.JavaScriptReverseRouter;
import java.util.*;

public class ApplicationController extends Controller {

    public Result home() {
        return ok(home.render());
    }

    public Result aggregators() {
        return ok(aggregators.render());
    }

    public Result howTo() {
        return ok(howto.render());
    }

    public Result about() {
        return ok(about.render());
    }

    public Result imprint() {
        return ok(imprint.render());
    }

    public Result metrics() {
        return redirect("https://metrics-project.net");
    }

    // This renders the cookie-policy page.
    public  Result cookies() {
        return ok(cookiepolicy.render());
    }

    //Defining a Javascript Router
    public Result javascriptRoutes() {
        return ok(
                JavaScriptReverseRouter.create("jsRoutes",
                        routes.javascript.PlatformsController.savePlatform(),
                        routes.javascript.PlatformsController.showSelectedPlatformFunctions(),
                        routes.javascript.PlatformsController.platforms(),
                        routes.javascript.ManagementController.saveInformation(),
                        routes.javascript.ManagementController.createNewInformation()
                )
        ).as("text/javascript");
    }
}