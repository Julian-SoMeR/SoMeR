package models;

import io.ebean.*;
import io.ebean.annotation.*;
import io.ebean.Model;

import javax.persistence.*;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;
import java.sql.Timestamp;
import play.mvc.*;

/**
 * This superclass generates all attributes that all model object have in common.
 * It would also contain the ids of the models but this results in bigger issues when referencing ids in
 * html-templates.
 */

@MappedSuperclass
public class BaseDomain extends Model {

    @WhenCreated
    Timestamp creationDate;

    @WhenModified
    Timestamp modificationDate;

    @Version
    Long versionNumber;

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    public Timestamp getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(Timestamp modificationDate) {
        this.modificationDate = modificationDate;
    }

    public Long getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(Long versionNumber) {
        this.versionNumber = versionNumber;
    }
}
