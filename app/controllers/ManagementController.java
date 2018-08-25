package controllers;

import play.mvc.*;
import play.*;
import views.html.*;
import models.*;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.swing.text.StyledEditorKit;

import play.data.Form;
import play.data.FormFactory;
import play.data.DynamicForm;

import java.util.*;

public class ManagementController extends Controller {
    // Dynamic form to bind data of multiple models from an html form to a form object.
    private DynamicForm requestForm;

    /**
     * Constructor that initializes the dynamic request form.
     *
     * @param formFactory Injected FormFactory necessary for the dynamic form.
     */
    @Inject
    public ManagementController(FormFactory formFactory) {
        this.requestForm = formFactory.form();
    }

    /**
     * Render the management page.
     *
     * @return Render HTML template with http status code 400.
     */
    public Result management() {
        return ok(management.render());
    }

    public Result platformPropertiesInformation() {
        List<Information> informationList = Information.findAllInformation();
        return ok(managementplatformpropertiesinformation.render(informationList));
    }

    public Result saveInformation() {
        // Bind the data of the html form to the dynamic form object
        DynamicForm requestData = requestForm.bindFromRequest();
        List<Information> informationList = Information.formToInformation(requestData);
        System.out.println("DATA: " + informationList);

        for (Information currentElement : informationList) {
            if (!(currentElement.informationName == null)) {
                // If the element contains information remove leading or trailing whitespaces.
                if (!currentElement.informationName.isEmpty()) {
                    currentElement.setInformationName(currentElement.informationName.trim());
                }

                // If the information is either empty, contains an alphanumeric character, it is valid.
                // This is done to prevent inputs only containing special characters.
                if (currentElement.informationName.isEmpty()
                        || currentElement.informationName.matches(".*\\w.*")
                        || currentElement.informationName.matches(".*\\d.*")) {
                    // If there is no entry with the current informationContentId create a new entry,
                    // else update existing entries.
                    if (currentElement.informationId == null) {
                        currentElement.save();
                    } else {
                        // Only update Information if there are changes.
                        Information informationNameBeforeSave =
                                Information.findByInformationId(currentElement.informationId);
                        if (!informationNameBeforeSave.informationName.equals(currentElement.informationName)) {
                            currentElement.update();
                        }
                    }
                } else {
                    flash("information_name_error" + currentElement.informationId,
                            "'" + currentElement.informationName + "' is not a valid information name."
                                    + " Please try using at least one letter or number!");
                }
            } else {
                return badRequest("This won't work!");
            }
        }
        flash("success", "Save successful.");
        return ok(managementplatformpropertiesinformation.render(informationList));
    }

    /**
     * Delete information and all connected data in InformationContent.
     *
     * @param informationId Id of the information to delete.
     * @return On successful delete, render HTML template with http status code 400. Else display NOT FOUND message.
     */
    public Result deleteInformation(Long informationId) {
        Information information = Information.findByInformationId(informationId);
        List<InformationContent> informationContents = InformationContent.findAllByInformationId(informationId);
        if (information == null) {
            return notFound(String.format("Information %s does not exist.", informationId));
        }
        for (InformationContent currentElement : informationContents) {
            currentElement.setDeleteStatus(true);
            currentElement.update();
        }
        information.setDeleteStatus(true);
        information.update();
        flash("delete-success", "Deletion successful.");
        return redirect(routes.ManagementController.platformPropertiesInformation());
    }

    public Result platformPropertiesFunction() {
        return ok(managementplatformpropertiesfunction.render());
    }

    public Result platformPropertiesImpact() {
        return ok(managementplatformpropertiesimpact.render());
    }

    public Result aggregatorProperties() {
        return TODO;
    }
}
