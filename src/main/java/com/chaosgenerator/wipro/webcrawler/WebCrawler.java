package com.chaosgenerator.wipro.webcrawler;

import com.chaosgenerator.wipro.input.UserInput;
import com.chaosgenerator.wipro.webcrawler.pojo.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.net.URL;
import java.util.List;
import java.util.Map;

public class WebCrawler {

    /**
     * Method to crawl a website and retrieve all it's links and images.
     * @param input
     */
    public void crawl(UserInput input) {
        URL domain = input.getDomainToCrawl();
        Page home = new Page(domain.toString());

        // Hashmaps have constant time search.
        // I'm using this map instead of a list because of it.
        Map<Page, String> sitemap = Maps.newHashMap();

        // Create list of pages to visit and add home page
        List<String> toVisit = Lists.newArrayList();
        toVisit.add(home.getUrl());

        // Because the list of URLs will keep increasing for every page visited
        // I'm creating the cursor below, for my loop. This avoids
        // ConcurrentModificationException
        int cursor = 0;

        StringBuilder builder = new StringBuilder();
        while (cursor < toVisit.size()) {
            String url = toVisit.get(cursor);
            builder.append(String.format("- %s", url));
            cursor++;
        }

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
         */    }
}
