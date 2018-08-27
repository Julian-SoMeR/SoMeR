package models.history;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class HistoryResult {

    public String id;
    public String name;
    public Timestamp timestamp;
    public String timestampString;

    public HistoryResult(String id, String name, Timestamp timestamp) {
        this.id = id;
        this.name = name;
        this.timestamp = timestamp;
        this.timestampString = createTimeStampString(timestamp);
    }


    public String createTimeStampString(Timestamp timestamp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String timestampString = simpleDateFormat.format(timestamp);
        return timestampString;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getTimestampString() {
        return timestampString;
    }

    public void setTimestampString(String timestampString) {
        this.timestampString = timestampString;
    }

    public String toString() {
        return "HistoryResult{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", timestamp=" + timestamp +
                ", timestampString='" + timestampString + '\'' +
                '}';
    }
}
