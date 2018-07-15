package controllers;

import play.mvc.*;

import play.*;
import views.html.*;
import views.html.platforms;
import views.html.platformdetails;

import models.*;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

import play.data.Form;
import play.data.FormFactory;

import java.util.List;

public class PlatformsController extends Controller {

    /* Empty form constant to bind data for "Create New Platform" Form */
    /* This is how should work but doesn't... */
    //@Inject FormFactory formFactory;
    //Form<Platform> platformForm = formFactory.form(Platform.class).bindFromRequest();

    /* And this is how it shouldn't work but actually does.... */
    private Form<Platform> platformForm;
    private Form<Platform> pla;

    @Inject
    public PlatformsController(FormFactory formFactory) {
        this.platformForm = formFactory.form(Platform.class);
    }

    public Result platforms() {
        List<Platform> platformList = Platform.findAllPlatforms();
        return ok(platforms.render(platformList));
    }

    /* Render the platform details page without any data in the form so that a new entry can be created. */
    public Result createNewPlatform() {
        return ok(platformdetails.render(platformForm));
    }

    /* This method renders the form data of a platform object that already exists. Editing possible. */
    public Result showSelectedPlatform(String platformID) {
        final Platform platform = Platform.findByPlatformID(platformID);
        if (platform == null) {
            return notFound(String.format("Platform %s does not exist.", platformID));
        }
        Form<Platform> filledPlatformForm = platformForm.fill(platform);
        return ok(platformdetails.render(filledPlatformForm));
    }

    /* Adding delete functionality, just for testing purposes. This will later be in the "Management" tab. */
    public Result deletePlatform(String platformID) {
        final Platform platform = Platform.findByPlatformID(platformID);
        if(platform == null) {
            return notFound(String.format("Platform %s does not exist.", platformID));
        }
        Platform.remove(platform);
        return redirect(routes.PlatformsController.platforms());
    }


    public Result savePlatform() {
        Form<Platform> boundPlatformForm = platformForm.bindFromRequest();
        if (boundPlatformForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(platformdetails.render(boundPlatformForm));
        }
        Platform platform = boundPlatformForm.bindFromRequest().get();
        platform.save();
        flash("success",
                String.format("Successfully saved platform %s", platform));
        return redirect(routes.PlatformsController.platforms());
    }
}
