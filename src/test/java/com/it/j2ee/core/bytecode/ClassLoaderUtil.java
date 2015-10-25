package com.it.j2ee.core.bytecode;

public class ClassLoaderUtil {
	
	 public ClassLoader getClassLoader(ClassLoader classLoader, Class clazz) {
	        ClassLoader t = classLoader;
	        if (t == null) {
	            t = clazz.getSuperclass().getClassLoader();
	        }
	        if (t == null) {
	            t = getClass().getClassLoader();
	        }
	        if (t == null) {
	            t = Thread.currentThread().getContextClassLoader();
	        }
	        if (t == null) {
	            throw new IllegalStateException("Cannot determine classloader");
	        }
	        return t;
	    }

}
