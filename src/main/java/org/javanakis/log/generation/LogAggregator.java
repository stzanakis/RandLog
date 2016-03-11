package org.javanakis.log.generation;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.javanakis.log.generation.types.LogType;
import org.javanakis.log.generation.types.LogTypeException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author Simon Tzanakis (Simon.Tzanakis@europeana.eu)
 * @since Feb 18, 2016
 */
public class LogAggregator implements Runnable {
    final Logger logger = LogManager.getLogger(Thread.currentThread().getName());
    static final Byte lock = new Byte("1");
    static ArrayList<String> list = null;
    String method, filePath;
    boolean enableExceptions;
    int speed; // Milliseconds of each log aggregation

    public LogAggregator(String method, boolean enableExceptions, int speed, String filePath) {
        this.method = method;
        this.enableExceptions = enableExceptions;
        this.speed = speed;
        this.filePath = filePath;
        loadUrls();
    }

    private void loadUrls() {
        synchronized (lock) {
            if (list == null) {
                list = new ArrayList<>();
                //Get file from resources folder
                ClassLoader classLoader = getClass().getClassLoader();
                URL resource = classLoader.getResource(filePath);
                File urlFile = null;
                if (resource == null) {
                    logger.info("Didn't find file in resources, trying to find in system directory now.");
                    urlFile = new File(filePath);
                    if (!urlFile.exists() || urlFile.isDirectory()) {
                        logger.fatal("File : " + urlFile.getAbsolutePath() + " doesn't exist.");
                        return;
                    }
                    logger.info("File found in system.");
                }
                try (BufferedReader reader = new BufferedReader(new FileReader(urlFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        if (line.startsWith("http"))
                        LogAggregator.list.add(line);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void run() {

        logger.info("Started logAggregator " + Thread.currentThread().getName()
                + " with speed factor: " + this.speed);

        switch (method) {
            case "url": {
                startTriggers();
                break;
            }
            case "log":
            default: {
                normalLogging();
            }
        }
    }

    private void startTriggers() {

        while (true) {
            try {
                Thread.sleep(speed);
                int idx = ThreadLocalRandom.current().nextInt(list.size());
                String httpUrl = list.get(idx);
                triggerUrlRequest(httpUrl);
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void triggerUrlRequest(String httpUrl) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse response = httpClient.execute(new HttpGet(httpUrl));
        new BasicResponseHandler().handleResponse(response);
//        URL url = new URL(httpUrl);
//        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//        connection.setRequestMethod("GET");
//        connection.connect();
//
//        int code = connection.getResponseCode();
        logger.info(httpUrl + " returned " + response.getStatusLine().getStatusCode());
    }

    private void normalLogging() {
        if (enableExceptions) {
            while (true) {
                try {
                    Thread.sleep(speed);
                    boolean exception = ThreadLocalRandom.current().nextBoolean();
                    if (exception) {
                        int idx = ThreadLocalRandom.current().nextInt(LogTypeException.values().length);
                        logger.log(LogTypeException.values()[idx].getLevel(), "Exception occurred",
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
