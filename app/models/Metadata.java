package models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints;

import io.ebean.Model;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Metadata {
    @Id
    public Long metadataID;
    public String metadataName;
    public String metadataCategory;
    public String metadataSubcategory;
    public String metadataDescription;

    public String toString() {
        return String.format("%s - %s", metadataID, metadataName);
    }
}
