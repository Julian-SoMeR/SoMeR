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
