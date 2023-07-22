package org.jeinnov.jeitime.persistence.dao.bilan;

import java.sql.Timestamp;
import java.util.List;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.jeinnov.jeitime.api.to.bilan.RecapProjetMensuelHibernate;

public class RecapProjetMensuelDAO {

	private static RecapProjetMensuelDAO dao;

	public RecapProjetMensuelDAO() {

	}

	public static RecapProjetMensuelDAO getInstance() {
		if (dao == null) {
			dao = new RecapProjetMensuelDAO();
		}
		return dao;
	}

	private CreateSQLQuery createSQLQuery = new CreateSQLQuery();

	/***************************************************
	 **M�thode permettant de faire la liste des projets s�lectionn�s par leurs
	 * types,eux-m�mes pour g�n�rer les tableaux r�capitulatifs
	 ***************************************************/

	@SuppressWarnings("unchecked")
	public List<RecapProjetMensuelHibernate> creerListRecapProjet(
			Session session, int idProjet, Timestamp timeDeb, Timestamp timeFin) {

		String query = createSQLQuery.createRecapQueryProjetMensuel(idProjet,
				timeDeb, timeFin);

		Query getByFonction = session
				.createSQLQuery(query)
				.setResultTransformer(
						Transformers
								.aliasToBean(RecapProjetMensuelHibernate.class));

		List<RecapProjetMensuelHibernate> listTache = getByFonction.list();

		return listTache;
	}

	/*
	 * @SuppressWarnings("unchecked") public ArrayList<RecapProjetMensuelTO>
	 * creerListRecapProjet(int[] idProjet, Date dateD, Date dateF) {
	 * ArrayList<RecapProjetMensuelTO> listRecap = new
	 * ArrayList<RecapProjetMensuelTO>(); Timestamp timeDeb = new
	 * Timestamp(dateD.getTime()); Timestamp timeFin = new
	 * Timestamp(dateF.getTime()); for (int i = 0; i < idProjet.length; i++) {
	 * int idP = idProjet[i]; String query = null; query =
	 * "select c.NOMCOLL, c.IDCOLL, p.NOMPROJET, t.IDTACHE, nt.IDNOMTACHE, nt.NOMTACHE,p.IDPROJET, ltu.NBHEURE, ltu.DATE "
	 * +
	 * " from PROJET p, TACHE t, LIENTACHUTIL ltu, COLLABORATEUR c, NOMTACHE nt"
	 * + " where t.idprojet = p.idprojet and nt.idnomtache = t.idnomtache" +
	 * " and ltu.idtache=t.idtache and ltu.idcoll = c.idcoll " +
	 * " and t.idprojet = " + idP + " and ltu.date between '" + timeDeb +
	 * "' and '" + timeFin + "' " +
	 * " group by c.nomcoll, c.idcoll, p.nomprojet, t.idtache, nt.idnomtache, nt.nomtache,p.IDPROJET, ltu.NBHEURE, ltu.DATE"
	 * + " order by c.nomcoll;";
	 * 
	 * Session session = HibernateUtil.getSessionFactory() .getCurrentSession();
	 * session.beginTransaction();
	 * 
	 * Query getByFonction = session .createSQLQuery(query)
	 * .setResultTransformer( Transformers
	 * .aliasToBean(RecapProjetMensuelHibernate.class));
	 * 
	 * List<RecapProjetMensuelHibernate> listTache = getByFonction.list(); for
	 * (int j = 0; j < listTache.size(); j++) { ProjetTO projet = new
	 * ProjetTO();
	 * 
	 * projet.setNomProjet(listTache.get(j).getNomProjet());
	 * projet.setIdProjet(listTache.get(j).getIdProjet());
	 * 
	 * CollaborateurTO collab = new CollaborateurTO(listTache.get(j)
	 * .getIdColl(), listTache.get(j).getNomColl()); double nbheure =
	 * listTache.get(j).getNbheure(); Date d = listTache.get(j).getDate();
	 * 
	 * int jour = definirLeJour(d);
	 * 
	 * RecapProjetMensuelTO r = new RecapProjetMensuelTO(); r.setCollab(collab);
	 * r.setNomProjet(projet); r.setJour(jour); r.setNbheure(nbheure);
	 * r.setIdTache(listTache.get(j).getIdTache()); listRecap.add(r); }
	 * 
	 * session.getTransaction().commit();
	 * HibernateUtil.getSessionFactory().close();
	 * 
	 * calculTotalProjet(listRecap, i); } calculTotalPourCollab(listRecap);
	 * 
	 * return listRecap; }
	 * 
	 * private void calculTotalProjet(ArrayList<RecapProjetMensuelTO> listRecap,
	 * int i) { double nh = 0; for (int l = 0; l < listRecap.size(); l++) { int
	 * j = l + 1; while (j < listRecap.size()) { if
	 * (listRecap.get(l).getNomProjet() == listRecap.get(j) .getNomProjet() &&
	 * listRecap.get(l).getCollab() == listRecap.get(i) .getCollab() &&
	 * listRecap.get(l).getJour() == listRecap.get(i) .getJour()) { nh =
	 * listRecap.get(l).getNbheure() + nh; listRecap.remove(j); } else { j++; }
	 * } } }
	 * 
	 * private void calculTotalPourCollab(ArrayList<RecapProjetMensuelTO>
	 * listRecap) { double nbh = 0; for (int i = 0; i < listRecap.size(); i++) {
	 * 
	 * int j = i + 1; while (j < listRecap.size()) { if
	 * (listRecap.get(i).getCollab().getIdColl() == listRecap
	 * .get(j).getCollab().getIdColl() &&
	 * listRecap.get(i).getNomProjet().getIdProjet() == listRecap
	 * .get(j).getNomProjet().getIdProjet() && listRecap.get(i).getJour() ==
	 * listRecap.get(j) .getJour()) { nbh = listRecap.get(i).getNbheure() +
	 * listRecap.get(j).getNbheure(); listRecap.get(i).setNbheure(nbh);
	 * listRecap.remove(j); } else { j++; } }
	 * 
	 * } }
	 * 
	 * private int definirLeJour(Date d) { int j; Calendar cal =
	 * Calendar.getInstance(); cal.setTime(d); j =
	 * cal.get((Calendar.DAY_OF_MONTH));
	 * 
	 * return j; }
	 */
	/******************************************************************************************************
	 * This method allows to fill a selected table project with its associated
	 * tasks. Furthermore, this method allows user to show a certain type of
	 * selected tasks (R&D, Non R&D, both) associated to a project
	 *****************************************************************************************************/

	/*
	 * @SuppressWarnings("unchecked") public ArrayList<Object> listCollab(int[]
	 * idProjet) { // int idProjet = 0;
	 * 
	 * Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	 * session.beginTransaction(); int idP = 0; ArrayList<Object> listItems =
	 * new ArrayList<Object>(); for (int i = 0; i < idProjet.length; i++) {
	 * 
	 * String q1 = "Select * from PROJET where idprojet = " + idProjet[i];
	 * 
	 * ProjetP p = (ProjetP) session.createSQLQuery(q1).addEntity(
	 * ProjetP.class).uniqueResult();
	 * 
	 * String nomProjet = p.getNomProjet(); listItems.add((String) nomProjet);
	 * 
	 * String q2 = "Select * from COLLABORATEUR;";
	 * 
	 * Query q = session.createSQLQuery(q2) .addEntity(CollaborateurP.class);
	 * List<CollaborateurP> listColl = new ArrayList<CollaborateurP>();
	 * 
	 * listColl = q.list();
	 * 
	 * idP = p.getIdProjet(); for (int j = 0; j < listColl.size(); j++) {
	 * 
	 * CollaborateurRecapMensTO c = new CollaborateurRecapMensTO();
	 * 
	 * c.setIdCollab(listColl.get(j).getIdColl());
	 * c.setNomCollab(listColl.get(j).getNomColl());
	 * c.setPrenomCollab(listColl.get(j).getPrenomColl()); c.setIdProjet(idP);
	 * 
	 * listItems.add((CollaborateurRecapMensTO) c); }
	 * 
	 * listItems.add((Integer) idP);
	 * 
	 * } session.getTransaction().commit();
	 * HibernateUtil.getSessionFactory().close();
	 * 
	 * return listItems; }
	 */
}
