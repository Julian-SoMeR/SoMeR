package models;

import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;
import play.data.DynamicForm;

import javax.persistence.*;

import play.mvc.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This model contains all the function data contents of the SoMeR. It can only be obtained via
 * a cross reference between its functions and a platform.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
@DocStore
@Entity
@Table(name = "function_content")
public class FunctionContent extends BaseDomain implements PathBindable<FunctionContent> {
    /* These are all attributes that are mapped for the database. */
    @Id
    public Long functionContentId;
    @NotNull
    @Column(columnDefinition = "TEXT default ''")
    public String functionContent;
    @DocEmbedded
    @ManyToOne
    @JoinColumn(name = "function_function_id")
    @NotNull
    public Function function;
    @DocEmbedded
    @ManyToOne
    @JoinColumn(name = "platform_platform_id")
    @NotNull
    public Platform platform;

    /* ----- Constructors ----- */
    public FunctionContent() {
    }

    public FunctionContent(Long functionContentId, String functionContent, Function function, Platform platform) {
        this.functionContentId = functionContentId;
        this.functionContent = functionContent;
        this.function = function;
        this.platform = platform;
    }

    /* ---- Methods necessary for the interface Pathbindable ---- */
    @Override
    public FunctionContent bind(String key, String id) {
        FunctionContent functionContent = findByFunctionContentId(new Long(id));
        if (functionContent == null) {
            throw new IllegalArgumentException("Function content data with functionContentId " + id + " not found");
        }
        return functionContent;
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(functionContentId);
    }

    @Override
    public String javascriptUnbind() {
        return this.functionContent;
    }


    /* ---- Methods ---- */

    /**
     * This method uses the functionContentId of the functionContent entity to find data in the database and bind
     * it to the corresponding model object.
     *
     * @param functionContentId Unique identifier of aggregator content.
     * @return Model object filled with data from the aggregatorContent table.
     */
    public static FunctionContent findByFunctionContentId(Long functionContentId) {
        return Ebean.find(FunctionContent.class).where().and(
                Expr.eq("functionContentId", functionContentId),
                Expr.eq("deleteStatus", 0)
        ).findOne();
    }

    /**
     * This method uses the platformId of the Platform entity to find data in the database and bind
     * it to the FunctionContent model object.
     */
    public static List<FunctionContent> findAllByPlatformId(Long platformId) {
        List<FunctionContent> functionContents = Ebean.find(FunctionContent.class).where().and(
                Expr.eq("platform_platform_id", platformId),
                Expr.eq("deleteStatus", 0)
        ).findList();
        ArrayList<FunctionContent> functionContentArrayList =
                new ArrayList<FunctionContent>(functionContents);
        return functionContentArrayList;
    }

    /**
     * This method uses the functionId of the Function entity to find data in the database and bind
     * it to the FunctionContent model object.
     *
     * @param functionId Id of the Function object.
     * @return List of Function Content objects with the functionId as a foreign key.
     */
    public static List<FunctionContent> findAllByFunctionId(Long functionId) {
        List<FunctionContent> functionContents =
                Ebean.find(FunctionContent.class).where().and(
                        Expr.eq("function_function_id", functionId),
                        Expr.eq("deleteStatus", 0)
                ).findList();
        ArrayList<FunctionContent> functionContentArrayList =
                new ArrayList<FunctionContent>(functionContents);
        return functionContentArrayList;
    }

    /**
     * This method is used to insert attribute values that need to be displayed in a view
     * because a FunctionContent object does only contain the id of the included platform or function object.
     *
     * @param functionContents Given list contain all the necessary FunctionContent objects.
     * @return List of FunctionContent objects with all necessary attributes inserted.
     */
    public static List<FunctionContent> fillForeignKeyObjects(List<FunctionContent> functionContents) {
        for (FunctionContent currentElement : functionContents) {
            currentElement.platform.setPlatformName(
                    Platform.findByPlatformId(currentElement.platform.platformId).getPlatformName());
            currentElement.function.setFunctionName(
                    Function.findByFunctionId(currentElement.function.functionId).getFunctionName());
            currentElement.function.setFunctionCategory(
                    Function.findByFunctionId(currentElement.function.functionId).getFunctionCategory());
        }
        return functionContents;
    }

    /**
     * Map all the data of the request form to Platform and FunctionContent objects. If the data is to be updated,
     * load the existing data into the objects. Else create new objects to save everything.
     *
     * @param requestForm Form object containing all the data form an html form.
     * @param platform    Platform object for use on update.
     * @return List of FunctionContents containing all the necessary data to render a page or save the objects.
     */
    public static List<FunctionContent> formToFunctionContents(DynamicForm requestForm, Platform platform) {
        List<FunctionContent> functionContents = new LinkedList<>();
        String categoryString = requestForm.get("functionCategory");
        List<Function> functionList = Function.findAllFunctionsOfCategory(categoryString);

        for (Function currentElement : functionList) {
            String functionContentIdString = requestForm.get("functionContentId-" + currentElement.functionId);

            if (functionContentIdString != null && !functionContentIdString.isEmpty()) {
                Long functionContentId = Long.parseLong(functionContentIdString);
                FunctionContent existingFunctionContent =
                        FunctionContent.findByFunctionContentId(functionContentId);
                String contentString = requestForm.get("functionContent-" + currentElement.functionId);
                existingFunctionContent.setFunctionContent(contentString);
                functionContents.add(existingFunctionContent);
            } else {
                FunctionContent newFunctionContent = new FunctionContent();
                newFunctionContent.setPlatform(platform);
                newFunctionContent.setFunction(currentElement);
                String contentString = requestForm.get("functionContent-" + currentElement.functionId);
                newFunctionContent.setFunctionContent(contentString);
                functionContents.add(newFunctionContent);
            }
        }
        return functionContents;
    }

    /**
     * Filter the FunctionContents by the category that is currently displayed in the view. The currently
     * active category is received by the category url string.
     *
     * @param functionContents Function Content objects to filter.
     * @param categoryUrl Category url string as filter criteria.
     * @return Filtered list of Function Content objects for display in a view.
     */
    public static List<FunctionContent> filterCurrentCategory(
            List<FunctionContent> functionContents,String categoryUrl) {
        List<FunctionContent> filteredFunctionContents = new LinkedList<>();

        for (FunctionContent currentElement: functionContents) {
            String currentCategory = currentElement.function.getFunctionCategory();
            currentCategory = currentCategory.replaceAll("[^A-Za-z0-9]", "");
            currentCategory = currentCategory.toLowerCase();
            if (currentCategory.equals(categoryUrl)) {
                filteredFunctionContents.add(currentElement);
            }
        }
        return  filteredFunctionContents;
    }

    /* ---- Getters, Setters, ToString Method ---- */

    public Long getFunctionContentId() {
        return functionContentId;
    }

    public void setFunctionContentId(Long functionContentId) {
        this.functionContentId = functionContentId;
    }

    public String getFunctionContent() {
        return functionContent;
    }

    public void setFunctionContent(String functionContent) {
        this.functionContent = functionContent;
    }

    public Function getFunction() {
        return function;
    }

    public void setFunction(Function function) {
        this.function = function;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String toString() {
        return "FunctionContent{" +
                "functionContentId=" + functionContentId +
                ", function=" + function +
                ", platform=" + platform +
                '}';
    }
}
