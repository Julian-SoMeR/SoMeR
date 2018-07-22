package models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints;

import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;

@Entity
public class Metadata extends Model {
    @Id
    public Long metadataID;
    public String metadataName;
    public String metadataCategory;
    public String metadataSubcategory;
    @Column(columnDefinition = "TEXT")
    public String metadataDescription;

    public String toString() {
        return String.format("%s - %s", metadataID, metadataName);
    }
}
