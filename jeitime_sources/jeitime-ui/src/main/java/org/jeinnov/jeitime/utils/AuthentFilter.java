package org.jeinnov.jeitime.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurManager;
import org.ow2.opensuit.core.util.BeanUtils;
import org.ow2.opensuit.xml.base.enums.Scope;


public class AuthentFilter implements javax.servlet.Filter {
	private final Logger logger = Logger.getLogger(this.getClass());

	public void init(FilterConfig config) throws ServletException {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpServletRequest req = (HttpServletRequest) request;
			UserConfig user = BeanUtils.getBean(req, UserConfig.class,
					"userConfig", Scope.Session);
			if (user == null) {
				// --- we have to load user
				user = new UserConfig();

				try {
					user.setCollab(CollaborateurManager.getInstance()
							.getByLogin(req.getUserPrincipal().getName()));
				} catch (CollaborateurException e) {
					logger.error(e.getMessage(), e);
				}

				BeanUtils.setBean(req, user, "userConfig", Scope.Session);
			}
		}
		chain.doFilter(request, response);
	}

}
