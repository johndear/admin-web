package com.it.j2ee.core.logger.slf4j;

import java.io.File;

import com.it.j2ee.core.logger.Level;
import com.it.j2ee.core.logger.Logger;
import com.it.j2ee.core.logger.LoggerAdapter;

public class Slf4jLoggerAdapter implements LoggerAdapter {
    
	public Logger getLogger(String key) {
		return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(key));
	}

    public Logger getLogger(Class<?> key) {
        return new Slf4jLogger(org.slf4j.LoggerFactory.getLogger(key));
    }

    private Level level;
    
    private File file;

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

}
