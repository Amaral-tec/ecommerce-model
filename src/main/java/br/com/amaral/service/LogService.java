package br.com.amaral.service;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LogService {

    private static final Logger LOGGER = Logger.getLogger(LogService.class.getName());

    public <T> void logEntity(long timestamp, String entityType, T entity) {
        LOGGER.log(Level.INFO, "{0} logged successfully. Timestamp: {1}, Entity: {2}",
                new Object[]{entityType, timestamp, entity});
    }
    
    public <T> void logEntityList(long timestamp, List<T> entityList) {
        LOGGER.log(Level.INFO, "{0} list logged successfully. Timestamp: {1}, Entity List: {2}",
                new Object[]{timestamp, entityList});
    }

}