package br.com.amaral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.amaral.formatter.CustomFormatter;
import br.com.amaral.service.LogService;

import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RestController
public class LogController <T> {
	
	static {
        configureLogger();
    }

    private static final Logger LOGGER = Logger.getLogger(LogController.class.getName());

    @Autowired
    private LogService logService;

    @Autowired
    private Class<T> entityClass;

    public LogController(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @ResponseBody
    @PostMapping(value = "**/log")
    public ResponseEntity<String> logEntity(@RequestBody T entity) {
        long timestamp = System.currentTimeMillis();

        LOGGER.log(Level.INFO, "Received request to log {0}. Timestamp: {1}, Request: {2}",
                new Object[]{entityClass.getSimpleName(), timestamp, entity});

        logService.logEntity(timestamp, entityClass.getSimpleName(), entity);

        return new ResponseEntity<>(entityClass.getSimpleName() + " logged successfully", HttpStatus.OK);
    }

    @ResponseBody
    @PostMapping(value = "/log-list")
    public ResponseEntity<String> logEntityList(@RequestBody List<T> entityList) {
        long timestamp = System.currentTimeMillis();

        LOGGER.log(Level.INFO, "Received request to log {0} list. Timestamp: {1}, Request: {2}",
                new Object[]{entityClass.getSimpleName(), timestamp, convertListToString(entityList)});

        logService.logEntityList(timestamp, entityList);

        return new ResponseEntity<>(entityClass.getSimpleName() + " list logged successfully", HttpStatus.OK);
    }

    // Método para converter a lista para uma representação de string
    private String convertListToString(List<T> entityList) {
        return entityList.stream().map(Object::toString).collect(Collectors.joining(", "));
    }
    
    private static void configureLogger() {
        Logger rootLogger = Logger.getLogger("");

        // Remove default handlers
        Handler[] handlers = rootLogger.getHandlers();
        for (Handler handler : handlers) {
            rootLogger.removeHandler(handler);
        }

        // Add a new ConsoleHandler with a custom Formatter
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(new CustomFormatter());
        rootLogger.addHandler(consoleHandler);
    }
}
