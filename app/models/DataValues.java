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
public class DataValues extends Model {

    @Column(columnDefinition = "TEXT")
    public String dataValue;

    @Id
    public Long dataValueId;
    public Long metadataId;
    public Long platformId;

    public Metadata metadata;
    public Platform platform;

    public String toString() {
        return String.format("%s - %s - %s", dataValue, platform, metadata);
    }
}