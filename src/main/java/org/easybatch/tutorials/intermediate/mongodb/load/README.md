# MongoDB feeding Tutorial

## Description

This tutorial is an application that reads tweets from flat file and import them in a MongoDB instance.

## Pre-requisite

* JDK 1.7+
* Maven
* MongoDB up and running on default port (27017)
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
$>mvn exec:java -PrunMongoDBImportDataTutorial
```

### From Your IDE

* Import the `easybatch-tutorials` project in your IDE
* Resolve maven dependencies
* Navigate to the `org.easybatch.tutorials.intermediate.mongodb.load` package
* Run the `org.easybatch.tutorials.intermediate.mongodb.load.Launcher` class without any argument
