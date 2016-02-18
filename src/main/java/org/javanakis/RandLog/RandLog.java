package org.javanakis.RandLog;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since Feb 18, 2016
 */
public class RandLog {
  final static Logger logger = LogManager.getLogger(Thread.currentThread().getName());

  public static void main(String[] args) {
    int numberOfThreads = 0, lowSpeedCap = 0, highSpeedCap = 0;

    if (args.length != 3) {
      logger.fatal("Wrong number of arguments.\n"
          + "Must be <numberOfThreads> <lowCap of millisecs> <highCap of millisecs> "
          + "Setting defaults to 10 1000 2000");
      numberOfThreads = 10;
      lowSpeedCap = 1000;
      highSpeedCap = 2000;
    } else if (args.length == 3) {
      numberOfThreads = Integer.parseInt(args[0]);
      lowSpeedCap = Integer.parseInt(args[1]);
      highSpeedCap = Integer.parseInt(args[2]);
    }

    logger.info("RandLog Started");
    new LogAggregatorExecutor(numberOfThreads, lowSpeedCap, highSpeedCap).executeAll();
  }
}
