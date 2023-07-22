package org.jeinnov.jeitime.ui.gestionBase;

import org.jeinnov.jeitime.api.service.gestionBase.NettoyerBaseManager;

public class NettoyerBaseBean {

	public void getNettoyage() {
		NettoyerBaseManager.getInstance().getNettoyage();
	}
}
