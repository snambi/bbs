package org.github.snambi.bbs.microsoft.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.github.snambi.bbs.microsoft.beans.EmailAddress;

/**
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Recipient {
    private EmailAddress emailAddress;

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }
}