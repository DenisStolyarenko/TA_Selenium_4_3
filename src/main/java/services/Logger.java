package services;


import org.slf4j.LoggerFactory;

public class Logger {
    public static org.slf4j.Logger logger = LoggerFactory.getLogger(Logger.class);//todo Почему не log4j?
    public static void error(String message) {//todo Чем обусловлено создание всех данных методов?
        logger.error(message);
    }

    public static void error(String message, Throwable throwable) {
        logger.error(message, throwable);
    }

    public static void info(String message) {
        logger.info(message);
    }

    public static void debug(String message) {
        logger.debug(message);
    }
}
