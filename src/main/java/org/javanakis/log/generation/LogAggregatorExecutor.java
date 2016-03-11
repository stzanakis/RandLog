package org.javanakis.log.generation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since Feb 18, 2016
 */
public class LogAggregatorExecutor implements Executor {
  List<LogAggregator> logAggregatorList;
  
  public LogAggregatorExecutor(String method, boolean enableExceptions, int threadNumber, int speedLowCap, int speedUpCap, String filePath)
  {
    threadNumber = threadNumber == 0 ? 1 : threadNumber;
    this.logAggregatorList = new ArrayList<>(threadNumber);
    for (int i = 0; i < threadNumber; i++) {
      int speed = ThreadLocalRandom.current().nextInt(speedLowCap, speedUpCap)+1;
      this.logAggregatorList.add(new LogAggregator(method, enableExceptions, speed, filePath));
    }
  }
  
  public void executeAll()
  {
    for (LogAggregator logAggregator : logAggregatorList) {
      execute(logAggregator);
    }
  }
  
  public void execute(Runnable r) {
    new Thread(r).start();
  }

}
