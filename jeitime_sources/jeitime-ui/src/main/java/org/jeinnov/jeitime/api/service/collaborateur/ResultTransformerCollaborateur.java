package org.jeinnov.jeitime.api.service.collaborateur;

import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.collaborateur.CollegeTO;
import org.jeinnov.jeitime.api.to.collaborateur.EquipeTO;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollaborateurP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.CollegeP;
import org.jeinnov.jeitime.persistence.bo.collaborateur.EquipeP;


public class ResultTransformerCollaborateur

{
	public ResultTransformerCollaborateur() {

	}

	public CollaborateurTO toCollaborateurTO(CollaborateurP collabP) {

		CollaborateurTO collaborateur = new CollaborateurTO();

		collaborateur.setIdColl(collabP.getIdColl());
		collaborateur.setLogin(collabP.getLogin());
		collaborateur.setPassword(collabP.getPassword());
		collaborateur.setNomColl(collabP.getNomColl());
		collaborateur.setPrenomColl(collabP.getPrenomColl());
		collaborateur.setStatut(collabP.getStatut());
		collaborateur.setSalairAnn(collabP.getSalairAnn());
		collaborateur.setChargeAnn(collabP.getChargeAnn());
		collaborateur.setNbHeureLundi(collabP.getNbHeureLundi());
		collaborateur.setNbHeureMardi(collabP.getNbHeureMardi());
		collaborateur.setNbHeureMercredi(collabP.getNbHeureMercredi());
		collaborateur.setNbHeureJeudi(collabP.getNbHeureJeudi());
		collaborateur.setNbHeureVendredi(collabP.getNbHeureVendredi());
		collaborateur.setNbHeureHeb(collabP.getNbHeureHeb());
		collaborateur.setNbHeureMens(collabP.getNbHeureMens());
		collaborateur.setNbHeureAnn(collabP.getNbHeureAnn());
		collaborateur.setContrat(collabP.getContrat());

		EquipeTO equipe = new EquipeTO();

		if (collabP.getEquipe() != null) {
			equipe.setIdEquip(collabP.getEquipe().getIdEquip());
			equipe.setNomEquip(collabP.getEquipe().getNomEquip());
			equipe.setFonctionEquip(collabP.getEquipe().getFonctionEquip());

		}

		collaborateur.setEquipe(equipe);

		return collaborateur;
	}

	public void toCollaborateurP(CollaborateurTO collaborateur, CollaborateurP collabP) {
		
		if(collaborateur.getIdColl() !=0){
			collabP.setIdColl(collaborateur.getIdColl());
		}	
		collabP.setLogin(collaborateur.getLogin());
		collabP.setPassword(collaborateur.getPassword());
		collabP.setNomColl(collaborateur.getNomColl());
		collabP.setPrenomColl(collaborateur.getPrenomColl());
		collabP.setStatut(collaborateur.getStatut());
		collabP.setSalairAnn(collaborateur.getSalairAnn());
		collabP.setChargeAnn(collaborateur.getChargeAnn());
		collabP.setNbHeureLundi(collaborateur.getNbHeureLundi());
		collabP.setNbHeureMardi(collaborateur.getNbHeureMardi());
		collabP.setNbHeureMercredi(collaborateur.getNbHeureMercredi());
		collabP.setNbHeureJeudi(collaborateur.getNbHeureJeudi());
		collabP.setNbHeureVendredi(collaborateur.getNbHeureVendredi());
		collabP.setNbHeureHeb(collaborateur.getNbHeureHeb());
		collabP.setNbHeureMens(collaborateur.getNbHeureMens());
		collabP.setNbHeureAnn(collaborateur.getNbHeureAnn());
		collabP.setContrat(collaborateur.getContrat());

		EquipeP equipe = new EquipeP();

		if (collaborateur.getEquipe()!=null && collaborateur.getEquipe().getIdEquip() != 0) {
			equipe.setIdEquip(collaborateur.getEquipe().getIdEquip());
			equipe.setNomEquip(collaborateur.getEquipe().getNomEquip());
			equipe.setFonctionEquip(collaborateur.getEquipe()
					.getFonctionEquip());
		} else {
			equipe = null;
		}

		collabP.setEquipe(equipe);
	}

	public CollegeTO toCollegeTO(CollegeP college) {

		CollegeTO collegeTO = new CollegeTO();

		if (college.getIdCollege() != 0) {
			collegeTO.setIdCollege(college.getIdCollege());
		}

		boolean alertHeb = college.isAlertJour();
		boolean alertMens = college.isAlertMois();
		boolean alertAnn = college.isAlertAnn();

		String listSaisie = college.getListSaisie();
		if (listSaisie == null) {
			listSaisie = null;
		}

		collegeTO.setNomCollege(college.getNomCollege());
		collegeTO.setNbHeureLun(college.getNbHeureLun());
		collegeTO.setNbHeureMar(college.getNbHeureMar());
		collegeTO.setNbHeureMerc(college.getNbHeureMerc());
		collegeTO.setNbHeureJeu(college.getNbHeureJeud());
		collegeTO.setNbHeureVen(college.getNbHeureVend());
		collegeTO.setNbHeureHeb(college.getNbHeureLun()
				+ college.getNbHeureMar() + college.getNbHeureMerc()
				+ college.getNbHeureJeud() + college.getNbHeureVend());
		collegeTO.setNbHeureMensCollege(college.getNbHeureMensCollege());
		collegeTO.setNbHeureAnnCollege(college.getNbHeureAnnCollege());
		collegeTO.setNbJourCongeAnnCollege(college.getNbJourCongeAnnCollege());
		collegeTO.setNbJourRttAnnCollege(college.getNbJourRttAnnCollege());
		collegeTO.setAlertAnn(alertAnn);
		collegeTO.setAlertJour(alertHeb);
		collegeTO.setAlertMois(alertMens);
		collegeTO.setListSaisie(listSaisie);

		return collegeTO;
	}

	public void toCollegeP(CollegeTO collegeTO, CollegeP college) {


		if (collegeTO.getIdCollege() != 0) {
			college.setIdCollege(collegeTO.getIdCollege());
		}

		boolean alertHeb = collegeTO.isAlertJour();
		boolean alertMens = collegeTO.isAlertMois();
		boolean alertAnn = collegeTO.isAlertAnn();

		String listSaisie = collegeTO.getListSaisie();
		if (listSaisie == null) {
			listSaisie = null;
		}

		college.setNomCollege(collegeTO.getNomCollege());
		college.setNbHeureLun(collegeTO.getNbHeureLun());
		college.setNbHeureMar(collegeTO.getNbHeureMar());
		college.setNbHeureMerc(collegeTO.getNbHeureMerc());
		college.setNbHeureJeud(collegeTO.getNbHeureJeu());
		college.setNbHeureVend(collegeTO.getNbHeureVen());
		college.setNbHeureHebdo(collegeTO.getNbHeureLun()
				+ collegeTO.getNbHeureMar() + collegeTO.getNbHeureMerc()
				+ collegeTO.getNbHeureJeu() + collegeTO.getNbHeureVen());
		college.setNbHeureMensCollege(collegeTO.getNbHeureMensCollege());
		college.setNbHeureAnnCollege(collegeTO.getNbHeureAnnCollege());
		college.setNbJourCongeAnnCollege(collegeTO.getNbJourCongeAnnCollege());
		college.setNbJourRttAnnCollege(collegeTO.getNbJourRttAnnCollege());
		college.setAlertAnn(alertAnn);
		college.setAlertJour(alertHeb);
		college.setAlertMois(alertMens);
		college.setListSaisie(listSaisie);

	}
}
