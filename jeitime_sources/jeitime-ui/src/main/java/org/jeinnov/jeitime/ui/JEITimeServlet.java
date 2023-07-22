package org.jeinnov.jeitime.ui;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurManager;
import org.jeinnov.jeitime.api.service.collaborateur.RoleCollabManager;
import org.jeinnov.jeitime.utils.HibernateUtil;
import org.ow2.opensuit.core.impl.j2ee.OpenSuitServlet;



/**
 * Servlet implementation class JEITimeServlet
 */
public class JEITimeServlet extends OpenSuitServlet {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		super.init();
		this.initHibernate();
		this.initRole();
		try {
			this.initCollabAdmin();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public void initCollabAdmin() throws CollaborateurException {
		ServletContext servletContext = getServletContext();
		if (servletContext != null) {
			CollaborateurManager.getInstance().init();
		}
	}

	public void initRole() {
		ServletContext servletContext = getServletContext();
		if (servletContext != null) {
			RoleCollabManager.getInstance().init();
		}
	}

	public void initHibernate() {


		String urlHibernate = System.getProperty("confPath");
		
		if (urlHibernate != null) {
			String tmp = "file://" + urlHibernate;
			System.setProperty("hibernate.default.config", tmp);
		}

		HibernateUtil.getSessionFactory();
	}

}
