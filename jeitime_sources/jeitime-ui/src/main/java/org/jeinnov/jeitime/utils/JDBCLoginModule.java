package org.jeinnov.jeitime.utils;

import java.security.Principal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

import org.apache.log4j.Logger;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurManager;
import org.jeinnov.jeitime.api.service.collaborateur.RoleCollabManager;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.RolesCollab;



public class JDBCLoginModule implements LoginModule {

	private final Logger logger = Logger.getLogger(this.getClass());

	private CallbackHandler callbackHandler;
	private Subject subject;

	private boolean success;
	private boolean commited;

	private CollaborateurP collab;
	private List<RolesCollab> roles;
	private RolesCollab rolesC;

	public JDBCLoginModule() {
		success = false;
		callbackHandler = null;
		subject = null;
		commited = false;
	}

	public boolean abort() throws LoginException {
		if (success == false) {
			return false;
		}

		else if (commited == false) {
			reset();
		} else {
			logout();
		}

		return true;
	}

	public boolean commit() throws LoginException {
		if (success == false) {
			return false;
		}
		if (!subject.getPrincipals().contains(collab)) {
			subject.getPrincipals().add(collab);
		}

		if (!subject.getPrincipals().contains(roles)) {
			subject.getPrincipals().add(rolesC);
		}

		commited = true;
		return true;
	}

	public boolean login() throws LoginException {
		if (callbackHandler == null) {
			throw new LoginException("Erreur : pas de callback");
		}

		Callback[] callbacks = new Callback[] { new NameCallback("Nom : "),
				new PasswordCallback("Mot de passe :", false) };
		try {
			callbackHandler.handle(callbacks);

			String userName = ((NameCallback) callbacks[0]).getName();

			char ac[] = ((PasswordCallback) callbacks[1]).getPassword();

			char[] pass = new char[ac.length];

			System.arraycopy(ac, 0, pass, 0, ac.length);

			String password = new String(pass);

			((PasswordCallback) callbacks[1]).clearPassword();

			success = rdbValidate(userName, password);
			callbacks[0] = null;
			callbacks[1] = null;

			if (!success) {
				throw new LoginException("Authentification incorrecte !");
			} else {
				logger.info("Vous êtes maintenant connecté");
			}
			return success;
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
		}
		return false;
	}

	public boolean logout() throws LoginException {
		Iterator<Principal> it = subject.getPrincipals().iterator();
		while (it.hasNext()) {
			CollaborateurP c = (CollaborateurP) it.next();
			subject.getPrincipals().remove(c);
		}
		return true;

	}

	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
	}

	private boolean rdbValidate(String userName, String password)
			throws CollaborateurException {
		boolean passwordMatch = false;
		CollaborateurP collab2 = CollaborateurManager.getInstance().getByLogin(
				userName);
		String dbPassword = collab2.getPassword();
		int id = collab2.getIdColl();
		String dbLogin = collab2.getLogin();
		if (dbPassword == null) {
			logger.info("ulitsateur ou mot de passe incorrect !!");
		}
		String passwordTmp = CollaborateurManager.getInstance()
				.passwordCrypting(password);
		passwordMatch = passwordTmp.equals(dbPassword);
		collab = new CollaborateurP(dbLogin);
		List<RolesCollab> rolesTmp = RoleCollabManager.getInstance()
				.getByCollaborateurId(id);
		roles = new ArrayList<RolesCollab>();
		for (RolesCollab rC : rolesTmp) {
			String nom = rC.getRole().getRoles();
			rolesC = new RolesCollab(nom);
			roles.add(rolesC);
		}

		return passwordMatch;
	}

	private void reset() {
		success = false;
		callbackHandler = null;
		subject = null;
		commited = false;
	}
}
