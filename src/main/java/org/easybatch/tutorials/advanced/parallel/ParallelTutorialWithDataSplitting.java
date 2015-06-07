/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package org.easybatch.tutorials.advanced.parallel;

import org.easybatch.core.api.Engine;
import org.easybatch.core.api.Report;
import org.easybatch.core.impl.EngineBuilder;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.tools.reporting.DefaultReportMerger;
import org.easybatch.tools.reporting.ReportMerger;
import org.easybatch.tutorials.basic.helloworld.TweetProcessor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.Arrays.asList;

/**
 * Main class to run the parallel tutorial with data source splitting.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class ParallelTutorialWithDataSplitting {

    private static final int THREAD_POOL_SIZE = 2;

    public static void main(String[] args) throws Exception {

        // Input file tweets-part1.csv
        File tweetsPart1 = new File(ParallelTutorialWithDataSplitting.class
                .getResource("/org/easybatch/tutorials/advanced/parallel/tweets-part1.csv").toURI());

        // Input file tweets-part2.csv
        File tweetsPart2 = new File(ParallelTutorialWithDataSplitting.class
                .getResource("/org/easybatch/tutorials/advanced/parallel/tweets-part2.csv").toURI());

        // Build worker engines
        Engine engine1 = buildEngine(tweetsPart1, "worker-engine1");
        Engine engine2 = buildEngine(tweetsPart2, "worker-engine2");

        //create a 2 threads pool to call worker engines in parallel
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        List<Future<Report>> partialReports = executorService.invokeAll(asList(engine1, engine2));

        //merge partial reports into a global one
        Report report1 = partialReports.get(0).get();
        Report report2 = partialReports.get(1).get();

        ReportMerger reportMerger = new DefaultReportMerger();
        Report finalReport = reportMerger.mergerReports(report1, report2);
        System.out.println(finalReport);

        executorService.shutdown();

    }

    private static Engine buildEngine(File file, String engineName) throws FileNotFoundException {
        return EngineBuilder.aNewEngine()
                .named(engineName)
                .reader(new FlatFileRecordReader(file))
                .processor(new TweetProcessor())
                .build();
    }

}