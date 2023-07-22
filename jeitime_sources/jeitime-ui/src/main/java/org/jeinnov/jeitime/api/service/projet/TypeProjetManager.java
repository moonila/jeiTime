package org.jeinnov.jeitime.api.service.projet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jeinnov.jeitime.api.to.projet.TypeProjetTO;
import org.jeinnov.jeitime.persistence.bo.projet.TypeProjetP;
import org.jeinnov.jeitime.persistence.dao.projet.TypeProjetDAO;
import org.jeinnov.jeitime.utils.HibernateUtil;


public class TypeProjetManager {
	private final Logger logger = Logger.getLogger(this.getClass());
	private TypeProjetDAO typeProjetDAO = TypeProjetDAO.getInstance();

	private static TypeProjetManager manager;

	public TypeProjetManager() {

	}

	public static TypeProjetManager getInstance() {
		if (manager == null) {
			manager = new TypeProjetManager();
		}
		return manager;
	}

	public TypeProjetTO get(int id) throws ProjetException {

		if (id == 0) {
			throw new ProjetException(
					"Attention aucun Type de Projet n'est sélectionné.");
		}
		TypeProjetTO typePTO = new TypeProjetTO();
		TypeProjetP typePP = new TypeProjetP();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			typePP = typeProjetDAO.find(id);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		if (typePP != null) {
			typePTO.setIdTypeProj(typePP.getIdTypePro());
			typePTO.setNomTypePro(typePP.getNomTypePro());
		} else {
			throw new ProjetException(
					"Attention aucun Type de Projet existe avec cet identifiant dans la base.");
		}
		return typePTO;
	}

	public List<TypeProjetTO> getAll() {
		List<TypeProjetTO> listTypePTO = new ArrayList<TypeProjetTO>();
		List<TypeProjetP> listTypePP = new ArrayList<TypeProjetP>();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			listTypePP = typeProjetDAO.getAll();
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		if (listTypePP != null) {
			for (TypeProjetP tPP : listTypePP) {
				TypeProjetTO tPTO = new TypeProjetTO();
				tPTO.setIdTypeProj(tPP.getIdTypePro());
				tPTO.setNomTypePro(tPP.getNomTypePro());

				listTypePTO.add(tPTO);
			}
		}
		return listTypePTO;
	}

	public int saveOrUpdate(TypeProjetTO tPTO) throws ProjetException {
		if (tPTO == null) {
			throw new ProjetException(
					"Attention aucun type de projet n'est sélectionné");
		}
		if (tPTO.getNomTypePro() == null) {
			throw new ProjetException(
					"Attention aucun nom n'a été donné au type de projet ");
		}
		TypeProjetP typePP = new TypeProjetP();
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			typePP = typeProjetDAO.getByName(tPTO.getNomTypePro());
			verificationAvantEnregistrement(tPTO, typePP);
			if (typePP == null || typePP.getIdTypePro() == 0) {
				typePP = new TypeProjetP();
			}
			typePP.setIdTypePro(tPTO.getIdTypeProj());
			typePP.setNomTypePro(tPTO.getNomTypePro());
			typeProjetDAO.save(typePP);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		int id = typePP.getIdTypePro();
		return id;
	}

	public void delete(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun type de projet n'est sélectionné");
		}
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			TypeProjetP typePP = typeProjetDAO.find(id);
			if (typePP == null || typePP.getIdTypePro() == 0) {
				throw new ProjetException(
						"Attention aucun Type de Projet existe avec cet identifiant dans la base.");
			}
			if (typePP.getProjets() != null && !typePP.getProjets().isEmpty()) {
				throw new ProjetException(
						"Attention ce Type de projet ne pourra être supprimé, car des projets lui sont rattachés.");
			}
			typeProjetDAO.remove(typePP);
			tx.commit();
		} catch (final RuntimeException e) {
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	public boolean isInProjet(int id) throws ProjetException {
		if (id == 0) {
			throw new ProjetException(
					"Attention aucun Type de Projet n'est sélectionné.");
		}
		boolean verif = true;
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		try {
			verif = typeProjetDAO.isInProject(id, session);
			tx.commit();
		} catch (final RuntimeException e) {
			verif = true;
			tx.rollback();
			logger.error(e.getMessage(), e);
			throw e;
		}
		return verif;
	}

	private void verificationAvantEnregistrement(TypeProjetTO typePT,
			TypeProjetP typePP) throws ProjetException {
		if (typePP != null) {
			if (typePP.getIdTypePro() != typePT.getIdTypeProj()) {
				throw new ProjetException(
						"Attention,un type de projet avec ce nom existe déjà !");
			}
		}
	}
}
