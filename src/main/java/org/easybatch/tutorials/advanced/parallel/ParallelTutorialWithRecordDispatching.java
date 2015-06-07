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
import org.easybatch.core.api.Record;
import org.easybatch.core.dispatcher.PoisonRecordBroadcaster;
import org.easybatch.core.dispatcher.RoundRobinRecordDispatcher;
import org.easybatch.core.filter.PoisonRecordFilter;
import org.easybatch.core.reader.QueueRecordReader;
import org.easybatch.flatfile.FlatFileRecordReader;
import org.easybatch.tutorials.basic.helloworld.TweetProcessor;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static java.util.Arrays.asList;
import static org.easybatch.core.impl.EngineBuilder.aNewEngine;

/**
* Main class to run the record dispatching tutorial.
 *
* @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
*/
public class ParallelTutorialWithRecordDispatching {

    private static final int THREAD_POOL_SIZE = 3;

    public static void main(String[] args) throws Exception {

        // Input file tweets.csv
        File tweets = new File(ParallelTutorialWithRecordDispatching.class
                            .getResource("/org/easybatch/tutorials/advanced/parallel/tweets.csv").toURI());

        // Create queues
        BlockingQueue<Record> queue1 = new LinkedBlockingQueue<Record>();
        BlockingQueue<Record> queue2 = new LinkedBlockingQueue<Record>();

        // Create a round robin record dispatcher to distribute records to worker engines
        RoundRobinRecordDispatcher roundRobinRecordDispatcher = new RoundRobinRecordDispatcher(asList(queue1, queue2));

        // Build a master engine to read records from the data source and dispatch them to worker engines
        Engine masterEngine = aNewEngine()
                .named("master-engine")
                .reader(new FlatFileRecordReader(tweets))
                .processor(roundRobinRecordDispatcher)
                .batchProcessEventListener(new PoisonRecordBroadcaster(roundRobinRecordDispatcher))
                .build();

        // Build worker engines
        Engine workerEngine1 = buildWorkerEngine(queue1, "worker-engine1");
        Engine workerEngine2 = buildWorkerEngine(queue2, "worker-engine2");

        // Create a thread pool to call master and worker engines in parallel
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        // Submit workers to executor service
        executorService.invokeAll(asList(masterEngine, workerEngine1, workerEngine2));

        // Shutdown executor service
        executorService.shutdown();

    }

    public static Engine buildWorkerEngine(BlockingQueue<Record> queue, String engineName) {
        return aNewEngine()
                .named(engineName)
                .reader(new QueueRecordReader(queue))
                .filter(new PoisonRecordFilter())
                .processor(new TweetProcessor())
                .build();
    }

}