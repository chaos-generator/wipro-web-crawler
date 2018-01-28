// I'd normally call this package domain, but to avoid confusion with the
// overall language of this app, I've called it pojo, which should be fairly
// descriptive.
package com.chaosgenerator.wipro.webcrawler.pojo;

import java.util.Objects;

public class Page {

    private String title;
    private final String url;

    public Page(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Page page = (Page) o;
        return Objects.equals(url, page.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
