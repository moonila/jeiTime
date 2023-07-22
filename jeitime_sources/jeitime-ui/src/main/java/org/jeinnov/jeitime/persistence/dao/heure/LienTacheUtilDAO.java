package org.jeinnov.jeitime.persistence.dao.heure;

import java.sql.Timestamp;
import java.util.List;

import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilIdP;
import org.jeinnov.jeitime.persistence.bo.projet.LienTachUtilP;
import org.jeinnov.jeitime.persistence.dao.GenericDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;

import com.trg.search.Filter;
import com.trg.search.Search;
import com.trg.search.Sort;


public class LienTacheUtilDAO extends GenericDAO<LienTachUtilP, Integer> {

	private static LienTacheUtilDAO dao;

	public LienTacheUtilDAO() {

	}

	static {
		dao = new LienTacheUtilDAO();
		dao.setSessionFactory(HibernateUtil.getSessionFactory());

	}

	public static LienTacheUtilDAO getInstance() {
		return dao;
	}

	public List<LienTachUtilP> getAllByIdCollaborateurAndStartDateAndEndDate(
			int idCollaborateur, Timestamp start, Timestamp end) {

		Search search = new Search();
		search.addFilterAnd(Filter.equal("id.idColl", idCollaborateur));
		search.addFilterGreaterOrEqual("id.date", start);
		search.addFilterLessOrEqual("id.date", end);
		search.addSort(new Sort("id.date"));

		List<LienTachUtilP> listLienTachUtil = search(search);

		return listLienTachUtil;
	}

	public LienTachUtilP getByIdCollaborateurAndIdTacheAndDate(int idCollab,
			int idTache, Timestamp date) {

		LienTachUtilIdP id = new LienTachUtilIdP();
		id.setDate(date);
		id.setIdColl(idCollab);
		id.setIdTache(idTache);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("id", id));

		LienTachUtilP lienTachUtil = searchUnique(search);

		return lienTachUtil;
	}

	public List<LienTachUtilP> getAllByIdCollaborateurAndDate(int idCollab,
			Timestamp date) {

		CollaborateurP collaborateur = new CollaborateurP();
		collaborateur.setIdColl(idCollab);

		Search search = new Search();
		search.addFilterAnd(Filter.equal("id.date", date));
		search.addFilterAnd(Filter.equal("id.idColl", idCollab));

		List<LienTachUtilP> listLienTachUtil = search(search);

		return listLienTachUtil;
	}
}
