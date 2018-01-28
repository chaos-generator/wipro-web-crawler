package com.chaosgenerator.wipro.webcrawler;

import com.google.common.base.Stopwatch;

public class App {

    public static void main(String args[]){
        Stopwatch stopwatch = new Stopwatch().start();

        stopwatch.stop();
        System.out.println(String.format("Crawled %s in %d  milliseconds", args[0], stopwatch.elapsedMillis()));
    }
}
