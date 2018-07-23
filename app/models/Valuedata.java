package models;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import play.data.validation.Constraints;

import io.ebean.Model;
import javax.persistence.*;


@Entity
public class Valuedata extends Model {

    @Column(columnDefinition = "TEXT")
    public String valuedataContent;

    @Id
    public Long valuedataId;

    @ManyToOne
    public Designationdata designationdata;
    @ManyToOne
    public Platformdata platformdata;

    public String toString() {
        return String.format("%s - %s - %s", valuedataContent, platformdata, designationdata);
    }
}
