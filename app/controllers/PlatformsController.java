package controllers;

import play.mvc.*;
import play.*;
import views.html.*;
import models.*;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import play.data.Form;
import play.data.FormFactory;
import play.data.DynamicForm;
import java.util.*;

public class PlatformsController extends Controller {

    private DynamicForm requestForm;
    private List<InformationContent> informationContents;
    //private Form<Platform> platformForm;
    //private Form<InformationContent> informationContentForm;

    @Inject
    public PlatformsController(FormFactory formFactory) {
        //this.platformForm = formFactory.form(Platform.class);
        this.requestForm = formFactory.form();
        //this.informationContentForm = formFactory.form(InformationContent.class);
    }

    public Result platforms(Integer page) {
        List<Platform> platformList = Platform.findAllPlatforms();
        return ok(platforms.render(platformList));
    }

    /* Render the platform details page without any data in the form so that a new entry can be created. */
    public Result createNewPlatform() {

        return ok(platformdetails.render(null));
    }

    /* This method renders the form data of a platform object that already exists. Editing possible. */
    public Result showSelectedPlatform(Long platformId) {
        final Platform platform = Platform.findByPlatformId(platformId);
        //List<InformationContent> informationContents = InformationContent.findAllByPlatformId(platformId);
        InformationContent informationContent = new InformationContent();
        if (platform == null) {
            return notFound(String.format("Platform %d does not exist.", platformId));
        }
        //Form<Platform> filledPlatformForm = platformForm.fill(platform);
        return ok(platformdetails.render(platform));
    }

    /* Adding delete functionality, just for testing purposes. This will later be in the "Management" tab. */
    public Result deletePlatform(Long platformId) {
        final Platform platform = Platform.findByPlatformId(platformId);
        if (platform == null) {
            return notFound(String.format("Platform %s does not exist.", platformId));
        }
        platform.delete();
        return redirect(routes.PlatformsController.platforms(1));
    }

    public Result savePlatform() {

        DynamicForm requestData = requestForm.bindFromRequest();
        System.out.println("DYNAMICFORM: " + requestData);
        //Form<Platform> boundPlatformForm = platformForm.bindFromRequest();
        //Platform platform = boundPlatformForm.bindFromRequest().get();
        //System.out.println("content: " + boundPlatformForm);
        Platform platform = Platform.formToPlatform(requestData);
        informationContents = InformationContent.formToInformationContents(requestData);
        // If the form has errors, display an error message to the user.
        if (requestData.hasErrors()) {
            flash("error", "Please correct the form below.");
            return badRequest(platformdetails.render(platform));
        }
        // If there is no entry with the current platformId create a new entry, else update existing entries.
        if (platform.platformId == null) {
            platform.save();
        }
        platform.update();
        flash("success",
                String.format("Successfully saved platform %s", platform));
        return redirect(routes.PlatformsController.platforms(1));
    }
}
