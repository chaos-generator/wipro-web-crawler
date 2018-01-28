package com.chaosgenerator.wipro.webcrawler;

import com.chaosgenerator.wipro.webcrawler.pojo.UserInput;
import com.chaosgenerator.wipro.webcrawler.pojo.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WebCrawler implements Crawler {

    private final UserInput input;

    public WebCrawler(UserInput input) {
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
            if (!isSameDomain(home, urlToVisit)) continue;

            // Skips pages already visited.
            if (alreadyVisited(sitemap, urlToVisit)) continue;

            Page currentPage = new Page(urlToVisit);

            Document doc = getDocument(urlToVisit, currentPage, sitemap);

            if (doc == null) continue;

            setPageTitle(currentPage, doc);

            processLinks(home, toVisit, currentPage, doc);

            processImages(home, currentPage, doc, currentPage.getLinks());

            sitemap.put(currentPage, null);
        }

        saveToFile(sitemap);
    }

    public void saveToFile(Map<Page, String> sitemap) {
        try (FileWriter fw = new FileWriter(input.getSitemapPath());
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (Page p : sitemap.keySet()) {
                bw.write(p.toString());
                bw.newLine();
                for (String link : p.getLinks()) {
                    // To avoid stopping writing the file in case of a temporary blip
                    try {
                        bw.write("\t - " + link);
                        bw.newLine();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                bw.newLine();
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processImages(URL home, Page currentPage, Document doc, Set<String> linksSet) {
        Elements imgs = doc.select("img[src]");
        for (Element image : imgs) {
            String linkUrl = normaliseLink(home, image);
            if (linkUrl != null) {
                linksSet.add(linkUrl);
            }
        }
        currentPage.setLinks(linksSet);
    }

    private void processLinks(URL home, List<String> toVisit, Page currentPage, Document doc) {
        Elements links = doc.select("a[href]");
        HashSet<String> linksSet = Sets.newHashSet();
        for (Element link : links) {
            String linkUrl = normaliseLink(home, link);
            if (linkUrl == null) {
                continue;
            }
            linksSet.add(linkUrl);
            toVisit.add(linkUrl);
        }
        currentPage.setLinks(linksSet);
    }

    /**
     * Normalises all links to the full path URL.
     *
     * @param home the home page url
     * @param link the link element from the html
     * @return string with the normalised full path link or null if the link was an anchor.
     */
    public String normaliseLink(URL home, Element link) {
        String linkUrl = link.attr("href");
        linkUrl = linkUrl.trim();

        if (linkUrl.startsWith("//")) { // handle relative protocols
            linkUrl = home.getProtocol() + ":" + linkUrl;
        } else if (linkUrl.startsWith("/")) { // handle relative domains
            linkUrl = home.toString() + linkUrl;
        } else if (linkUrl.startsWith("#")) { // ignore anchors
            linkUrl = null;
        }
        return linkUrl;
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
            if (input.isVisitSubDomains()) {
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
