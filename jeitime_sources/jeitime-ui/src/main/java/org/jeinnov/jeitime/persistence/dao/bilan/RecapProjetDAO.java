package org.jeinnov.jeitime.persistence.dao.bilan;

import java.sql.Timestamp;
import java.util.List;



import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.jeinnov.jeitime.api.to.bilan.RecapProjetHibernate;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;

public class RecapProjetDAO {
	private static RecapProjetDAO dao;

	public RecapProjetDAO() {

	}

	public static RecapProjetDAO getInstance() {
		if (dao == null) {
			dao = new RecapProjetDAO();
		}
		return dao;
	}

	private CreateSQLQuery createSQLQuery = new CreateSQLQuery();

	@SuppressWarnings("unchecked")
	public List<RecapProjetHibernate> creerListRecapProjet(Session session,
			int idProjet, Timestamp timeDeb, Timestamp timeFin) {

		String query = createSQLQuery.createRecapQueryProjet(idProjet, timeDeb,
				timeFin);

		Query getByFonction = session.createSQLQuery(query)
				.setResultTransformer(
						Transformers.aliasToBean(RecapProjetHibernate.class));

		List<RecapProjetHibernate> listTache = getByFonction.list();

		return listTache;
	}

	@SuppressWarnings("unchecked")
	public List<TacheP> listTache(Session session, int idProjet) {

		String query = createSQLQuery.createNomTacheQueryProjet(idProjet);

		Query getByFonction = session.createSQLQuery(query).addEntity(
				TacheP.class);

		List<TacheP> listTache = getByFonction.list();

		return listTache;
	}

	@SuppressWarnings("unchecked")
	public List<RecapProjetHibernate> creerListRecapSuiviProjet(
			Session session, int idProjet) {

		String query = createSQLQuery.createRecapQuerySuiviProjet(idProjet);

		Query getByFonction = session.createSQLQuery(query)
				.setResultTransformer(
						Transformers.aliasToBean(RecapProjetHibernate.class));

		List<RecapProjetHibernate> listTache = getByFonction.list();

		return listTache;
	}

}
