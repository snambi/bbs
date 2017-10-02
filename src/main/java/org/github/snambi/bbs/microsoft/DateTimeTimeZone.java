package org.github.snambi.bbs.microsoft;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Date;

/**
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DateTimeTimeZone {
    private Date dateTime;
    private String timeZone;

    public Date getDateTime() {
        return dateTime;
    }
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
    public String getTimeZone() {
        return timeZone;
    }
    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }
}