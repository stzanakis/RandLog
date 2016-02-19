package org.javanakis.RandLog;

import java.util.concurrent.ThreadLocalRandom;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since Feb 18, 2016
 */
public class LogAggregator implements Runnable {
  boolean enableExceptions;
  int speed; // Milliseconds of each log aggregation

  public LogAggregator(boolean enableExceptions, int speed) {
    this.enableExceptions = enableExceptions;
    this.speed = speed;
  }

  public void run() {
    final Logger logger = LogManager.getLogger(Thread.currentThread().getName());

    logger.info("Started logAggregator " + Thread.currentThread().getName()
        + " with speed factor: " + this.speed);


    if (enableExceptions) {
      while (true) {
        try {
          Thread.sleep(speed);
          boolean exception = ThreadLocalRandom.current().nextBoolean();
          if (exception) {
            int idx = ThreadLocalRandom.current().nextInt(LogTypeException.values().length);
            logger.log(LogTypeException.values()[idx].getLevel(), "Exception occured",
                LogTypeException.values()[idx].getThrowable());
          } else {
            int idx = ThreadLocalRandom.current().nextInt(LogType.values().length);
            logger.log(LogType.values()[idx].getLevel(), LogType.values()[idx].getMessage());
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    } else {
      while (true) {
        try {
          Thread.sleep(speed);
          int idx = ThreadLocalRandom.current().nextInt(LogType.values().length);
          logger.log(LogType.values()[idx].getLevel(), LogType.values()[idx].getMessage());
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }
  }

}
