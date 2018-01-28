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
        Page validPage1 = new Page("http://www.wiprodigital.com");
        Page validPage2 = new Page("http://wiprodigital.com/who-we-are/");
        Page invalidPage1 = new Page("http://www.wiprodigital.com");

        WebCrawler crawler = new WebCrawler();
        assertFalse(crawler.shouldSkipPage(sitemap, validPage1.getUrl()));
        sitemap.put(validPage1, null);

        assertFalse(crawler.shouldSkipPage(sitemap, validPage2.getUrl()));
        sitemap.put(validPage2, null);

        assertTrue(crawler.shouldSkipPage(sitemap, validPage1.getUrl()));
        sitemap.put(invalidPage1, null);
    }

}