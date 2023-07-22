package org.jeinnov.jeitime.api.service.gestionBase;

import org.hibernate.Session;
import org.jeinnov.jeitime.persistence.dao.gestionBase.NettoyerBaseDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class NettoyerBaseManager {

	private static NettoyerBaseManager instance;

	public static NettoyerBaseManager getInstance() {
		if (instance == null) {
			instance = new NettoyerBaseManager();
		}
		return instance;
	}

	private NettoyerBaseDAO nettoyerBaseDAO = NettoyerBaseDAO.getInstance();

	public void getNettoyage() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

//		nettoyerBaseDAO.nettoyage(session);
		nettoyerBaseDAO.deleteOrphanAffecterTache(session);
		nettoyerBaseDAO.deleteOrphanAffecterCollaborateur(session);
		nettoyerBaseDAO.deleteBadDatedebutAffecter(session);
		nettoyerBaseDAO.deleteBadDatefinAffecter(session);
		nettoyerBaseDAO.deleteOrphanLienTachUtilCollaborateur(session);
		nettoyerBaseDAO.deleteOrphanLienTachUtilTache(session);
		nettoyerBaseDAO.deleteBadDateLienTachUtil(session);
		nettoyerBaseDAO.deleteOrphanAppartientCollegeCollaborateur(session);
		nettoyerBaseDAO.deleteOrphanAppartientCollegeCollege(session);
		nettoyerBaseDAO.deleteBadDateAppartientCollege(session);
		nettoyerBaseDAO.deleteOrphanContratClientPart(session);
		nettoyerBaseDAO.deleteOrphanContratProjet(session);
		nettoyerBaseDAO.deleteOrphanRoleCollabCollaborateur(session);
		

		session.getTransaction().commit();
		HibernateUtil.getSessionFactory().close();
	}
}
