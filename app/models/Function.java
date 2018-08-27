package models;

import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;

import javax.persistence.*;

import play.mvc.*;

/**
 * This model contains all the designation data for the function data of the SoMeR.
 * The JPA/Ebean annotations are used to tell Play how
 * to generate the tables, contents and relations of the database and provide evolutions.
 */
@DocStore
@Entity
@Table(name = "function")
public class Function extends BaseDomain implements PathBindable<Function> {
    /* These are all attributes that are mapped for the database. */
    @Id
    public Long functionId;
    @DocSortable
    @NotNull
    public String functionName;
    @NotNull
    @Column(columnDefinition = "TEXT default ''")
    public String functionCategory;
    @Column(columnDefinition = "TEXT default ''")
    public String functionDescription;

    //@DocEmbedded
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "function")
    public List<FunctionContent> functionContents = new ArrayList<>();

    // List to collect all function objects to render them on the platforms -> functions page.
    private static List<Function> functionList;

    /* ----- Constructors ----- */
    public Function() {
    }

    public Function(Long functionId, String functionName, String functionCategory, String functionDescription, List<FunctionContent> functionContents) {
        this.functionId = functionId;
        this.functionName = functionName;
        this.functionCategory = functionCategory;
        this.functionDescription = functionDescription;
        this.functionContents = functionContents;
    }

    /* Methods necessary for the interface Pathbindable */
    @Override
    public Function bind(String key, String id) {
        Function function = findByFunctionId(new Long(id));
        if (function == null) {
            throw new IllegalArgumentException("Function data with functionId " + id + " not found");
        }
        return function;
    }

    @Override
    public String unbind(String key) {
        return String.valueOf(functionId);
    }

    @Override
    public String javascriptUnbind() {
        return this.functionName;
    }


    /* ---- Methods ---- */

    /**
     * This method uses the functionId of the function entity to find data in the database and bind in to the
     * corresponding model object.
     *
     * @param functionId Unique identifier of functions.
     * @return Model object filled with data from the function table.
     */
    public static Function findByFunctionId(Long functionId) {
        return Ebean.find(Function.class).where().and(
                Expr.eq("functionId", functionId),
                Expr.eq("deleteStatus", 0)
        ).findOne();
    }

    /**
     * Uses the name of the function to look up data in the database.
     *
     * @param functionName Name of the corresponding function.
     * @return The function object filled with the data found in the database.
     */
    public static Function findByFunctionName(String functionName) {
        return Ebean.find(Function.class).where().and(
                Expr.eq("functionName", functionName),
                Expr.eq("deleteStatus", 0)
        ).findOne();
    }

    /**
     * Looks up all function data in the database, sorts this data and saves it in a list.
     *
     * @return List of all function objects filled by the data from the database.
     */
    public static List<Function> findAllFunctions() {
        functionList = Ebean.find(Function.class).where().eq("deleteStatus", 0).orderBy("functionName asc").findList();
        ArrayList<Function> functionArrayList = new ArrayList<Function>(functionList);
        return functionArrayList;
    }

    /**
     * Looks up all function data from one category in the database, sorts this data and saves it in a list.
     *
     * @return List of all function objects filled by the data of one category from the database.
     */
    public static List<Function> findAllFunctionsOfCategory(String functionCategory) {
        functionList = Ebean.find(Function.class).where().and(
                Expr.eq("functionCategory", functionCategory),
                Expr.eq("deleteStatus", 0)
        ).orderBy("functionName asc").findList();
        ArrayList<Function> functionArrayList = new ArrayList<Function>(functionList);
        return functionArrayList;
    }

    /**
     * Get all distinct categories from the Function entity and generate a usable url string from them.
     * The url string is saved in the description field since the entity itself will never be saved and
     * the unchanged category names are needed to display in a view.
     *
     * @return List of Functions with all distinct categories and their url strings.
     */
    public static List<Function> findDistinctCategories() {
        functionList = Ebean.find(Function.class).select("functionCategory").where()
                .eq("deleteStatus", 0).setDistinct(true).findList();
        for (Function currentElement : functionList) {
            String currentFunctionCategory = currentElement.functionCategory;
            currentFunctionCategory = currentFunctionCategory.replaceAll("[^A-Za-z0-9]", "");
            currentFunctionCategory = currentFunctionCategory.toLowerCase();
            // As long as this is never saved, this is the easiest way to have the pathname and
            // the category in the same object
            currentElement.setFunctionDescription(currentFunctionCategory);
        }
        return functionList;
    }

    /**
     * Get one category from the Function entity and generate a usable url string. This is necessary when
     * routing to the view where the categories are displayed for the first time.
     *
     * @return Generated category url string.
     */
    public static String generateFirstCategoryUrlString() {
        functionList = Ebean.find(Function.class).select("functionCategory").where()
                .eq("deleteStatus", 0).setDistinct(true).findList();
        String categoryUrlString = functionList.get(1).functionCategory;
        categoryUrlString = categoryUrlString.replaceAll("[^A-Za-z0-9]", "");
        categoryUrlString = categoryUrlString.toLowerCase();
        return categoryUrlString;
    }

    /* ---- Getters, Setters, ToString Method ---- */

    public Long getFunctionId() {
        return functionId;
    }

    public void setFunctionId(Long functionId) {
        this.functionId = functionId;
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionCategory() {
        return functionCategory;
    }

    public void setFunctionCategory(String functionCategory) {
        this.functionCategory = functionCategory;
    }

    public String getFunctionDescription() {
        return functionDescription;
    }

    public void setFunctionDescription(String functionDescription) {
        this.functionDescription = functionDescription;
    }

    public List<FunctionContent> getFunctionContents() {
        return functionContents;
    }

    public void setFunctionContents(List<FunctionContent> functionContents) {
        this.functionContents = functionContents;
    }

    public String toString() {
        return "Function{" +
                "functionId=" + functionId +
                ", functionName='" + functionName + '\'' +
                ", functionCategory='" + functionCategory + '\'' +
                ", functionDescription='" + functionDescription + '\'' +
                '}';
    }
}
