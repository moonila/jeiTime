package org.jeinnov.jeitime.utils;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.ow2.opensuit.core.session.OpenSuitSession;
import org.ow2.opensuit.core.util.BeanUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserConfig {
	private final Logger logger = Logger.getLogger(this.getClass());
	private CollaborateurP collab;

	public final UserConfig getCurrent(HttpServletRequest request) {
		try {
			return (UserConfig) BeanUtils.getOpenSuitBean(OpenSuitSession
					.getCurrentRequest(), "userConfig");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return null;
		}

	}

	@SuppressWarnings("static-access")
	public Locale getLocale() {
		HttpServletRequest request = OpenSuitSession.getCurrentRequest();
		Cookie[] cookies = request.getCookies();
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("lang")) {
				return new Locale(cookies[i].getValue());
			}
		}

		// --- otherwise return default locale
//		return OpenSuitSession.getSession(request).getApplication()
//				.getAllLanguages()[0].getLocale();
		return OpenSuitSession.getSession(request).getCurrentApplication().getAllLanguages()[0].getLocale();
	}

	public void setLocale(Locale locale) {
		HttpServletRequest request = OpenSuitSession.getCurrentRequest();
		HttpServletResponse response = OpenSuitSession.getCurrentResponse();
		Cookie langCookie = new Cookie("lang", locale.getLanguage());
		langCookie.setPath(request.getContextPath());
		response.addCookie(langCookie);
	}

	public String getIdentity() {
		if (collab != null) {
			return collab.getNameAppli();
		}
		HttpServletRequest req = OpenSuitSession.getCurrentRequest();
		return req.getUserPrincipal().getName();
	}

	public int getPaginationLength() {
		return 20;

	}

	public void disconnect(HttpServletRequest request) {
		request.getSession().invalidate();
	}

	public CollaborateurP getCollab() {
		return collab;
	}

	public void setCollab(CollaborateurP collab) {
		this.collab = collab;
	}

}
