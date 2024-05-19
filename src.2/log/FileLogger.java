package log;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Writes log entries to a file.
 */
public class FileLogger {
    private static final String LOG_FILE_NAME = "POS.log";
    private PrintWriter logFile;

    /**
     * Creates a new instance and also creates a new log file.
     *
     * @throws IOException If unable to create the log file.
     */
    public FileLogger() throws IOException {
        logFile = new PrintWriter(new FileWriter(LOG_FILE_NAME, true), true);
    }

    /**
     * Writes a log entry describing a thrown exception.
     *
     * @param exception The exception that shall be logged.
     */
    public void log(Exception exception) {
        logFile.println(exception.getMessage());
        exception.printStackTrace(logFile);
    }
}

// 