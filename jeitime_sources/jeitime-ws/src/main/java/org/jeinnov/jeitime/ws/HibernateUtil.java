package org.jeinnov.jeitime.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class HibernateUtil {

	public static SessionFactory sessionFactory;
	private static Logger logger = Logger.getLogger(HibernateUtil.class);
	
	public static AnnotationConfiguration configuration;
	static Properties properties;
	static {
		try {
			URL configUrl = null;
			if (System.getProperty("hibernate.default.config") != null) {
				try {
					configUrl = new URL(System
							.getProperty("hibernate.default.config"));
					// ok, this is a valid url
				} catch (final MalformedURLException e) {
					logger.error(e.getMessage(), e);
				}
			}
			// Création de la SessionFactory à partir de hibernate.cfg.xml
			if (configUrl == null) {
				configuration = new AnnotationConfiguration();
				configuration.configure();
				sessionFactory = configuration
						.buildSessionFactory();
			} else {
				sessionFactory = new AnnotationConfiguration().configure(
						configUrl).buildSessionFactory();
			}	

		} catch (HibernateException e) {
			logger.warn(e);
		}
	}


	@SuppressWarnings("rawtypes")
	public static final ThreadLocal session = new ThreadLocal();

	public static SessionFactory getSessionFactory() throws HibernateException {
		return sessionFactory;
	}
	
	 public static AnnotationConfiguration getConfiguration() {
			if (configuration == null) {
				throw new IllegalStateException("Configuration not initialized yet");
			}
			return configuration;
		}
	 
	 public static Properties getProperties() {
			return properties;
		}
}
