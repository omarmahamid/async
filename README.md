# async


Async profiler embedded in java.

artifacts

async-core

async-spring

async-annotation


Async Profiler embedded in a Spring application within a scheduled service, capturing profiling data at regular intervals (each tick of time) and generating an HTML report that can be viewed in a browser

# pre-requisite

`JDK17+`


## Supported Machines


| CPU Arch | Linux | MacOS | Windows |
|----------|-------|-------|---------|
| x86_64   | ✔️     | ✔️     | ❌       |
| arm      | ✔️     | ✔️     | ❌       |
| arm64    | ✔️     | ✔️     | ❌       |

# Dependency


	<dependency>
		<groupId>io.github.omarmahamid</groupId>
		<artifactId>async</artifactId>
		<version>0.0.2</version>
  	</dependency>





# Configuration

`async-profiler-enabled` - Enable profiler to work.

`async-profiler-event` - Event of async profiler (CPU, ALLOC, WALL). (Default is CPU)

`async-profiler-duration` - Duration of interval profiling in seconds. (Default is 1000)

`async-profiler-basedir` - The based directory to dump the html files in. (Default is base application dir)


## Generated File

async will generate a html file that can be shown in the browser.
 
![img.png](img.png)

![img_1.png](img_1.png)


## Future Plans 

1. create artifact for @Profiling on tests
2. integrate @Profiling in JUnit library
