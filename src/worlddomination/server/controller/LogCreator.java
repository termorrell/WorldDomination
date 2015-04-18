package worlddomination.server.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by ${mm280} on 11/02/15.
 */
public class LogCreator {

    static Logger log = LogManager.getLogger(LogCreator.class.getName());

    public static void main(String[] args) {
        log.info("lo");
    }
}
