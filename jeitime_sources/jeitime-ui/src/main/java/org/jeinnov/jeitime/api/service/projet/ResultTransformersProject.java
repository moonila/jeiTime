package org.jeinnov.jeitime.api.service.projet;

import org.jeinnov.jeitime.api.to.projet.DomaineTO;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.api.to.projet.ThematiqueTO;
import org.jeinnov.jeitime.api.to.projet.TypeProjetTO;
import org.jeinnov.jeitime.api.to.projet.TypeTacheTO;
import org.jeinnov.jeitime.persistence.bo.projet.DomaineP;
import org.jeinnov.jeitime.persistence.bo.projet.NomTacheP;
import org.jeinnov.jeitime.persistence.bo.projet.ProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;
import org.jeinnov.jeitime.persistence.bo.projet.ThematiqueP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeProjetP;
import org.jeinnov.jeitime.persistence.bo.projet.TypeTacheP;


public class ResultTransformersProject {

	public ResultTransformersProject() {
		super();
	}

	public ProjetTO toProjectTO(ProjetP p) {
		ProjetTO projet = new ProjetTO();

		projet.setIdProjet(p.getIdProjet());
		projet.setNomProjet(p.getNomProjet());
		projet.setBudgeprevu(p.getBudgeprevu());
		projet.setDateCloture(p.getDateCloture());
		projet.setDateDeb(p.getDateDeb());
		projet.setDateFermeture(p.getDateFermeture());
		projet.setDateFin(p.getDateFin());
		projet.setTpsprevu(p.getTpsprevu());

		TypeProjetTO typePTO = new TypeProjetTO();
		typePTO.setIdTypeProj(p.getTypeProjet().getIdTypePro());
		typePTO.setNomTypePro(p.getTypeProjet().getNomTypePro());

		projet.setTypeProjet(typePTO);

		if (p.getDomaine() != null) {
			DomaineTO domTO = new DomaineTO();
			domTO.setIdDomaine(p.getDomaine().getIdDomaine());
			domTO.setNomDomaine(p.getDomaine().getNomDomaine());

			projet.setDomaine(domTO);
		}

		if (p.getThematique() != null) {
			ThematiqueTO themTO = new ThematiqueTO();
			themTO.setIdThema(p.getThematique().getIdThematique());
			themTO.setNomThema(p.getThematique().getNomThematique());

			projet.setThematique(themTO);
		}

		if (p.getProjet() != null) {
			ProjetTO proj = new ProjetTO();
			proj.setIdProjet(p.getProjet().getIdProjet());
			proj.setNomProjet(p.getProjet().getNomProjet());

			projet.setProjet(proj);
		}

		return projet;
	}

	public void toProjectP(ProjetTO projet, ProjetP p) {

		if (projet.getIdProjet() != 0) {
			p.setIdProjet(projet.getIdProjet());
		}
		p.setNomProjet(projet.getNomProjet());
		p.setBudgeprevu(projet.getBudgeprevu());
		p.setDateCloture(projet.getDateCloture());
		p.setDateDeb(projet.getDateDeb());
		p.setDateFermeture(projet.getDateFermeture());
		p.setDateFin(projet.getDateFin());
		p.setTpsprevu(projet.getTpsprevu());

		TypeProjetP typePP = new TypeProjetP();
		typePP.setIdTypePro(projet.getTypeProjet().getIdTypeProj());
		typePP.setNomTypePro(projet.getTypeProjet().getNomTypePro());

		p.setTypeProjet(typePP);

		if (projet.getDomaine() != null
				&& projet.getDomaine().getIdDomaine() != 0) {
			DomaineP domP = new DomaineP();
			domP.setIdDomaine(projet.getDomaine().getIdDomaine());
			domP.setNomDomaine(projet.getDomaine().getNomDomaine());

			p.setDomaine(domP);
		}

		if (projet.getThematique() != null
				&& projet.getThematique().getIdThema() != 0) {
			ThematiqueP themP = new ThematiqueP();
			themP.setIdThematique(projet.getThematique().getIdThema());
			themP.setNomThematique(projet.getThematique().getNomThema());

			p.setThematique(themP);
		}

		if (projet.getProjet() != null && projet.getProjet().getIdProjet() != 0) {
			ProjetP projP = new ProjetP();
			projP.setIdProjet(projet.getProjet().getIdProjet());
			projP.setNomProjet(projet.getProjet().getNomProjet());

			p.setProjet(projP);
		}
	}

	public TacheTO toTacheTO(TacheP tacheP) {

		TacheTO tacheTO = new TacheTO();
		tacheTO.setIdTache(tacheP.getIdTache());
		tacheTO.setEligible(tacheP.isEligible());
		tacheTO.setBudgetprevu(tacheP.getBudgetprevu());
		tacheTO.setPriorite(tacheP.getPriorite());
		tacheTO.setTpsprevu(tacheP.getTpsprevu());

		ProjetTO projet = new ProjetTO();
		ProjetP p = tacheP.getProjet();
		projet.setIdProjet(p.getIdProjet());
		projet.setNomProjet(p.getNomProjet());
		tacheTO.setProjet(projet);

		NomTacheTO nomTacheTO = new NomTacheTO();
		NomTacheP nomTacheP = tacheP.getNomTacheP();
		nomTacheTO.setIdNomTache(nomTacheP.getIdNomTache());
		nomTacheTO.setNomTache(nomTacheP.getNomTache());

		TypeTacheTO typeTacheTO = new TypeTacheTO();
		typeTacheTO.setIdTypTach(nomTacheP.getTypeTache().getIdTypeTache());
		typeTacheTO.setNomTypTach(nomTacheP.getTypeTache().getNomTypeTache());
		nomTacheTO.setTypeTache(typeTacheTO);

		tacheTO.setNomtache(nomTacheTO);

		return tacheTO;
	}

	public void toTacheP(TacheTO tacheTO, TacheP tacheP) {

		if (tacheTO.getIdTache() != 0) {
			tacheP.setIdTache(tacheTO.getIdTache());
		}

		tacheP.setEligible(tacheTO.isEligible());
		tacheP.setBudgetprevu(tacheTO.getBudgetprevu());
		tacheP.setPriorite(tacheTO.getPriorite());
		tacheP.setTpsprevu(tacheTO.getTpsprevu());

		ProjetP projet = new ProjetP();
		ProjetTO p = tacheTO.getProjet();
		projet.setIdProjet(p.getIdProjet());
		tacheP.setProjet(projet);

		NomTacheP nomTacheP = new NomTacheP();
		NomTacheTO nomTacheTO = tacheTO.getNomtache();
		nomTacheP.setIdNomTache(nomTacheTO.getIdNomTache());
		nomTacheP.setNomTache(nomTacheTO.getNomTache());

		TypeTacheP typeTacheP = new TypeTacheP();
		typeTacheP.setIdTypeTache(nomTacheTO.getTypeTache().getIdTypTach());
		typeTacheP.setNomTypeTache(nomTacheTO.getTypeTache().getNomTypTach());
		nomTacheP.setTypeTache(typeTacheP);

		tacheP.setNomTacheP(nomTacheP);

	}
}
