package desapp.grupo.e.service.log;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

public class Log {

    private static Logger logger;

    private Log() {
        // No quiero que se inicialize el Log
    }

    static {
        initLog();
        Properties props = new Properties();
        try {
            InputStream inStream = Log.class.getClassLoader().getResourceAsStream("log4j.properties");
            props.load(inStream);
        } catch (Exception e){
            Log.exception(e);
        }
        PropertyConfigurator.configure(props);
    }

    public static void info(String msgToLog) {
        logger.info(msgToLog);
    }

    public static void debug(String msgToLog) {
        logger.debug(msgToLog);
    }

    public static void warning(String msgToLog) {
        logger.warn(msgToLog);
    }

    public static void error(String msgToLog) {
        logger.error(msgToLog);
    }

    public static void exception(Exception exception) {
        logger.error(exception.getMessage(), exception);
    }

    public static void exception(Throwable throwable) {
        logger.error(throwable.getMessage(), throwable);
    }

    private static void initLog() {
        initFileAppender();
        if(logger == null) {
            logger = Logger.getLogger(getClassName());
        }
    }

    private static String getClassName() {
        StackTraceElement[] stElements = Thread.currentThread().getStackTrace();
        for (int i=1; i<stElements.length; i++) {
            StackTraceElement ste = stElements[i];
            if (!Log.class.getName().equals(ste.getClassName()) && ste.getClassName().indexOf("java.lang.Thread") != 0) {
                String className = ste.getFileName().substring(0, ste.getFileName().indexOf("."));
                return className + "." + ste.getMethodName();
            }
        }
        return Log.class.getName();
    }


    private static void initFileAppender() {
        try {
            File homeLoggingDir = new File(System.getProperty("user.home")+"/log/desapp/");
            if (!homeLoggingDir.exists() ) {
                boolean isCreated = homeLoggingDir.mkdirs();
                if(isCreated) {
                    logger.debug("Creating missing logging directory: " + homeLoggingDir);
                } else {
                    logger.debug("Can't create a logging directory");
                }
            }
        } catch(Exception e) {
            Log.exception(e);
        }
    }
}
