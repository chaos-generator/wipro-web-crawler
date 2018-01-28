package com.chaosgenerator.wipro;

import com.chaosgenerator.wipro.input.InputParser;
import com.chaosgenerator.wipro.input.UserInput;
import com.chaosgenerator.wipro.webcrawler.WebCrawler;
import com.google.common.base.Stopwatch;

public class App {

    public static void main(String args[]){
        Stopwatch stopwatch = new Stopwatch().start();
        UserInput input = InputParser.parse(args);

        WebCrawler crawler = new WebCrawler();
        crawler.crawl(input);

        stopwatch.stop();
        System.out.println(String.format("Crawled %s in %d  milliseconds", input.getDomainToCrawl(), stopwatch.elapsedMillis()));
    }
}
