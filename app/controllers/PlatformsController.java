package controllers;

import play.mvc.*;

import play.*;
import views.html.*;
import views.html.platforms;
import views.html.platformform;

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
    @Inject
    public PlatformsController (FormFactory formFactory) {
        this.platformForm = formFactory.form(Platform.class);
    }

    public Result platforms() {
        List<Platform> platformList = Platform.findAllPlatforms();
        return ok(platforms.render(platformList));
    }

    public Result createNewPlatform() {
        return ok(platformform.render(platformForm));
    }

    public Result showSelectedPlatform(String platformID) {
        return TODO;
    }

    public Result savePlatform() {
        Form<Platform> boundPlatformForm = platformForm.bindFromRequest();
        if(boundPlatformForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(platformform.render(boundPlatformForm));
        }
        Platform platform = boundPlatformForm.bindFromRequest().get();
        System.out.println("Before saved: " + platform);
        System.out.println("Form Saved: " + boundPlatformForm.data());
        platform.save();
        System.out.println("TEST GET: " + boundPlatformForm.get().platformName);
        System.out.println("After Saved: " + platform);
        flash("success",
                String.format("Successfully saved platform %s", platform));
        return redirect(routes.ApplicationController.home());
    }
}
