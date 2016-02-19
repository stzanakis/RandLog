package org.javanakis.RandLog;

import java.io.IOException;

import org.apache.logging.log4j.Level;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since Feb 19, 2016
 */
public enum LogTypeException {
  FATAL(Level.FATAL, new Error("Fatal error")), ERROR(Level.ERROR, new IOException("IOException occured")), WARN(
      Level.WARN, new RuntimeException("RunTimeException occured"));

  public Level level;
  public Throwable throwable;

  private LogTypeException(Level level, Throwable throwable) {
    this.level = level;
    this.throwable = throwable;
  }

  public Level getLevel() {
    return level;
  }

  public Throwable getThrowable() {
    return throwable;
  }
}
