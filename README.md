# wipro-web-crawler
A web crawler for Wipro interview process.

## Exercise Description
Here are the instructions for the Buildit Platform Engineer exercise:

###What we are looking for

This exercise is to examine your technical knowledge, reasoning and engineering
principals. There are no tricks or hidden agendas.

We are looking for a demonstration of your experience and skill using current 
software development technologies and methods. Please make sure your code is 
clear and demonstrates good practices.

Your solution will form the basis for discussion in subsequent interviews.

### What you need to do
Please write a simple web crawler in a language of your choice in a couple of
hours – please don’t spend much more than that.

The crawler should be limited to one domain. Given a starting URL – say 
http://wiprodigital.com - it should visit all pages within the domain, but not
follow the links to external sites such as Google or Twitter.

The output should be a simple structured site map (this does not need to be a
traditional XML sitemap - just some sort of output to reflect what your crawler
has discovered), showing links to other pages under the same domain, links to 
external URLs and links to static content such as images for each respective 
page.

Please provide a README.md file that explains how to run / build your solution.
Also, detail anything further that you would like to achieve with more time.

Once done, please make your solution available on Github and forward the link.
Where possible please include your commit history to give visibility of your 
thinking and progress.

What you need to share with us:
- Working crawler as per requirements above
- A README.md explaining
- How to build and run your solution
- Reasoning and describe any trade offs
- Explanation of what could be done with more time
- Project builds / runs / tests as per instruction

# My solution

## Process planning
1. Get user input
2. Start crawler
3. Set user input as top level page
4. Maybe read robots.txt
5. Maybe apply robots.txt rules
6. Skip if page has been visited already
7. Download html file
8. Parse html file
9. Extract title from file
10. Extract links from file
11. Maybe consider spawning threads to process multiple pages at the same time
12. Create page object with title, url and list of links.
13. Add page to a map
14. Add links a to toVisit list, filtering out links from other websites.
15. save to file (consider that links written before, as skipped to avoid circular references)
16. maybe format the file

## Initial considerations
I'll use java, as I was initially approached by the recruiter as a java 
developer, so it is my understanding that this would allow the review of my 
test to be more meaningful for the role.

Otherwise, I'd possibly choose Python if this is never meant to be concurrent,
as Python is very simple and very quick to code. If this is ever meant to be
concurrent, I'd consider Go, as the concurrency model of Go is very robust and
it was a language designed for developer productivity.

I chose Java 8 to write the code instead of Java 9, simply because so far, I 
only read the new features list, but haven't had a chance to work with it yet.

As java is my choice of language, I'll use JUnit for unit tests and Mockito to
create mocks. Jsoup was chosen as the lib to parse the HTML.

I also added Google's guava, as the helper methods included in that library are
life-saving.

### How to build
dependencies: 
- [gradle](https://gradle.org/)
- [java 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)

With the dependencies installed, run the command below to clean build and 
execute tests:

```
gradle clean build test
```

### How to run

To run the web crawler, use the command below:

```
java -jar <jarfile> <website> <sitemap>
```

Where:
- `jarfile` is the full path to the jar file
- `website` is the domain name to crawl
- `sitemap` is the file to save

### Improvements

- Make the crawler multi-threaded
- Allows for distribution
- Generate a pretty site map  
- Add more functional tests
- Add wiremock to for an end to end test, with a mocked 