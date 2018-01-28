package com.chaosgenerator.wipro.webcrawler;

import com.chaosgenerator.wipro.input.UserInput;
import com.chaosgenerator.wipro.webcrawler.pojo.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class WebCrawler {

    /**
     * Method to crawl a website and retrieve all it's links and images.
     *
     * @param input an UserInput object with the domain to visit and the path
     *              to the file to save.
     */
    public void crawl(UserInput input) {
        URL home = input.getDomainToCrawl();
        Page homepage = new Page(home.toString());

        // Hashmaps have constant time search.
        // I'm using this map instead of a list because of it.
        Map<Page, String> sitemap = Maps.newHashMap();

        // Create list of pages to visit and add home page
        List<String> toVisit = Lists.newArrayList();
        toVisit.add(homepage.getUrl());

        // Because the list of URLs will keep increasing for every page visited
        // I'm creating the cursor below, for my loop. This avoids
        // ConcurrentModificationException
        int cursor = 0;

        StringBuilder builder = new StringBuilder();
        while (cursor < toVisit.size()) {
            String urlToVisit = toVisit.get(cursor);
            builder.append(String.format("- %s", urlToVisit));
            cursor++;

            //Skips pages in different domains
            if (sameDomain(home, urlToVisit)) continue;

            // Skips pages already visited.
            if (alreadyVisited(sitemap, urlToVisit)) continue;
        }

        /**
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

    boolean sameDomain(URL home, String urlToVisit) {
        URL currentUrl;
        try {
            currentUrl = new URL(urlToVisit);
            if (!currentUrl.getHost().equals(home.getHost())) {
                return true;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();//log error and continue
            return false;
        }
        return false;
    }

    public boolean alreadyVisited(Map<Page, String> sitemap, String url) {
        Page currentPage = new Page(url);
        return sitemap.containsKey(currentPage);
    }
}
