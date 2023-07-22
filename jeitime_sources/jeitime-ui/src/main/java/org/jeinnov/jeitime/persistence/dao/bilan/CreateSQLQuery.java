package org.jeinnov.jeitime.persistence.dao.bilan;

import java.sql.Timestamp;

public class CreateSQLQuery {

	public CreateSQLQuery() {
		super();
	}

	public String createRecapQueryDomaine(int id, Timestamp timeDeb,
			Timestamp timeFin) {
		String query = "select c.NOMCOLL, c.IDCOLL, p.NOMPROJET, t.IDTACHE, nt.IDNOMTACHE, nt.NOMTACHE,p.IDPROJET, "
				+ " Sum(ltu.nbheure) as TOTAL "
				+ " from PROJET p, TACHE t, LIENTACHUTIL ltu, COLLABORATEUR c, NOMTACHE nt"
				+ " where t.idprojet = p.idprojet and nt.idnomtache = t.idnomtache"
				+ " and ltu.idtache=t.idtache and ltu.idcoll = c.idcoll "
				+ " and p.iddomaine = "
				+ id
				+ " and ltu.date between '"
				+ timeDeb
				+ "' and '"
				+ timeFin
				+ "' "
				+ " group by c.nomcoll, c.idcoll, p.nomprojet, t.idtache, nt.idnomtache, nt.nomtache,p.IDPROJET"
				+ " order by p.idprojet;";

		return query;
	}

	public String createTacheQueryDomaine(int id) {
		String query = "Select * from TACHE t, PROJET p "
				+ "where t.idprojet = p.idprojet and p.iddomaine = " + id
				+ " order by p.idprojet";

		return query;
	}

	public String createNomTacheQueryDomaine(int id) {
		String query = "select  * from TACHE t, NOMTACHE nt, PROJET p "
				+ " where p.idprojet = t.idprojet "
				+ " and t.idnomtache = nt.idnomtache " + " and p.iddomaine ="
				+ id + " order by p.nomprojet, t.eligible=false, nt.nomtache ;";

		return query;
	}

	public String createRecapQueryThema(int id, Timestamp timeDeb,
			Timestamp timeFin) {
		String query = "select c.NOMCOLL, c.IDCOLL, p.NOMPROJET, t.IDTACHE, nt.IDNOMTACHE, nt.NOMTACHE,p.IDPROJET, "
				+ " Sum(ltu.nbheure) as TOTAL "
				+ " from PROJET p, TACHE t, LIENTACHUTIL ltu, COLLABORATEUR c, NOMTACHE nt"
				+ " where t.idprojet = p.idprojet and nt.idnomtache = t.idnomtache"
				+ " and ltu.idtache=t.idtache and ltu.idcoll = c.idcoll "
				+ " and p.idthematique = "
				+ id
				+ " and ltu.date between '"
				+ timeDeb
				+ "' and '"
				+ timeFin
				+ "' "
				+ " group by c.nomcoll, c.idcoll, p.nomprojet, t.idtache, nt.idnomtache, nt.nomtache,p.IDPROJET"
				+ " order by c.nomcoll;";
		return query;

	}

	public String createTacheQueryThema(int id) {
		String query = "Select * from TACHE t, PROJET p where t.idprojet = p.idprojet and p.idthematique = "
				+ id;
		return query;
	}

	public String createNomTacheQueryThema(int id) {
		String query = "select  * from TACHE t, NOMTACHE nt, PROJET p "
				+ " where p.idprojet = t.idprojet "
				+ " and t.idnomtache = nt.idnomtache "
				+ " and p.idthematique =" + id
				+ " order by  p.idprojet, t.eligible=false,nt.nomtache;";

		return query;
	}

	public String createRecapQueryTypeProjet(int id, Timestamp timeDeb,
			Timestamp timeFin) {
		String query = "select c.NOMCOLL, c.IDCOLL, p.NOMPROJET, t.IDTACHE, nt.IDNOMTACHE, nt.NOMTACHE,p.IDPROJET, "
				+ " Sum(ltu.nbheure) as TOTAL "
				+ " from PROJET p, TACHE t, LIENTACHUTIL ltu, COLLABORATEUR c, NOMTACHE nt"
				+ " where t.idprojet = p.idprojet and nt.idnomtache = t.idnomtache"
				+ " and ltu.idtache=t.idtache and ltu.idcoll = c.idcoll "
				+ " and p.idtypeprojet = "
				+ id
				+ " and ltu.date between '"
				+ timeDeb
				+ "' and '"
				+ timeFin
				+ "' "
				+ " group by c.nomcoll, c.idcoll, p.nomprojet, t.idtache, nt.idnomtache, nt.nomtache,p.IDPROJET"
				+ " order by p.idprojet;";

		return query;
	}

	public String createTacheQueryTypeProjet(int id) {
		String query = "Select * from TACHE t, PROJET p "
				+ "where t.idprojet = p.idprojet and p.idtypeprojet = " + id
				+ " order by p.idprojet";

		return query;
	}

	public String createNomTacheQueryTypeProjet(int id) {
		String query = "select  * from TACHE t, NOMTACHE nt, PROJET p "
				+ " where p.idprojet = t.idprojet "
				+ " and t.idnomtache = nt.idnomtache "
				+ " and p.idtypeprojet =" + id
				+ " order by p.nomprojet, t.eligible=false, nt.nomtache ;";

		return query;
	}

	public String createRecapQueryProjetMensuel(int idP, Timestamp timeDeb,
			Timestamp timeFin) {
		String query = "select c.NOMCOLL, c.IDCOLL, p.NOMPROJET, t.IDTACHE, nt.IDNOMTACHE, nt.NOMTACHE,p.IDPROJET, ltu.NBHEURE, ltu.DATE "
				+ " from PROJET p, TACHE t, LIENTACHUTIL ltu, COLLABORATEUR c, NOMTACHE nt"
				+ " where t.idprojet = p.idprojet and nt.idnomtache = t.idnomtache"
				+ " and ltu.idtache=t.idtache and ltu.idcoll = c.idcoll "
				+ " and t.idprojet = "
				+ idP
				+ " and ltu.date between '"
				+ timeDeb
				+ "' and '"
				+ timeFin
				+ "' "
				+ " group by c.nomcoll, c.idcoll, p.nomprojet, t.idtache, nt.idnomtache, nt.nomtache,p.IDPROJET, ltu.NBHEURE, ltu.DATE"
				+ " order by c.nomcoll;";

		return query;
	}

	public String createRecapQueryProjet(int idP, Timestamp timeDeb,
			Timestamp timeFin) {
		String query = "select c.NOMCOLL, c.IDCOLL, p.NOMPROJET, t.IDTACHE, nt.IDNOMTACHE, nt.NOMTACHE,p.IDPROJET, "
				+ " Sum(ltu.nbheure) as TOTAL "
				+ " from PROJET p, TACHE t, LIENTACHUTIL ltu, COLLABORATEUR c, NOMTACHE nt"
				+ " where t.idprojet = p.idprojet and nt.idnomtache = t.idnomtache"
				+ " and ltu.idtache=t.idtache and ltu.idcoll = c.idcoll "
				+ " and t.idprojet = "
				+ idP
				+ " and ltu.date between '"
				+ timeDeb
				+ "' and '"
				+ timeFin
				+ "' "
				+ " group by c.nomcoll, c.idcoll, p.nomprojet, t.idtache, nt.idnomtache, nt.nomtache,p.IDPROJET"
				+ " order by c.nomcoll;";

		return query;
	}

	public String createRecapQuerySuiviProjet(int idP) {
		String query = "select c.NOMCOLL, c.IDCOLL, p.NOMPROJET, t.IDTACHE, nt.IDNOMTACHE, nt.NOMTACHE,p.IDPROJET, "
				+ " Sum(ltu.nbheure) as TOTAL "
				+ " from PROJET p, TACHE t, LIENTACHUTIL ltu, COLLABORATEUR c, NOMTACHE nt"
				+ " where t.idprojet = p.idprojet and nt.idnomtache = t.idnomtache"
				+ " and ltu.idtache=t.idtache and ltu.idcoll = c.idcoll "
				+ " and t.idprojet = "
				+ idP
				+ " group by c.nomcoll, c.idcoll, p.nomprojet, t.idtache, nt.idnomtache, nt.nomtache,p.IDPROJET"
				+ " order by c.nomcoll;";

		return query;
	}

	public String createNomTacheQueryProjet(int id) {
		String query = "select  * from TACHE t, NOMTACHE nt, PROJET p "
				+ " where p.idprojet = t.idprojet "
				+ " and t.idnomtache = nt.idnomtache " + " and p.idprojet ="
				+ id + " order by p.nomprojet, t.eligible=false, nt.nomtache ;";

		return query;
	}
}
