# Job monitoring Tutorial

## Description

This tutorial is an application that reads tweets from a flat file and prints them out to the standard output.
The `TweetSlowProcessor` simulates a long running processor to allow you to monitor the application using a JMX client.

## Pre-requisite

* JDK 1.6+
* Maven
* Any JMX compliant client (such as VisualVM)
* Git (optional)
* Your favorite IDE (optional)

## Get source code

### Using git

`https://github.com/EasyBatch/easybatch-tutorials.git`

### Downloading a zip file

Download the [zip file](https://github.com/EasyBatch/easybatch-tutorials/archive/master.zip) containing the source code and extract it.

## Run the tutorial

### From the command line

Open a terminal in the directory where you have extracted the source code of the project, then proceed as follows:

```
$>cd easybatch-tutorials
$>mvn install
$>mvn exec:java -PrunJmxTutorial
```

Using your JMX client, navigate to the `org.easybatch.core.jmx:type=EasyBatchMonitorMBean` MBean
 and you will be able to monitor the execution progress of the application in real time.

### From Your IDE

* Import the `easybatch-tutorials` project in your IDE
* Resolve maven dependencies
* Navigate to the `org.easybatch.tutorials.advanced.jmx` package
* Run the `org.easybatch.tutorials.advanced.jmx.Launcher` class without any argument

Using your JMX client, navigate to the `org.easybatch.core.jmx:type=EasyBatchMonitorMBean` MBean
 and you will be able to monitor the execution progress of the application in real time.
