package org.jeinnov.jeitime.persistence.dao.gestionBase;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilP;


public class NettoyerBaseDAO {

	private static NettoyerBaseDAO instance;

	public static NettoyerBaseDAO getInstance() {
		if (instance == null) {
			instance = new NettoyerBaseDAO();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public void nettoyage(Session session) {

		Query getByFonction = session.createSQLQuery(
				"select * from LIENTACHUTIL order by idcoll;").addEntity(
				LienTachUtilP.class);
		List<LienTachUtilP> listLien = getByFonction.list();

		for (int i = 0; i < listLien.size(); i++) {
			Date date = null;
			if (listLien.get(i) != null) {
				date = listLien.get(i).getId().getDate();
				Calendar cal = Calendar.getInstance();
				cal.setTime(date);

				int heure = cal.get(Calendar.HOUR);
				if (date != null && heure != 0) {
					session.delete(listLien.get(i));
				}
			}
		}
	}

	public void deleteOrphanAffecterTache(Session session) {

		String query = "DELETE AFFECTER FROM AFFECTER "
				+ "LEFT JOIN TACHE ON TACHE.idTache = AFFECTER.idTache "
				+ "WHERE TACHE.idTache IS NULL";

		session.createSQLQuery(query).executeUpdate();
	}

	public void deleteOrphanAffecterCollaborateur(Session session) {

		String query = "DELETE AFFECTER FROM AFFECTER "
				+ "LEFT JOIN COLLABORATEUR ON COLLABORATEUR.idColl = AFFECTER.idColl "
				+ "WHERE COLLABORATEUR.idColl IS NULL";

		session.createSQLQuery(query).executeUpdate();
	}

	public void deleteBadDatedebutAffecter(Session session) {
		String query = "delete from AFFECTER where datedebut ='0000-00-00 00:00:00'";
		session.createSQLQuery(query).executeUpdate();
	}

	public void deleteBadDatefinAffecter(Session session) {
		String query = "delete from AFFECTER where datefin ='0000-00-00 00:00:00'";
		session.createSQLQuery(query).executeUpdate();
	}

	public void deleteOrphanLienTachUtilCollaborateur(Session session) {

		String query = "DELETE LIENTACHUTIL FROM LIENTACHUTIL "
				+ "LEFT JOIN COLLABORATEUR ON COLLABORATEUR.idColl = LIENTACHUTIL.idColl "
				+ "WHERE COLLABORATEUR.idColl IS NULL";

		session.createSQLQuery(query).executeUpdate();
	}

	public void deleteOrphanLienTachUtilTache(Session session) {

		String query = "DELETE LIENTACHUTIL FROM LIENTACHUTIL "
				+ "LEFT JOIN TACHE ON TACHE.idTache = LIENTACHUTIL.idTache "
				+ "WHERE TACHE.idTache IS NULL";

		session.createSQLQuery(query).executeUpdate();
	}

	public void deleteBadDateLienTachUtil(Session session) {
		String query = "delete from LIENTACHUTIL where date ='0000-00-00 00:00:00'";
		session.createSQLQuery(query).executeUpdate();
	}
	
	public void deleteOrphanAppartientCollegeCollege(Session session) {

		String query = "DELETE APPARTIENTCOLLEGE FROM APPARTIENTCOLLEGE "
				+ "LEFT JOIN COLLEGE ON COLLEGE.idCollege = APPARTIENTCOLLEGE.idCollege "
				+ "WHERE COLLEGE.idCollege IS NULL";

		session.createSQLQuery(query).executeUpdate();
	}

	public void deleteOrphanAppartientCollegeCollaborateur(Session session) {

		String query = "DELETE APPARTIENTCOLLEGE FROM APPARTIENTCOLLEGE "
				+ "LEFT JOIN COLLABORATEUR ON COLLABORATEUR.idColl = APPARTIENTCOLLEGE.idColl "
				+ "WHERE COLLABORATEUR.idColl IS NULL";

		session.createSQLQuery(query).executeUpdate();
	}

	public void deleteBadDateAppartientCollege(Session session) {
		String query = "delete from APPARTIENTCOLLEGE where datec ='0000-00-00 00:00:00'";
		session.createSQLQuery(query).executeUpdate();
	}
	
	public void deleteOrphanContratProjet(Session session) {

		String query = "DELETE CONTRAT FROM CONTRAT "
				+ "LEFT JOIN PROJET ON PROJET.idProjet = CONTRAT.idProjet "
				+ "WHERE PROJET.idProjet IS NULL";

		session.createSQLQuery(query).executeUpdate();
	}

	public void deleteOrphanContratClientPart(Session session) {

		String query = "DELETE CONTRAT FROM CONTRAT "
				+ "LEFT JOIN CLIENTPART ON CLIENTPART.idClientPart = CONTRAT.idClientPart "
				+ "WHERE CLIENTPART.idClientPart IS NULL";

		session.createSQLQuery(query).executeUpdate();
	}
	
	public void deleteOrphanRoleCollabCollaborateur(Session session) {

		String query = "DELETE ROLECOLLAB FROM ROLECOLLAB "
			+ "LEFT JOIN COLLABORATEUR ON COLLABORATEUR.idColl = ROLECOLLAB.idColl "
			+ "WHERE COLLABORATEUR.idColl IS NULL";
		session.createSQLQuery(query).executeUpdate();
	}
}
