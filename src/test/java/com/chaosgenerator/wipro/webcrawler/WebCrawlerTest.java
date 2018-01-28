package com.chaosgenerator.wipro.webcrawler;

import com.chaosgenerator.wipro.webcrawler.pojo.Page;
import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class WebCrawlerTest {

    @Test
    public void shouldSkipPageTest() {
        Map<Page, String> sitemap = Maps.newHashMap();

        WebCrawler crawler = new WebCrawler();

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

}