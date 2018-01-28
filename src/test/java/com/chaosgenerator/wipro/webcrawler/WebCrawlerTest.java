package com.chaosgenerator.wipro.webcrawler;

import com.chaosgenerator.wipro.input.UserInput;
import com.chaosgenerator.wipro.webcrawler.pojo.Page;
import com.google.common.collect.Maps;
import org.jsoup.nodes.Document;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import static org.junit.Assert.*;

public class WebCrawlerTest {

    @Test
    public void alreadyVisitedTest() {
        Map<Page, String> sitemap = Maps.newHashMap();

        WebCrawler crawler = new WebCrawler(new UserInput());

        // Visit first page
        String firstUrl = "http://www.wiprodigital.com";
        assertFalse(crawler.alreadyVisited(sitemap, firstUrl));

        // Add page visited to list
        Page validPage1 = new Page(firstUrl);
        sitemap.put(validPage1, null);

        // Visit second page
        String secondUrl = "http://wiprodigital.com/who-we-are/";
        assertFalse(crawler.alreadyVisited(sitemap, secondUrl));

        // Add page visited to list
        Page validPage2 = new Page(secondUrl);
        sitemap.put(validPage2, null);

        // Pages have been visited already
        assertTrue(crawler.alreadyVisited(sitemap, validPage1.getUrl()));
        assertTrue(crawler.alreadyVisited(sitemap, validPage2.getUrl()));
    }

    @Test
    public void isSameDomainTest_VisitSubdomains() {
        UserInput input = new UserInput();
        input.setVisitSubDomains(true);
        WebCrawler crawler = new WebCrawler(input);
        URL home = null;
        try {
            home = new URL("http://wiprodigital.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertTrue(crawler.isSameDomain(home, "http://wiprodigital.com/who-we-are/"));
        assertTrue(crawler.isSameDomain(home, "http://wiprodigital.com/"));
        assertTrue(crawler.isSameDomain(home, "http://wiprodigital.com/what-we-do/"));
        assertFalse(crawler.isSameDomain(home, "http://www.google.com"));
    }


    @Test
    public void isSameDomainTest_DontVisitSubdomains() {
        UserInput input = new UserInput();
        input.setVisitSubDomains(false);
        WebCrawler crawler = new WebCrawler(input);
        URL home = null;
        try {
            home = new URL("http://wiprodigital.com");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        assertTrue(crawler.isSameDomain(home, "http://wiprodigital.com/who-we-are/"));
        assertTrue(crawler.isSameDomain(home, "http://wiprodigital.com/"));
        assertFalse(crawler.isSameDomain(home, "http://services.wiprodigital.com/"));
        assertFalse(crawler.isSameDomain(home, "http://test.wiprodigital.com"));
        assertFalse(crawler.isSameDomain(home, "http://www.google.com"));
    }

    @Test
    public void getDocumentTest() {
        WebCrawler crawler = new WebCrawler(new UserInput());
        String urlToVisit = "https://www.google.com";
        Page currentPage = null;
        Map<Page, String> sitemap = null;
        Document doc = crawler.getDocument(urlToVisit, currentPage, sitemap);
        assertNotNull(doc);
        // Correct as of 28/01/2018
        assertEquals("Google", doc.title());
    }

    @Test
    public void getDocumentTest_ErrorUnknown() {
        WebCrawler crawler = new WebCrawler(new UserInput());
        String urlToVisit = "https://www.thisdomaindonotexistandshouldcontinuenotexisting.com";
        Page currentPage = new Page(urlToVisit);
        Map<Page, String> sitemap = Maps.newHashMap();
        Document doc = crawler.getDocument(urlToVisit, currentPage, sitemap);
        assertNull(doc);
        assertEquals("java.net.UnknownHostException: www.thisdomaindonotexistandshouldcontinuenotexisting.com",
                currentPage.getError());
        assertEquals(1, sitemap.size());
    }

    @Test
    public void getDocumentTest_Error404() {
        WebCrawler crawler = new WebCrawler(new UserInput());
        String urlToVisit = "http://wiprodigital.com/thispagedonotexistandshouldcontinuenotexisting.html";
        Page currentPage = new Page(urlToVisit);
        Map<Page, String> sitemap = Maps.newHashMap();
        Document doc = crawler.getDocument(urlToVisit, currentPage, sitemap);
        assertNull(doc);
        assertEquals("org.jsoup.HttpStatusException: HTTP error fetching URL. Status=404, " +
                "URL=http://wiprodigital.com/thispagedonotexistandshouldcontinuenotexisting.html", currentPage.getError());
        assertEquals(1, sitemap.size());
    }
}