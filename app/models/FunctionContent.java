package models;

import play.data.validation.Constraints;
import io.ebean.*;
import io.ebean.annotation.*;

import javax.persistence.*;

import play.mvc.*;

import java.util.ArrayList;
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
    @Column(columnDefinition = "TEXT")
    public String functionContent;
    @DocEmbedded
    @ManyToOne
    @JoinColumn(name = "function_function_id")
    public Function function;
    @DocEmbedded
    @ManyToOne
    @JoinColumn(name = "platform_platform_id")
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
        return Ebean.find(FunctionContent.class, functionContentId);
    }

    /**
     * This method uses the platformId of the Platform entity to find data in the database and bind
     * it to the FunctionContent model object.
     */
    public static List<FunctionContent> findAllByPlatformId(Long platformId) {
        List<FunctionContent> functionContents = Ebean.find(FunctionContent.class).where()
                .eq("platform_platform_id", platformId).findList();
        System.out.println("TEST: " + functionContents);
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
        }
        return functionContents;
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
