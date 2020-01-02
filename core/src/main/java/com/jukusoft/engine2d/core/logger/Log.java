package com.jukusoft.engine2d.core.logger;

import com.jukusoft.engine2d.core.config.Config;
import com.jukusoft.engine2d.core.utils.FilePath;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class Log {

    /**
     * logging levels:
     * <p>
     * - VERBOSE
     * - DEBUG
     * - INFO
     * - WARN (exception was caught, but application can still continue)
     * - ERROR (application crashed)
     */
    public enum LEVEL {
        VERBOSE(1, 'V'),
        DEBUG(2, 'D'),
        INFO(3, 'I'),
        WARN(4, 'W'),
        ERROR(5, 'E');

        private final int value;
        private final char shortcut;

        LEVEL(final int value, final char shortcut) {
            this.value = value;
            this.shortcut = shortcut;
        }

        public final int getValue() {
            return value;
        }

        public final char getShortcut() {
            return this.shortcut;
        }
    }

    //instances
    protected static final ConcurrentLinkedQueue<String> loggingQueue = new ConcurrentLinkedQueue<>();
    protected static LogWriter logWriter = null;
    protected static Thread logWriterThread = null;

    //config
    protected static boolean printToConsole = false;
    protected static boolean enabled = false;
    protected static LEVEL level = LEVEL.WARN;

    protected static SimpleDateFormat format = null;

    protected static final String LOGGER_TAG = "Logger";

    protected static final CountDownLatch shutdownLatch = new CountDownLatch(1);

    //headless mode means the LoggerWriter thread is not started but all messages are printed to console
    protected static boolean headlessMode = false;

    public static void init() {
        Log.enabled = Config.getBool(LOGGER_TAG, "enabled");
        Log.printToConsole = Config.getBool(LOGGER_TAG, "printToConsole");
        Log.level = LEVEL.valueOf(Config.get(LOGGER_TAG, "level"));

        //get format
        format = new SimpleDateFormat(Config.get(LOGGER_TAG, "timeFormat"));

        //first check, if logging is enabled
        if (Log.enabled && (Config.getBool(LOGGER_TAG, "writeToFile") || Config.getBool(LOGGER_TAG, "printToConsole"))) {
            String filePath = FilePath.parse(Config.get(LOGGER_TAG, "file"), false);

            File file = new File(filePath);

            logWriter = new LogWriter(file, loggingQueue, shutdownLatch);
            logWriterThread = new Thread(logWriter);
            logWriterThread.setPriority(Thread.MIN_PRIORITY);

            //set thread name
            logWriterThread.setName("log-writer");
            logWriterThread.setDaemon(false);

            //start thread
            logWriterThread.start();
        }

        headlessMode = false;
    }

    public static void initJUnitLogger(LEVEL level) {
        Log.enabled = true;
        Log.printToConsole = true;
        Log.level = level;

        //get format
        format = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");

        headlessMode = true;
    }

    /**
     * Send a VERBOSE log message
     *
     * @param tag     log tag
     * @param message log message
     */
    public static final void v(String tag, String message) {
        v(tag, message, null);
    }

    /**
     * Send a VERBOSE log message with exception
     *
     * @param tag     log tag
     * @param message log message
     * @param e       throwable
     */
    public static final void v(String tag, String message, Throwable e) {
        log(LEVEL.VERBOSE, tag, message, e);
    }

    /**
     * Send a DEBUG log message
     *
     * @param tag     log tag
     * @param message log message
     */
    public static final void d(String tag, String message) {
        d(tag, message, null);
    }

    /**
     * Send a DEBUG log message with exception
     *
     * @param tag     log tag
     * @param message log message
     * @param e       throwable
     */
    public static final void d(String tag, String message, Throwable e) {
        log(LEVEL.DEBUG, tag, message, e);
    }

    /**
     * Send a INFO log message
     *
     * @param tag     log tag
     * @param message log message
     */
    public static final void i(String tag, String message) {
        i(tag, message, null);
    }

    /**
     * Send a INFO log message with exception
     *
     * @param tag     log tag
     * @param message log message
     * @param e       throwable
     */
    public static final void i(String tag, String message, Throwable e) {
        log(LEVEL.INFO, tag, message, e);
    }

    /**
     * Send a WARN log message
     *
     * @param tag     log tag
     * @param message log message
     */
    public static final void w(String tag, String message) {
        w(tag, message, null);
    }

    /**
     * Send a WARN log message with exception
     *
     * @param tag     log tag
     * @param message log message
     * @param e       throwable
     */
    public static final void w(String tag, String message, Throwable e) {
        log(LEVEL.WARN, tag, message, e);
    }

    /**
     * Send a ERROR log message
     *
     * @param tag     log tag
     * @param message log message
     */
    public static final void e(String tag, String message) {
        e(tag, message, null);
    }

    /**
     * Send a ERROR log message with exception
     *
     * @param tag     log tag
     * @param message log message
     * @param e       throwable
     */
    public static final void e(String tag, String message, Throwable e) {
        log(LEVEL.ERROR, tag, message, e);
    }

    protected static final void log(LEVEL level, String tag, String message, Throwable e) {
        if (!enabled) {
            //logging isn't enabled
            return;
        }

        //check for level
        if (level.getValue() < Log.level.getValue()) {
            //we dont need to log this level
            return;
        }

        StringBuilder sb = new StringBuilder(tag);

        while (sb.length() < 20) {
            sb.append(" ");
        }

        String timestampStr = format.format(new Date(System.currentTimeMillis()));

        if (headlessMode) {
            System.out.println("[" + timestampStr + "] " + level.getShortcut() + "/" + sb.toString() + ": " + message);

            if (e != null) {
                //print exception in extra line
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                System.err.println(sw.toString());
            }
        } else {
            loggingQueue.add("[" + timestampStr + "] " + level.getShortcut() + "/" + sb.toString() + ": " + message);

            if (e != null) {
                //print exception in extra line
                StringWriter sw = new StringWriter();
                e.printStackTrace(new PrintWriter(sw));
                loggingQueue.add(sw.toString());
            }
        }
    }

    public static void shutdown() {
        if (headlessMode) {
            //we don't have to do anything here in headless mode, because there is no extra thread
            return;
        }

        logWriterThread.interrupt();

        try {
            if (!shutdownLatch.await(1000l, TimeUnit.MILLISECONDS)) {
                System.err.println("[Log] Coulnd't wait for log shutdown, the latch waiting time elapsed before the count reached zero.");
                throw new IllegalStateException("Coulnd't wait for log shutdown, the latch waiting time elapsed before the count reached zero.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
