package org.jeinnov.jeitime.persistence.dao.bilan;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.jeinnov.jeitime.api.to.bilan.RecapProjetHibernate;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;

public class RecapThemaDAO {

	private static RecapThemaDAO dao;
	private CreateSQLQuery createSQLQuery = new CreateSQLQuery();

	public RecapThemaDAO() {

	}

	public static RecapThemaDAO getInstance() {
		if (dao == null) {
			dao = new RecapThemaDAO();
		}
		return dao;
	}

	/*****************************************
	 * Méthode permettant de générer la liste des projets triée par thématique
	 *****************************************/
	@SuppressWarnings("unchecked")
	public List<RecapProjetHibernate> creerListRecapProjetThema(
			Session session, int idT, Date dateD, Date dateF) {

		Timestamp timeDeb = new Timestamp(dateD.getTime());
		Timestamp timeFin = new Timestamp(dateF.getTime());

		String query = createSQLQuery.createRecapQueryThema(idT, timeDeb,
				timeFin);

		Query getRecap = session.createSQLQuery(query).setResultTransformer(
				Transformers.aliasToBean(RecapProjetHibernate.class));

		List<RecapProjetHibernate> listTache = getRecap.list();

		return listTache;
	}

	@SuppressWarnings("unchecked")
	public List<TacheP> listTacheThema(Session session, int id) {
		// Création de la string pour faire la requête de récupération des
		// taches
		String query = createSQLQuery.createTacheQueryThema(id);

		// Requête sur l'entité tâche
		Query q = session.createSQLQuery(query).addEntity(TacheP.class);
		List<TacheP> list = q.list();

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<TacheP> listTache(Session session, int id) {

		String query = createSQLQuery.createNomTacheQueryThema(id);

		List<TacheP> listTache = session.createSQLQuery(query).addEntity(
				TacheP.class).list();

		return listTache;
	}
	/*
	 * @SuppressWarnings("unchecked") public ArrayList<Object> listTacheT(int[]
	 * tablIdThema) {
	 * 
	 * ArrayList<Object> listItems = new ArrayList<Object>(); Session session =
	 * HibernateUtil.getSessionFactory().getCurrentSession();
	 * session.beginTransaction(); // ArrayList<TacheTO> listT = new
	 * ArrayList<TacheTO>();
	 * 
	 * for (int i = 0; i < tablIdThema.length; i++) {
	 * 
	 * int idT = tablIdThema[i];
	 * 
	 * String query = createSQLQuery.createNomTacheQueryThema(idT);
	 * 
	 * Query getByFonction = session.createSQLQuery(query).addEntity(
	 * TacheP.class);
	 * 
	 * List<TacheP> listTache = getByFonction.list();
	 * 
	 * if (listTache == null || listTache.isEmpty()) {
	 * 
	 * ThematiqueP tp = (ThematiqueP) session.load(ThematiqueP.class, idT);
	 * String nomThema = tp.getNomThematique(); listItems.add((String)
	 * nomThema); // listItems.add((ProjetTO)pTO); listItems.add((Integer)
	 * tp.getIdThematique()); } else { for (int j = 0; j < listTache.size();
	 * j++) { String nomThema = listTache.get(j).getProjet()
	 * .getThematique().getNomThematique();
	 * 
	 * TypeTacheTO typeTache = null;
	 * 
	 * NomTacheTO nomtache = new NomTacheTO(listTache.get(j)
	 * .getNomTacheP().getIdNomTache(), listTache.get(j)
	 * .getNomTacheP().getNomTache(), typeTache);
	 * 
	 * TacheTO tache = new TacheTO(listTache.get(j).getIdTache(), nomtache,
	 * listTache.get(j).getBudgetprevu(), listTache.get(j).getTpsprevu(),
	 * listTache.get(j) .getPriorite(), listTache.get(j) .isEligible(), null);
	 * tache.setEligible(listTache.get(j).isEligible()); ProjetTO proj = new
	 * ProjetTO(); proj .setIdProjet(listTache.get(j).getProjet()
	 * .getIdProjet()); proj.setNomProjet(listTache.get(j).getProjet()
	 * .getNomProjet()); proj.setThematique(new ThematiqueTO(listTache.get(j)
	 * .getProjet().getThematique().getIdThematique()));
	 * 
	 * listItems.add((String) nomThema); listItems.add((ProjetTO) proj);
	 * listItems.add((TacheTO) tache);
	 * 
	 * tache.setProjet(proj); } } listItems.add((Integer) idT); }
	 * 
	 * for (int i = 0; i < listItems.size(); i++) { int j = i + 1; while (j <
	 * listItems.size()) { if (listItems.get(i) == listItems.get(j)) {
	 * listItems.remove(j); } if (listItems.get(i) instanceof ProjetTO &&
	 * listItems.get(j) instanceof ProjetTO) { if (((ProjetTO)
	 * listItems.get(i)).getIdProjet() == ((ProjetTO) listItems
	 * .get(j)).getIdProjet()) { listItems.remove(j);
	 * 
	 * } else { j++; } } else { j++; } }
	 * 
	 * }
	 * 
	 * { session.getTransaction().commit();
	 * HibernateUtil.getSessionFactory().close(); }
	 * 
	 * listItems = insertionSousTotal(listItems);
	 * 
	 * listItems = insertionSousTotalProjet(listItems);
	 * 
	 * return listItems; }
	 */
	/*
	 * public ArrayList<Object> insertionSousTotal(ArrayList<Object> listItems)
	 * { ArrayList<Object> list = new ArrayList<Object>();
	 * 
	 * for (int i = 0; i < listItems.size(); i++) {
	 * 
	 * if (listItems.get(i) instanceof String) { list.add((String)
	 * listItems.get(i)); } else if (listItems.get(i) instanceof ProjetTO) {
	 * list.add((ProjetTO) listItems.get(i)); } else if (listItems.get(i)
	 * instanceof TacheTO) {
	 * 
	 * if (listItems.get(i - 1) instanceof ProjetTO || ((TacheTO)
	 * listItems.get(i - 1)).isEligible() == true && ((TacheTO)
	 * listItems.get(i)).isEligible() == true) { list.add((TacheTO)
	 * listItems.get(i)); } if (listItems.get(i - 1) instanceof TacheTO &&
	 * ((TacheTO) listItems.get(i - 1)).isEligible() == true && ((TacheTO)
	 * listItems.get(i)).isEligible() == false)
	 * 
	 * { SousTotal sst = new SousTotal(); sst.setIdProjet(((TacheTO)
	 * listItems.get(i)).getProjet() .getIdProjet()); sst.setRd(true);
	 * sst.setNomSousTotal("Sous Total R&D"); list.add((SousTotal) sst);
	 * list.add((TacheTO) listItems.get(i)); }
	 * 
	 * if (listItems.get(i - 1) instanceof TacheTO && ((TacheTO) listItems.get(i
	 * - 1)).isEligible() == false && ((TacheTO) listItems.get(i)).isEligible()
	 * == false)
	 * 
	 * { list.add((TacheTO) listItems.get(i)); } if (listItems.get(i + 1)
	 * instanceof Integer && ((TacheTO) listItems.get(i)).isEligible() == false)
	 * 
	 * { // list.add((TacheTO)listItems.get(i)); SousTotal sst = new
	 * SousTotal(); sst.setNomSousTotal("Sous Total non R&D");
	 * sst.setIdProjet(((TacheTO) listItems.get(i)).getProjet() .getIdProjet());
	 * sst.setRd(false); list.add((SousTotal) sst); } if (listItems.get(i + 1)
	 * instanceof Integer && ((TacheTO) listItems.get(i)).isEligible() == true)
	 * 
	 * { // list.add((TacheTO)listItems.get(i)); SousTotal sst = new
	 * SousTotal(); sst.setNomSousTotal("Sous Total R&D");
	 * sst.setIdProjet(((TacheTO) listItems.get(i)).getProjet() .getIdProjet());
	 * sst.setRd(true); list.add((SousTotal) sst); } if (listItems.get(i + 1)
	 * instanceof ProjetTO && ((TacheTO) listItems.get(i)).isEligible() ==
	 * false)
	 * 
	 * { // list.add((TacheTO)listItems.get(i)); SousTotal sst = new
	 * SousTotal(); sst.setNomSousTotal("Sous Total non R&D");
	 * sst.setIdProjet(((TacheTO) listItems.get(i)).getProjet() .getIdProjet());
	 * sst.setRd(false); list.add((SousTotal) sst); } if (listItems.get(i + 1)
	 * instanceof ProjetTO && ((TacheTO) listItems.get(i)).isEligible() == true)
	 * 
	 * { // list.add((TacheTO)listItems.get(i)); SousTotal sst = new
	 * SousTotal(); sst.setNomSousTotal("Sous Total R&D");
	 * sst.setIdProjet(((TacheTO) listItems.get(i)).getProjet() .getIdProjet());
	 * sst.setRd(true); list.add((SousTotal) sst); }
	 * 
	 * } else { list.add((Integer) listItems.get(i)); } }
	 * 
	 * return list; }
	 * 
	 * public ArrayList<Object> insertionSousTotalProjet( ArrayList<Object>
	 * listItems) { ArrayList<Object> list = new ArrayList<Object>();
	 * 
	 * for (int i = 0; i < listItems.size(); i++) {
	 * 
	 * if (listItems.get(i) instanceof String) { list.add((String)
	 * listItems.get(i)); } else if (listItems.get(i) instanceof ProjetTO) {
	 * list.add((ProjetTO) listItems.get(i)); } else if (listItems.get(i)
	 * instanceof TacheTO) { list.add((TacheTO) listItems.get(i)); } else if
	 * (listItems.get(i) instanceof SousTotal) { if (listItems.get(i - 1)
	 * instanceof TacheTO)
	 * 
	 * { list.add((SousTotal) listItems.get(i)); } if (listItems.get(i + 1)
	 * instanceof ProjetTO || listItems.get(i + 1) instanceof Integer)
	 * 
	 * { Total t = new Total(); t.setIdProjet(((SousTotal)
	 * listItems.get(i)).idProjet); list.add((Total) t); } } else {
	 * list.add((Integer) listItems.get(i)); } }
	 * 
	 * return list; }
	 */
}
