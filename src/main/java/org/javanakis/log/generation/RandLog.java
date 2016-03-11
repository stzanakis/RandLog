package org.javanakis.log.generation;

import org.apache.commons.cli.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since Feb 18, 2016
 * <p/>
 * Main class, entry point to start all the LogAggregator Threads.
 */
public class RandLog {
    final static Logger logger = LogManager.getLogger(Thread.currentThread().getName());

    public static void main(String[] args) {
        boolean enableExceptions;
        int numberOfThreads, lowSpeedCap, highSpeedCap;
        String method, filePath;

        //Parse arguments
        Options options = new Options();
        options.addOption("m", true, "method to run the app"); //url, log
        options.addOption("e", true, "include exceptions");
        options.addOption("t", true, "thread number");
        options.addOption("l", true, "lower bound in milliseconds");
        options.addOption("h", true, "high bound in milliseconds");
        options.addOption("f", true, "file with url links");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);

            String m = cmd.getOptionValue("m");
            method = (m == null || (!m.equals("log") && !m.equals("url"))) ? "log" : cmd.getOptionValue("m");
            enableExceptions = (cmd.getOptionValue("e") == null) ? true : Boolean.parseBoolean(cmd.getOptionValue("e"));
            numberOfThreads = (cmd.getOptionValue("t") == null) ? 5 : Integer.parseInt(cmd.getOptionValue("t"));
            lowSpeedCap = (cmd.getOptionValue("l") == null) ? 1000 : Integer.parseInt(cmd.getOptionValue("l"));
            highSpeedCap = (cmd.getOptionValue("h") == null) ? 2000 : Integer.parseInt(cmd.getOptionValue("h"));
            filePath = (cmd.getOptionValue("f") == null) ? "url-list.txt" : cmd.getOptionValue("f");
        } catch (ParseException e) {
            logger.fatal("Not able to parse arguments");
            return;
        }
        logger.info("App started with the following arguments: -m " + method + " -e " + enableExceptions + " -t " + numberOfThreads +
                " -l " + lowSpeedCap + " -h " + highSpeedCap);

        logger.info("RandLog Started");
        new LogAggregatorExecutor(method, enableExceptions, numberOfThreads, lowSpeedCap, highSpeedCap, filePath).executeAll();
    }
}
