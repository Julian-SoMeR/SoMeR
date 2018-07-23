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
    private Form<Platformdata> platformdataForm;

    @Inject
    public PlatformsController(FormFactory formFactory) {
        this.platformdataForm = formFactory.form(Platformdata.class);
    }

    public Result platforms(Integer page) {
        List<Platformdata> platformdataList = Platformdata.findAllPlatforms();
        return ok(platforms.render(platformdataList));
    }

    /* Render the platform details page without any data in the form so that a new entry can be created. */
    public Result createNewPlatform() {
        return ok(platformdetails.render(platformdataForm));
    }

    /* This method renders the form data of a platform object that already exists. Editing possible. */
    public Result showSelectedPlatform(Long platformdataId) {
        final Platformdata platformdata = Platformdata.findByPlatformdataId(platformdataId);
        if (platformdata == null) {
            return notFound(String.format("Platform %d does not exist.", platformdataId));
        }
        Form<Platformdata> filledPlatformdataForm = platformdataForm.fill(platformdata);
        return ok(platformdetails.render(filledPlatformdataForm));
    }

    /* Adding delete functionality, just for testing purposes. This will later be in the "Management" tab. */
    public Result deletePlatform(Long platformdataId) {
        final Platformdata platformdata = Platformdata.findByPlatformdataId(platformdataId);
        if (platformdata == null) {
            return notFound(String.format("Platform %s does not exist.", platformdataId));
        }
        platformdata.delete();
        return redirect(routes.PlatformsController.platforms(1));
    }

    public Result savePlatform() {
        Form<Platformdata> boundPlatformdataForm = platformdataForm.bindFromRequest();
        // If the form has errors, display an error message to the user.
        if (boundPlatformdataForm.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(platformdetails.render(boundPlatformdataForm));
        }
        Platformdata platformdata = boundPlatformdataForm.bindFromRequest().get();
        // If there is no entry with the current platformdataId create a new entry, else update existing entries.
        if (platformdata.platformdataId == null) {
            platformdata.save();
        }
        platformdata.update();
        flash("success",
                String.format("Successfully saved platform %s", platformdata));
        return redirect(routes.PlatformsController.platforms(1));
    }
}
