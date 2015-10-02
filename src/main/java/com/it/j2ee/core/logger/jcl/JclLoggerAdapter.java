package com.it.j2ee.core.logger.jcl;

import java.io.File;

import org.apache.commons.logging.LogFactory;

import com.it.j2ee.core.logger.Level;
import com.it.j2ee.core.logger.Logger;
import com.it.j2ee.core.logger.LoggerAdapter;

public class JclLoggerAdapter implements LoggerAdapter {

	public Logger getLogger(String key) {
		return new JclLogger(LogFactory.getLog(key));
	}

    public Logger getLogger(Class<?> key) {
        return new JclLogger(LogFactory.getLog(key));
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
