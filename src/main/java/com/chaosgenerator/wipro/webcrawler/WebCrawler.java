package com.chaosgenerator.wipro.webcrawler;

import com.chaosgenerator.wipro.input.UserInput;
import com.chaosgenerator.wipro.webcrawler.pojo.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class WebCrawler {

    private final UserInput input;

    public WebCrawler(UserInput input){
        this.input = input;
    }

    /**
     * Method to crawl a website and retrieve all it's links and images.
     */
    public void crawl() {
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

        while (cursor < toVisit.size()) {
            String urlToVisit = toVisit.get(cursor);
            cursor++;

            //Skips pages in different domains
            if (isSameDomain(home, urlToVisit)) continue;

            // Skips pages already visited.
            if (alreadyVisited(sitemap, urlToVisit)) continue;

            Page currentPage = new Page(urlToVisit);

            Document doc = getDocument(urlToVisit, currentPage, sitemap);

            if (doc == null) continue;

            setPageTitle(currentPage, doc);

            Elements imgs = doc.select("img[src]");
            Elements links = doc.select("a[href]");

        }

        /**
         * 12. Create page object with title, url and list of links.
         * 13. Add page to a map
         * 14. Add links a to toVisit list, filtering out links from other websites.
         * 15. save to file (consider that links written before, as skipped to avoid circular references)
         * 16. maybe format the file
         */

    }

    void setPageTitle(Page currentPage, Document doc) {
        String title = doc.title();
        currentPage.setTitle(title);
    }

    public Document getDocument(String urlToVisit, Page currentPage, Map<Page, String> sitemap) {
        Document doc;
        try {
            doc = Jsoup.connect(urlToVisit).get();
        } catch (Exception e) {//This will capture 404, TLS/SSL errors, mimetype errors and other errors returned by connect() and get()
            currentPage.setError(e.toString());
            sitemap.put(currentPage, null);
            return null;
        }
        return doc;
    }

    boolean isSameDomain(URL home, String urlToVisit) {
        URL currentUrl;
        try {
            currentUrl = new URL(urlToVisit);
            if(input.isVisitSubDomains()) {
                if (currentUrl.getHost().endsWith(home.getHost())) {
                    return true;
                }
            } else {
                if (currentUrl.getHost().equals(home.getHost())) {
                    return true;
                }
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
