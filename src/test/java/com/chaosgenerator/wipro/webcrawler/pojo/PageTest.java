package com.chaosgenerator.wipro.webcrawler.pojo;

import org.junit.Test;

import static org.junit.Assert.*;

public class PageTest {

    @Test
    public void Page_EqualsTest() {
        Page page1 = new Page("http://www.wiprodigital.com");
        Page page2 = new Page("http://www.wiprodigital.com");
        Page page3 = new Page("http://www.google.com");
        assertEquals(page1, page2);
        assertNotEquals(page1, page3);
        assertNotEquals(page2, page3);
    }

    @Test
    public void Page_HashTest() {
        Page page1 = new Page("http://www.wiprodigital.com");
        Page page2 = new Page("http://www.wiprodigital.com");
        Page page3 = new Page("http://www.google.com");
        assertEquals(page1.hashCode(), page2.hashCode());
        assertNotEquals(page1.hashCode(), page3.hashCode());
        assertNotEquals(page2.hashCode(), page3.hashCode());
    }
}