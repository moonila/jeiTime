package org.jeinnov.jeitime.ui.projet;

import javax.servlet.http.HttpServletRequest;

import org.jeinnov.jeitime.api.service.projet.DomaineManager;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.to.projet.DomaineTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;


import java.util.ArrayList;
import java.util.List;

public class DomaineUIBean {

	private DomaineManager domaineManager = DomaineManager.getInstance();

	// --- Variables --- //

	private int idDom;
	private String nomDom;

	private DomaineTO selected;
	private List<DomaineTO> allDomaine;

	private List<String> domCir;

	// --- Actions Methods --- //

	public void loadAll() {
		allDomaine = new ArrayList<DomaineTO>();
		allDomaine = domaineManager.getAll();
		domCir = getDomaineCir();
	}

	public List<String> getDomaineCir() {
		List<String> recup = new ArrayList<String>();
		recup.add("Informatique,Electronique");
		recup.add("Biologie,Botanique");
		recup.add("Sciences médicales");
		recup.add("Mathématiques,Physique fondamentale");
		recup.add("Sciences agronomiques et alimentaires");
		recup.add("Océan, Atmosphère, Terre, Environnement naturel");
		recup.add("Energétique,Thermique,");
		recup.add("...");
		return recup;
	}

	public void create() throws IError {
		DomaineTO dom = new DomaineTO(idDom, nomDom);
		try {
			domaineManager.saveOrUpdate(dom);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue, "
							+ "le domaine n'a pas pu etre sauvegardé. "
							+ "Un autre domaine existe peut-être déjà avec ce nom. ",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void update() throws IError {
		int id = selected.getIdDomaine();
		String nom = selected.getNomDomaine();

		DomaineTO dom = new DomaineTO(id, nom);
		try {
			domaineManager.saveOrUpdate(dom);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. Le domaine n'a pas pu être sauvegardé."
							+ "Le domaine avec ce  nom existe peut-être déjà dans la base.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		refresh();
	}

	public void delete(HttpServletRequest iRequest) throws IError {
		int id = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			domaineManager.delete(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue, le domaine n'a pas pu être supprimé.",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}

		refresh();
	}

	public void cancel() {
		selected = null;
	}

	public void refresh() {
		idDom = 0;
		nomDom = null;
		selected = null;

		allDomaine = new ArrayList<DomaineTO>();
		allDomaine = domaineManager.getAll();
	}

	public void select(HttpServletRequest iRequest) throws IError {
		selected = null;
		int id = Integer.parseInt(iRequest.getParameter("ID"));
		try {
			selected = domaineManager.get(id);
		} catch (ProjetException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue, le domaine n'a pas pu être chargé",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
	}

	public boolean isInProject(int id) throws ProjetException {
		boolean verif = domaineManager.isInProject(id);

		return verif;
	}

	// --- Getters et Setters --- //

	public List<DomaineTO> getAllDomaine() {
		return allDomaine;
	}

	public void setAllDomaine(List<DomaineTO> allDomaine) {
		this.allDomaine = allDomaine;
	}

	public DomaineTO getSelected() {
		return selected;
	}

	public int getIdDom() {
		return idDom;
	}

	public void setIdDom(int idDom) {
		this.idDom = idDom;
	}

	public String getNomDom() {
		return nomDom;
	}

	public void setNomDom(String nomDom) {
		this.nomDom = nomDom;
	}

	public List<String> getDomCir() {
		return domCir;
	}

}
