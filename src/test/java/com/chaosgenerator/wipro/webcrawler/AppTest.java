package com.chaosgenerator.wipro.webcrawler;

import static org.mockito.Mockito.*;
import static org.junit.Assert.assertTrue;

import com.chaosgenerator.wipro.App;
import com.chaosgenerator.wipro.input.UserInput;
import org.junit.After;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AppTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(System.out);
        System.setErr(System.err);
    }

    @Test
    public void testApp() {
        String[] args = {"https://www.wiprodigital.com", "mysite.map"};
        WebCrawler mock = Mockito.mock(WebCrawler.class);

        App.main(args);
        UserInput input = new UserInput();
        verify(mock, atMost(1)).crawl(input);

        assertTrue(outContent.toString().contains("Crawled https://www.wiprodigital.com in "));
    }


    @Test(expected = IllegalArgumentException.class)
    public void testApp_invalidUrl() {
        String[] args = {"something invalid", "mysite.map"};
        App.main(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApp_NullUrl() {
        String[] args = {null, "mysite.map"};
        App.main(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApp_NulFilename() {
        String[] args = {"https://www.wiprodigital", null};
        App.main(args);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testApp_EmptyFilename() {
        String[] args = {null, ""};
        App.main(args);
    }
}
