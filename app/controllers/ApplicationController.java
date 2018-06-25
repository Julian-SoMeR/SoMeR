package controllers;
import play.*;
import play.mvc.*;
import views.html.*;


public class ApplicationController extends Controller {
    public Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public Result home() {
        return ok(home.render());
    }

    public Result platforms() {
        return ok(platforms.render());
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

    public Result management() {
        return ok(management.render());
    }

    public Result search() {
        return ok(search.render());
    }


}