package org.jeinnov.jeitime.persistence.dao.bilan;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.jeinnov.jeitime.api.to.bilan.RecapProjetHibernate;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;

public class RecapDomDAO {

	private static RecapDomDAO dao;

	private CreateSQLQuery createSQLQuery = new CreateSQLQuery();

	public RecapDomDAO() {

	}

	public static RecapDomDAO getInstance() {
		if (dao == null) {
			dao = new RecapDomDAO();
		}

		return dao;
	}

	@SuppressWarnings("unchecked")
	public List<RecapProjetHibernate> creerListRecapProjet(Session session,
			int idT, Date dateD, Date dateF) {

		Timestamp timeDeb = new Timestamp(dateD.getTime());
		Timestamp timeFin = new Timestamp(dateF.getTime());

		// Création de la string pour faire la requête qui permettra le
		// récapitulatif
		String query = createSQLQuery.createRecapQueryDomaine(idT, timeDeb,
				timeFin);

		// Requête avec un alias sur RecapProjetHibernate
		Query getRecap = session.createSQLQuery(query).setResultTransformer(
				Transformers.aliasToBean(RecapProjetHibernate.class));

		List<RecapProjetHibernate> listTache = new ArrayList<RecapProjetHibernate>();

		listTache = getRecap.list();

		return listTache;
	}

	@SuppressWarnings("unchecked")
	public List<TacheP> listTacheDomaine(Session session, int id) {
		// Création de la string pour faire la requête de récupération des
		// taches
		String query = createSQLQuery.createTacheQueryDomaine(id);

		// Requête sur l'entité tâche
		Query q = session.createSQLQuery(query).addEntity(TacheP.class);
		List<TacheP> list = q.list();

		// Collections.sort(list);

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<TacheP> listTache(Session session, int id) {

		String query = createSQLQuery.createNomTacheQueryDomaine(id);

		List<TacheP> listTache = session.createSQLQuery(query).addEntity(
				TacheP.class).list();

		return listTache;
	}

}
