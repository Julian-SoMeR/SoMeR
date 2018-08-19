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
    // Dynamic form to bind data of multiple models from an html form to a form object.
    private DynamicForm requestForm;
    // List of all available information descriptors.
    List<Information> informationList = Information.findAllInformation();

    @Inject
    public PlatformsController(FormFactory formFactory) {
        this.requestForm = formFactory.form();
    }

    public Result platforms(Integer page) {
        List<Platform> platformList = Platform.findAllPlatforms();
        return ok(platforms.render(platformList));
    }

    /* Render the platform details page without any data in the form so that a new entry can be created. */
    public Result createNewPlatform() {
        return ok(platformcreate.render(informationList));
    }

    /* This method renders the form data of a platform object that already exists. Editing possible. */
    public Result showSelectedPlatform(Long platformId) {
        List<InformationContent> informationContents;
        Platform platform = Platform.findByPlatformId(platformId);
        informationContents = InformationContent.findAllByPlatformId(platformId);
        informationContents = InformationContent.fillForeignKeyObjects(informationContents);
        if (platform == null) {
            return notFound(String.format("Platform %d does not exist.", platformId));
        }
        return ok(platformdetails.render(informationContents));
    }

    /* Adding delete functionality, just for testing purposes. This will later be in the "Management" tab. */
    public Result deletePlatform(Long platformId) {
        Platform platform = Platform.findByPlatformId(platformId);
        List<InformationContent> informationContents = InformationContent.findAllByPlatformId(platformId);

        if (platform == null) {
            return notFound(String.format("Platform %s does not exist.", platformId));
        }
        for (InformationContent currentElement : informationContents) {
            currentElement.delete();
        }
        platform.delete();

        return redirect(routes.PlatformsController.platforms(1));
    }

    public Result updatePlatformInformation() {
        Boolean platformHasErrors = false;
        Boolean informationContentHasErrors = false;

        List<InformationContent> informationContents;
        // Bind the data of the html form to the dynamic form object
        DynamicForm requestData = requestForm.bindFromRequest();
        //System.out.println("DYNAMICFORM: " + requestData);
        // Transfer all the data related to the platform entity into a platform object.
        Platform platform = Platform.formToPlatform(requestData);

        /* Possible Error Messages
        flash("error", "The platform name '" + platform.platformName + "' is not a valid name for a platform." +
                " Please enter a valid platform name!");
        flash("error", "The '" + currentElement.information.informationName + "' you entered is not valid!");

           // Might be necessary for displaying proper error messages
        informationContents = InformationContent.fillForeignKeyObjects(informationContents);
        */

        // Remove leading or trailing whitespaces of the platform name before proceeding to save the platform object.
        platform.setPlatformName(platform.platformName.trim());
        // Only save or update a platform if its name did not contained only whitespaces
        if (platform.platformName.length() > 0) {
            // A platform name should also contain either an alphanumeric character or a number to be valid.
            if (platform.platformName.matches(".*\\w.*") || (platform.platformName.matches(".*\\d.*"))) {
                // If there is no entry with the current platformId create a new entry, else update existing entries.
                if (platform.platformId == null) {
                    platform.save();
                } else {
                    platform.update();
                }
            } else {
                platformHasErrors = true;
                // Since there is an error, render an error message.
                flash("platform_error", "'" + platform.platformName
                        + "' is not a valid name for a platform. Please enter a platform name!");
            }
        } else {
            platformHasErrors = true;
            // Since there is an error, render an error message.
            flash("platform_error", "Please enter a platform name!");
        }
        // Transfer all the data related to the InformationContent entity into a InformationContent object.
        informationContents = InformationContent.formToInformationContents(requestData, platform);

        for (InformationContent currentElement : informationContents) {
            // If the element contains information content remove leading or trailing whitespaces.
            if (!currentElement.informationContent.isEmpty()) {
                currentElement.setInformationContent(currentElement.informationContent.trim());
            }

            // If the information content is either empty, contains an alphanumeric character or contains a number,
            // it is valid. This is done to prevent inputs only containing special characters.
            if (currentElement.informationContent.isEmpty()
                    || currentElement.informationContent.matches(".*\\w.*")
                    || (currentElement.informationContent.matches(".*\\d.*"))) {
                // If there is no entry with the current informationContentId create a new entry,
                // else update existing entries.
                if (currentElement.informationContentId == null) {
                    currentElement.save();
                } else {
                    currentElement.update();
                }
                } else {
                informationContentHasErrors = true;
                flash("information_content_error" + currentElement.information.informationId, "'" + currentElement.informationContent
                        + "' is not a valid input for this field. Please try using at least one letter or number!");
            }
        }
        if (informationContentHasErrors || platformHasErrors) {
            // If there is no platform id, we tried to save a new platform.
            // Thus we need to render the platform creation template.
            if (platform.platformId == null) {
                return redirect(routes.PlatformsController.createNewPlatform());
            } else {
                // Otherwise we need to redirect to the platform details page of the specific platform.;
                return redirect(routes.PlatformsController.showSelectedPlatform(platform.platformId));
            }
        }
        // If none of the error flags is true, show a success message
        flash("success", "Successfully saved platform '" + platform.platformName + "'.");
        return redirect(routes.PlatformsController.platforms(1));
    }

/*    public Result saveNewPlatform() {
        List<InformationContent> informationContents;
        DynamicForm requestData = requestForm.bindFromRequest();
        //System.out.println("DYNAMICFORM: " + requestData);
        Platform platform = Platform.formToPlatform(requestData);
        informationContents = InformationContent.formToInformationContents(requestData);

        // If the form has errors, display an error message to the user.
        if (requestData.hasErrors() || platform.platformName.isEmpty()) {
            flash("error", "Please correct the form below.");
            return badRequest(platformdetails.render(informationContents));
            //return redirect(routes.PlatformsController.showSelectedPlatform(platform.platformId));
        }
    }
    */
}
