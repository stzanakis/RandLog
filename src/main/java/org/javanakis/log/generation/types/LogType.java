package org.javanakis.log.generation.types;

import org.apache.logging.log4j.Level;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since Feb 18, 2016
 */
public enum LogType {
  FATAL(Level.FATAL, "This is a fatal log.."), ERROR(Level.ERROR, "This is a fatal log.."), WARN(
      Level.WARN, "This is a warn log.."), INFO(Level.INFO, "This is a info log.."), DEBUG(
      Level.DEBUG, "This is a debug log..");

  public Level level;
  public String message;

  private LogType(Level level, String message) {
    this.level = level;
    this.message = message;
  }

  public Level getLevel() {
    return level;
  }

  public String getMessage() {
    return message;
  }
}
