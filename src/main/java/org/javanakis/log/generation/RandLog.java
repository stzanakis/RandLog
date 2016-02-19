package org.javanakis.log.generation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since Feb 18, 2016
 * 
 * Main class, entry point to start all the LogAggregator Threads.
 */
public class RandLog {
  final static Logger logger = LogManager.getLogger(Thread.currentThread().getName());

  public static void main(String[] args) {
    boolean enableExcepions = true;
    int numberOfThreads = 0, lowSpeedCap = 0, highSpeedCap = 0;

    if (args.length != 4) {
      logger.fatal("Wrong number of arguments.\n"
          + "Must be <boolean(enabled exceptions?)><numberOfThreads> <lowCap in millisecs> <highCap in millisecs> "
          + "Setting defaults to true 10 1000 2000");
      enableExcepions = true;
      numberOfThreads = 10;
      lowSpeedCap = 1000;
      highSpeedCap = 2000;
    } else if (args.length == 4) {
      enableExcepions = Boolean.parseBoolean(args[0]);
      numberOfThreads = Integer.parseInt(args[1]);
      lowSpeedCap = Integer.parseInt(args[2]);
      highSpeedCap = Integer.parseInt(args[3]);
    }

    logger.info("RandLog Started");
    new LogAggregatorExecutor(enableExcepions, numberOfThreads, lowSpeedCap, highSpeedCap).executeAll();
  }
}
