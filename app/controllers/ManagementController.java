package controllers;

import models.history.*;
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

import io.ebean.RawSql;
import io.ebean.RawSqlBuilder;

import io.ebean.*;
import io.ebean.SqlQuery;
import io.ebean.SqlRow;
import io.ebean.SqlUpdate;

import java.sql.Timestamp;
import java.util.*;

/**
 * Controller Class for the SoMeR Management page. Everything happening on this page goes here.
 */
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
        return redirect(routes.ManagementController.platformPropertiesInformation());
    }

    public Result platformPropertiesInformation() {
        List<Information> informationList = Information.findAllInformation();
        return ok(managementplatformpropertiesinformation.render(informationList));
    }


    /**
     * Creating a new platform and saving all necessary entities bound to the platform.
     * On successful save, redirect to the newly created platform.
     *
     * @return Either stay on createPlatform page and show an error or redirect to newly created platform after saving.
     */
    public Result createNewInformation() {
        // Bind the data of the html form to the dynamic form object
        DynamicForm requestData = requestForm.bindFromRequest();
        // Transfer all the data related to the platform entity into a platform object.
        Information information = Information.formToNewInformation(requestData);

        if (information.informationName != null || !(information.informationName.isEmpty())) {
            // Remove leading or trailing whitespaces of the information name before proceeding
            // to save the information object.
            information.setInformationName(information.informationName.trim());
            // Only save a new information if its name did not contained only whitespaces
            if (information.informationName.length() > 0) {
                // A platform name should also contain either an alphanumeric character or a number to be valid.
                if (information.informationName.matches(".*\\w.*")
                        || (information.informationName.matches(".*\\d.*"))) {
                    information.save();
                    // If everything went okay, also save a new empty InformationContent with platformId and
                    // informationId set.
                    List<Platform> platforms = Platform.findAllPlatforms();
                    for (Platform currentElement : platforms) {
                        InformationContent informationContent = new InformationContent();
                        informationContent.setPlatform(currentElement);
                        informationContent.setInformation(information);
                        informationContent.setInformationContent("");
                        informationContent.save();
                    }
                    flash("success", "Save successful.");
                } else {
                    flash("new_information_name_error",
                            "'" + information.informationName + "' is not a valid information name."
                                    + " Please try using at least one letter or number!");
                }
            } else {
                flash("new_information_name_error",
                        "Please enter information name!");
            }
        } else {
            return badRequest("This won't work!");
        }
        return redirect(routes.ManagementController.platformPropertiesInformation());
    }

    public Result saveInformation() {
        // Bind the data of the html form to the dynamic form object
        DynamicForm requestData = requestForm.bindFromRequest();
        List<Information> informationList = Information.formToInformation(requestData);

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
                        flash("success", "Save successful.");
                    } else {
                        // Only update Information if there are changes.
                        Information informationNameBeforeSave =
                                Information.findByInformationId(currentElement.informationId);
                        if (!informationNameBeforeSave.informationName.equals(currentElement.informationName)) {
                            currentElement.update();
                            flash("success", "Save successful.");
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
        return redirect(routes.ManagementController.platformPropertiesInformation());
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
        List<Function> functions = Function.findDistinctCategories();
        return ok(managementplatformpropertiesfunctioncategories.render(functions));
    }

    /**
     * Delete function and all connected data in FunctionContent.
     *
     * @param functionId Id of the function to delete.
     * @return On successful delete, render HTML template with http status code 400. Else display NOT FOUND message.
     */
    public Result deleteFunction(Long functionId) {
        Function function = Function.findByFunctionId(functionId);
        List<FunctionContent> functionContents = FunctionContent.findAllByFunctionId(functionId);
        if (function == null) {
            return notFound(String.format("Function %s does not exist.", functionId));
        }
        for (FunctionContent currentElement : functionContents) {
            currentElement.setDeleteStatus(true);
            currentElement.update();
        }
        function.setDeleteStatus(true);
        function.update();
        flash("delete-success", "Deletion successful.");
        return redirect(routes.ManagementController.platformPropertiesFunction());
    }

    /**
     * Delete function and all connected data in FunctionContent.
     *
     * @param functionId Id of the function to delete.
     * @return On successful delete, render HTML template with http status code 400. Else display NOT FOUND message.
     */
    public Result deleteFunctionCategory(Function function) {
        List<Function> functions = Function.findAllFunctionsOfCategory(function.functionCategory);
        for (Function currentFunctionElement : functions) {
            if (currentFunctionElement == null) {
                return notFound(String.format("Function %s does not exist.", currentFunctionElement.functionId));
            }
            List<FunctionContent> functionContents =
                    FunctionContent.findAllByFunctionId(currentFunctionElement.functionId);
            for (FunctionContent currentFunctionContentElement : functionContents) {
                if (currentFunctionContentElement == null) {
                    return notFound(String.format(
                            "Function Content %s does not exist.", currentFunctionContentElement.functionContentId));
                }
                currentFunctionContentElement.setDeleteStatus(true);
                currentFunctionContentElement.update();
            }
            currentFunctionElement.setDeleteStatus(true);
            currentFunctionElement.update();
        }
        flash("delete-success", "Deletion successful.");
        return redirect(routes.ManagementController.platformPropertiesFunction());
    }

    // These can be used to implement the missing views.
    public Result platformPropertiesFunctionEmpty() {
        return ok(managementplatformpropertiesfunction.render());
    }

    public Result platformPropertiesImpact() {
        return ok(managementplatformpropertiesimpact.render());
    }

    public Result aggregatorProperties() {
        return ok(managementaggregatorproperties.render());
    }

    /**
     * Prototype method to render the first list of the history tab, sorted by change date and alphabetically otherwise.
     * The sql query is only conceptual and needs some work.
     * @return List of all history entries for all platforms with their last change date.
     */
    public Result showHistory() {
        List<Platform> resultList = new ArrayList<>();
        String sqlQueryString = "select\n" +
                "\tt.ID, max(t.DATE) DATE, p.platform_name NAME\n" +
                "from (\n" +
                "select fc.platform_platform_id ID, max(fch.history_creation_date) DATE\n" +
                "from function_content fc\n" +
                "\tjoin function_content_history fch on fch.function_content_function_content_id = fc.function_content_id\n" +
                "group by fc.platform_platform_id\n" +
                "union\n" +
                "select fc.platform_platform_id ID, max(fh.history_creation_date) DATE\n" +
                "from function_content fc\n" +
                "\tjoin function_history fh on fh.function_function_id = fc.function_function_id\n" +
                "group by fc.platform_platform_id\n" +
                ") t\n" +
                "join platform p on p.platform_id = t.ID\n" +
                "group by t.ID Order by DATE desc, Name asc";

        SqlQuery sqlQuery = Ebean.createSqlQuery(sqlQueryString);
        List<SqlRow> sqlResult = sqlQuery.findList();
        List<HistoryResult> historyResults = new ArrayList<>();
        for(SqlRow curSqlRow : sqlResult){
            HistoryResult historyResult
                    = new HistoryResult(curSqlRow.getString("id"),
                                        curSqlRow.getString("name"),
                                        Timestamp.valueOf(curSqlRow.getString("date")));
            historyResults.add(historyResult);
        }
        System.out.println(historyResults);
        return ok(managementhistory.render(historyResults));
    }

    public Result showHistoryDetails() {
        return  ok(managementhistorydetails.render());
    }

    private List<Platform> analyzeHistory(List<PlatformHistory> platformHistoryList,
                                          List<InformationHistory> informationHistoryList,
                                          List<InformationContentHistory> informationContentHistoryList,
                                          List<ImpactHistory> impactHistoryList,
                                          List<ImpactContentHistory> impactContentHistoryList,
                                          List<FunctionHistory> functionHistoryList,
                                          List<FunctionContentHistory> functionContentHistoryList) {

        List<Platform> resultList = new ArrayList<>();
        for (PlatformHistory currentPlatformHistory : platformHistoryList) {
            Long currentPlatformId = currentPlatformHistory.platform.platformId;
        }
        return resultList;
    }
}
