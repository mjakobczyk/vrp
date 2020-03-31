# VRP

## Overview

This application is dedicated for solving *Vehicle Routing Problem*.

## Prerequisites

* [Java 11](https://www.java.com/pl/)
* [Maven](https://maven.apache.org/)

## Installation
```bash
$ git clone https://github.com/mjakobczyk/vrp.git
$ cd vrp
$ make build
```

## Usage
First, specify path to the file as an input argument to the application in Makefile.
There are some default files provided in `resources` directory.
```bash
// Makefile:7
resource=<path_to_the_input_file>
```
Second, run the application and collect results.
```bash
$ make run
```
