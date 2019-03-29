package com.mits.creditcard.logger;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.log4j.PropertyConfigurator;

//This class is used to configuring the log4j
@WebListener("application context listener")
public class ContextListener implements ServletContextListener {

	//is used to get the location from the context param and  configuring the loggers
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// initialize log4j here
		ServletContext context = event.getServletContext();
		String log4jConfigFile = context.getInitParameter("log4j-config-location");
		String fullPath = context.getRealPath("") + File.separator + log4jConfigFile;
		
		PropertyConfigurator.configure(fullPath);
		
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// do nothing
	}	
}