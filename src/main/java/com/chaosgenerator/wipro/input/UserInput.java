package com.chaosgenerator.wipro.input;

import java.net.URL;
import java.util.Objects;

public class UserInput {

    private URL domainToCrawl;
    private String sitemapPath;

    public URL getDomainToCrawl() {
        return domainToCrawl;
    }

    public void setDomainToCrawl(URL domainToCrawl) {
        this.domainToCrawl = domainToCrawl;
    }

    public String getSitemapPath() {
        return sitemapPath;
    }

    public void setSitemapPath(String sitemapPath) {
        this.sitemapPath = sitemapPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserInput)) return false;
        UserInput userInput = (UserInput) o;
        return Objects.equals(getDomainToCrawl(), userInput.getDomainToCrawl()) &&
                Objects.equals(getSitemapPath(), userInput.getSitemapPath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDomainToCrawl(), getSitemapPath());
    }
}
