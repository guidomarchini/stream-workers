#Stream workers

Stream workers is an application spawns 10 workers (kotlin coroutines) and lets them search for the text "Lpfn".

How to run:
-
First we need to compile the code and create the jar. To do so, you can find the pom.xml to build it using maven.
>mvn install

Now you can run `streamWorkers.sh`. Possible options are:
* Display options
>./streamWorkers.sh -h
* Run the application with a given timeout in millis (defaults to 60s)
>./streamWorkers.sh -t `<timeout>`

A little about the code
-
The main classes are two:
* Boss: It's the orchestrator. Given the workers, it spawns a coroutine for each one of them.
  This generates a report with the results, total byte count and total elapsed time.
* Worker: The worker makes the business logic. It searches for the provided "stream" for the given text.

There's a provider implementation, which just generates random alphanumeric characters.

And last, we have the Application, which arranges the Boss call.

Dev notes
-
I took some liberties, and here are the points that I decided to change about the problem statement:

* `then informs the parent of the following data fields via some form of inter-process communication or shared data structure`:
 I decided to delegate the failure and timeout responsibility to the boss, wo the worker doesn't know about concurrency.

* `writes a report to stdout for each worker sorted in descending order by [elapsed]`:
For TIMEOUT and FAILURE, they are added at the beginning.
  
* `the source of random bytes must be a stream`:
I didn't make a Stream literally, but that could be fixed with another DataProvider.
  
* The timeout is not an application timeout, it's each worker's timeout:
During the tests I found that initializing the coroutines took much more time than actually running the workers.
  It would be a matter of changing the line of `withTimeout (Boss:27)` in order to make it a timeout for the whole application.
  
Another thing is that I couldn't make Mockito run with suspend functions :( it was calling the real function instead of the mock...oh well...