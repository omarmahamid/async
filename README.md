# async

Async Profiler is a sampling CPU and heap profiler for Java that uses AsyncGetCallTrace and perf_events.
By this repo, it can be embedded in a Spring application within a scheduled service, capturing profiling data at regular intervals (each tick of time) and generating an HTML report that can be viewed in a browser


# Dependency

Maven
^^^^^

.. code-block:: xml

	<dependency>
		<groupId>io.github.omarmahamid</groupId>
		<artifactId>async</artifactId>
		<version>0.0.1</version>
  	</dependency>


# Configuration

`async-profiler-event` - Event of async profiler (CPU, ALLOC, WALL).
`async-profiler-duration` - Duration of interval profiling in seconds.

