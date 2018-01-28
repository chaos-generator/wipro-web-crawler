package com.chaosgenerator.wipro.webcrawler;

import org.junit.After;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class AppTest {
    /**
     * 1. Get user input
     * 2. Start crawler
     * 3. Set user input as top level page
     * 4. Maybe read robots.txt
     * 5. Mayve apply robots.txt rules
     * 6. Skip if page has been visited already
     * 7. Download html file
     * 8. Parse html file
     * 9. Extract title from file
     * 10. Extract links from file
     * 11. Maybe consider spawning threads to process multiple pages at the same time
     * 12. Create page object with title, url and list of links.
     * 13. Add page to a map
     * 14. Add links a to toVisit list, filtering out links from other websites.
     * 15. save to file (consider that links written before, as skipped to avoid circular references)
     * 16. maybe format the file
     */

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
        String[] args = {"website"};
        App.main(args);
        assertTrue(outContent.toString().contains("Crawled website in "));
    }


}
