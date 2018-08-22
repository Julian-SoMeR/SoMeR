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

    /**
     * Render the platform details page without any data in the form so that a new entry can be created.
     *
     * @return Render HTML template with http status code 400.
     */
    public Result createNewPlatform() {
        return ok(platformcreate.render());
    }

    /**
     * This method renders the form data of a platform object that already exists. Editing possible.
     *
     * @param platformId Id of the selected platform on the platforms page.
     * @return Render HTML template with http status code 400 or display NOT FOUND message.
     */
    public Result showSelectedPlatformInformation(Long platformId) {
        List<InformationContent> informationContents;
        Platform platform = Platform.findByPlatformId(platformId);
        informationContents = InformationContent.findAllByPlatformId(platformId);
        informationContents = InformationContent.fillForeignKeyObjects(informationContents);
        if (platform == null) {
            return notFound(String.format("Platform %d does not exist.", platformId));
        }
        return ok(platformgeneralinformation.render(informationContents, platform));
    }

    public Result showSelectedPlatformFunctions(Long platformId) {
        List<FunctionContent> functionContents;
        Platform platform = Platform.findByPlatformId(platformId);
        functionContents = FunctionContent.findAllByPlatformId(platformId);
        functionContents = FunctionContent.fillForeignKeyObjects(functionContents);
        if (platform == null) {
            return notFound(String.format("Platform %d does not exist.", platformId));
        }
        return ok(platformfunction.render(functionContents, platform));
    }

    /* Adding delete functionality, just for testing purposes. This will later be in the "Management" tab. */

    /**
     * Delete platform and all connected data in information/functions/impacts.
     *
     * @param platformId Id of the platform to delete.
     * @return On successful delete, render HTML template with http status code 400. Else display NOT FOUND message.
     */
    public Result deletePlatform(Long platformId) {
        Platform platform = Platform.findByPlatformId(platformId);
        List<InformationContent> informationContents = InformationContent.findAllByPlatformId(platformId);
        if (platform == null) {
            return notFound(String.format("Platform %s does not exist.", platformId));
        }
        for (InformationContent currentElement : informationContents) {
            currentElement.setDeleteStatus(true);
            currentElement.update();
        }
        platform.setDeleteStatus(true);
        platform.update();
        return redirect(routes.PlatformsController.platforms(1));
    }

    /**
     * Creating a new platform and saving all necessary entities bound to the platform.
     * On successful save, redirect to the newly created platform.
     *
     * @return Either stay on createPlatform page and show an error or redirect to newly created platform after saving.
     */
    public Result saveNewPlatform() {
        // Bind the data of the html form to the dynamic form object
        DynamicForm requestData = requestForm.bindFromRequest();
        Boolean platformHasErrors = false;
        // Transfer all the data related to the platform entity into a platform object.
        Platform platform = Platform.formToPlatform(requestData);

        // Remove leading or trailing whitespaces of the platform name before proceeding to save the platform object.
        platform.setPlatformName(platform.platformName.trim());
        // Only save or update a platform if its name did not contained only whitespaces
        if (platform.platformName.length() > 0) {
            // A platform name should also contain either an alphanumeric character or a number to be valid.
            if (platform.platformName.matches(".*\\w.*") || (platform.platformName.matches(".*\\d.*"))) {
                platform.save();

                // If everything went okay, also save empty Information-, Function- and ImpactContent objects to the
                // database.
                List<Information> informationList = Information.findAllInformation();
                for (Information currentElement : informationList) {
                    InformationContent informationContent = new InformationContent();
                    informationContent.setPlatform(platform);
                    informationContent.setInformation(currentElement);
                    informationContent.setInformationContent("");
                    informationContent.save();
                }
                List<Function> functionList = Function.findAllFunctions();
                for (Function currentElement : functionList) {
                    FunctionContent functionContent = new FunctionContent();
                    functionContent.setPlatform(platform);
                    functionContent.setFunction(currentElement);
                    functionContent.setFunctionContent("");
                    functionContent.save();
                }
                List<Impact> impactList = Impact.findAllImpacts();
                for (Impact currentElement : impactList) {
                    ImpactContent impactContent = new ImpactContent();
                    impactContent.setPlatform(platform);
                    impactContent.setImpact(currentElement);
                    impactContent.setImpactContent("");
                    impactContent.save();
                }
            }
        } else {
            platformHasErrors = true;
        }
        if (platformHasErrors || platform.platformId == null) {
            if (platform.platformName.isEmpty()) {
                // Render this error message if there were only whitespaces
                flash("platform_error", "Please enter a platform name!");
            } else {
                // Something goes wrong or the user entered something that didn't contain alphanumeric characters or
                // numbers, render this error message
                flash("platform_error", "'" + platform.platformName
                        + "' is not a valid name for a platform. Please enter a platform name!");
            }
            return redirect(routes.PlatformsController.createNewPlatform());
        } else {
            return redirect(routes.PlatformsController.showSelectedPlatformInformation(platform.platformId));
        }
    }

    /**
     * Saving changes on the platforms functions page.
     * Decide where to route next after successful save. Otherwise display error messages.
     *
     * @return Either stay on the functions page or redirect to platforms page.
     */
    public Result savePlatformFunctions() {
        List<FunctionContent> functionContents;
        // Bind the data of the html form to the dynamic form object
        DynamicForm requestData = requestForm.bindFromRequest();
        Boolean platformHasErrors = false;
        // Transfer all the data related to the platform entity into a platform object.
        Platform platform = Platform.formToPlatform(requestData);

        // Transfer all the data related to the InformationContent entity into a InformationContent object.
        functionContents = FunctionContent.formToFunctionContents(requestData, platform);

        for (FunctionContent currentElement : functionContents) {
            // If the element contains information content remove leading or trailing whitespaces.
            if (!currentElement.functionContent.isEmpty()) {
                currentElement.setFunctionContent(currentElement.functionContent.trim());
            }

            // If the information content is either empty, contains an alphanumeric character or contains a number,
            // it is valid. This is done to prevent inputs only containing special characters.
            if (currentElement.functionContent.isEmpty()
                    || currentElement.functionContent.matches(".*\\w.*")
                    || currentElement.functionContent.matches(".*\\d.*")) {
                // Only update InformationContent if there are changes.
                FunctionContent functionContentBeforeSave =
                        FunctionContent.findByFunctionContentId(currentElement.functionContentId);
                if (!functionContentBeforeSave.functionContent.equals(currentElement.functionContent)) {
                    currentElement.update();
                }
            } else {
                flash("function_content_error" + currentElement.function.functionId,
                        "'" + currentElement.functionContent + "' is not a valid input for this field."
                                + " Please try using at least one letter or number!");
            }
        }
        return redirect(routes.PlatformsController.platforms(1));
    }

    /**
     * Bind data from an html form and bind it to a dynamic form. Check all the input data for errors and whitespaces.
     * If the data is valid, save or update all the data entered. Else redirect and display error messages.
     *
     * @return Either redirect to platforms page on successful save, or redirect to the same page and show errors.
     */
    public Result savePlatform() {
        // Flags to check for errors and make routing decisions
        Boolean platformHasErrors = false;
        Boolean informationContentHasErrors = false;
        Boolean createNewPlatformSubmission = false;

        // Get the value of the submit button set by submitWithValue() javascript method from
        // the http request by name to decide where to route next after successful saves.
        int routingCaseSelector = 0;
        String[] submitValues = request().body().asFormUrlEncoded().get("submit-button");
        if (submitValues == null || submitValues.length == 0) {
            return badRequest("No action provided!");
        } else {
            String submitValueString = submitValues[0];
            // Default save on platforms and platform creation. Redirect to platforms list.
            if (submitValueString.equals("saveandexit")) {
                routingCaseSelector = 1;
                // Redirect to newly created platform after saving
            } else if (submitValueString.equals("platformcreate-saveonly")) {
                routingCaseSelector = 2;
                // Stay on general information page after saving. Same action as above.
            } else if (submitValueString.equals("generalinformation-saveonly")) {
                routingCaseSelector = 2;
                // Redirect to functions of newly created platform after saving
            } else if (submitValueString.equals("platformcreate-saveredirectfunctions")) {
                routingCaseSelector = 3;
                // Redirect to impacts of newly created platform after saving
            } else if (submitValueString.equals("platformcreate-saveredirectimpacts")) {
                routingCaseSelector = 4;
                // Action provided does not exist.
            } else {
                return badRequest("Action provided didn't match anything known.");
            }
        }

        List<InformationContent> informationContents;
        // Bind the data of the html form to the dynamic form object
        DynamicForm requestData = requestForm.bindFromRequest();

        // Transfer all the data related to the platform entity into a platform object.
        Platform platform = Platform.formToPlatform(requestData);

        // Remove leading or trailing whitespaces of the platform name before proceeding to save the platform object.
        platform.setPlatformName(platform.platformName.trim());
        // Only save or update a platform if its name did not contained only whitespaces
        if (platform.platformName.length() > 0) {
            // A platform name should also contain either an alphanumeric character or a number to be valid.
            if (platform.platformName.matches(".*\\w.*") || (platform.platformName.matches(".*\\d.*"))) {
                // If there is no entry with the current platformId create a new entry, else update existing entries.
                if (platform.platformId == null) {

                    platform.save();
                    createNewPlatformSubmission = true;
                } else {
                    // Only update a platform if there are changes
                    Platform platformBeforeSave = Platform.findByPlatformId(platform.platformId);
                    if (!platformBeforeSave.platformName.equals(platform.platformName)) {
                        platform.update();
                    }
                    createNewPlatformSubmission = true;
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
                    || currentElement.informationContent.matches(".*\\d.*")) {
                // Never save anything, if the platform hasn't been saved before. FOR NOW..
                if (createNewPlatformSubmission) {
                    // If there is no entry with the current informationContentId create a new entry,
                    // else update existing entries.
                    if (currentElement.informationContentId == null) {
                        currentElement.save();
                    } else {
                        // Only update InformationContent if there are changes.
                        InformationContent informationContentBeforeSave =
                                InformationContent.findByInformationContentId(currentElement.informationContentId);
                        if (!informationContentBeforeSave.informationContent.equals(currentElement.informationContent)) {
                            currentElement.update();
                        }
                    }
                }
            } else {
                informationContentHasErrors = true;
                flash("information_content_error" + currentElement.information.informationId,
                        "'" + currentElement.informationContent + "' is not a valid input for this field."
                                + " Please try using at least one letter or number!");
            }
        }
        if (informationContentHasErrors || platformHasErrors) {
            // If there is no platform id, we tried to save a new platform.
            // Thus we need to render the platform creation template.
            if (platform.platformId == null) {
                return redirect(routes.PlatformsController.createNewPlatform());
            } else {
                // Otherwise we need to redirect to the platform details page of the specific platform.;
                return redirect(routes.PlatformsController.showSelectedPlatformInformation(platform.platformId));
            }
        }
        // If none of the error flags is true, show a success message
        flash("success", "Save successful.");
        // Decide where to route next.
        switch (routingCaseSelector) {
            case 0:
                return badRequest("RoutingCaseSelector didn't change. This should be impossible.");

            case 1:
                return redirect(routes.PlatformsController.platforms(1));

            case 2:
                return redirect(routes.PlatformsController.showSelectedPlatformInformation(platform.platformId));

            case 3:
                return redirect(routes.PlatformsController.showSelectedPlatformFunctions(platform.platformId));

            case 4:
                return redirect(routes.PlatformsController.platforms(1));

            default:
                return badRequest("Something went horribly wrong!");
        }
    }
}
