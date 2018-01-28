package com.chaosgenerator.wipro.webcrawler;

import com.chaosgenerator.wipro.input.InputParser;
import com.chaosgenerator.wipro.input.UserInput;
import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.junit.Assert.*;

public class InputParserTest {

    @Test
    public void testUserInput(){
        UserInput expected = new UserInput();
        URL url;
        try {
            url = new URL("https://www.wiprodigital.com");
            expected.setDomainToCrawl(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        expected.setSitemapPath("./mysite.map");
        String[] args = {"https://www.wiprodigital.com", "./mysite.map"};
        UserInput actual = InputParser.parse(args);
        assertEquals(expected, actual);
    }

}