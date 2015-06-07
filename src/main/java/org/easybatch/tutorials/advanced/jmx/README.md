# Job monitoring Tutorial

## Description

This tutorial is an application that reads tweets from a flat file and prints them out to the standard output.
The `TweetSlowProcessor` simulates a long running processor to allow you to monitor the application using a JMX client.

This tutorial contains 2 examples:

- `SingleEngineJmxTutorial`: shows how to monitor a single engine
- `ParallelEngineJmxTutorial`: shows how to monitor two worker engines running in parallel 

## Pre-requisite

* JDK 1.6+
* Maven
* Any JMX compliant client (such as VisualVM)
* Git (optional)
* Your favorite IDE (optional)

## Get source code

### Using git

`git clone https://github.com/EasyBatch/easybatch-tutorials.git`

### Downloading a zip file

Download the [zip file](https://github.com/EasyBatch/easybatch-tutorials/archive/master.zip) containing the source code and extract it.

## Run the tutorial

### From the command line

Open a terminal in the directory where you have extracted the source code of the project, then proceed as follows:

```
$>cd easybatch-tutorials
$>mvn install
$> # Launch the single engine jmx tutorial
$>mvn exec:java -PrunSingleEngineJmxTutorial
```

Using your JMX client, navigate to the `org.easybatch.core.jmx:name=engine` MBean
 and you will be able to monitor the execution progress of the application in real time.
 
```
$> # Launch the parallel engine jmx tutorial
$>mvn exec:java -PrunParallelEngineJmxTutorial
```
When you want to monitor multiple engines in parallel, you can give each engine a name. This name will be the name
of the JMX MBean that monitors the engine.
In this tutorial, we are running two engines in parallel named `worker-engine1` and `worker-engine2`.
Using your JMX client, navigate to the `org.easybatch.core.jmx` type.
 You can see two MBeans named `worker-engine1` and `worker-engine2` registered there. You can monitor the execution progress of each engine
 in real time.

### From Your IDE

* Import the `easybatch-tutorials` project in your IDE
* Resolve maven dependencies
* Navigate to the `org.easybatch.tutorials.advanced.jmx` package
* Run the `org.easybatch.tutorials.advanced.jmx.SingleEngineJmxTutorial` class without any argument

Using your JMX client, navigate to the `org.easybatch.core.jmx:name=engine` MBean
 and you will be able to monitor the execution progress of the application in real time.
 
* Run the `org.easybatch.tutorials.advanced.jmx.ParallelEngineJmxTutorial` class without any argument

When you want to monitor multiple engines in parallel, you can give each engine a name. This name will be the name
of the JMX MBean that monitors the engine.
In this tutorial, we are running two engines in parallel named `worker-engine1` and `worker-engine2`.
Using your JMX client, navigate to the `org.easybatch.core.jmx` type.
 You can see two MBeans named `worker-engine1` and `worker-engine2` registered there. You can monitor the execution progress of each engine
 in real time.
