package com.chaosgenerator.wipro;

import com.chaosgenerator.wipro.webcrawler.input.InputParser;
import com.chaosgenerator.wipro.webcrawler.pojo.UserInput;
import com.chaosgenerator.wipro.webcrawler.WebCrawler;
import com.google.common.base.Stopwatch;

public class App {

    public static void main(String args[]){
        Stopwatch stopwatch = new Stopwatch().start();
        UserInput input = InputParser.parse(args);

        WebCrawler crawler = new WebCrawler(input);
        crawler.crawl();

        stopwatch.stop();
        System.out.println(String.format("Crawled %s in %d  milliseconds", input.getDomainToCrawl(), stopwatch.elapsedMillis()));
    }
}
