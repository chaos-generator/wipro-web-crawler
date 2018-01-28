package com.chaosgenerator.wipro.webcrawler;

import com.chaosgenerator.wipro.input.UserInput;

import java.net.URL;

public class WebCrawler {
    public void crawl(UserInput input) {
        URL domain = input.getDomainToCrawl();


        /**
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
    }
}
