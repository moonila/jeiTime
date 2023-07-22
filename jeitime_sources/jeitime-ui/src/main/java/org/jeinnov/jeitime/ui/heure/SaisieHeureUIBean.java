package org.jeinnov.jeitime.ui.heure;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurException;
import org.jeinnov.jeitime.api.service.collaborateur.CollaborateurManager;
import org.jeinnov.jeitime.api.service.heure.GestionHeureManager;
import org.jeinnov.jeitime.api.service.heure.HeureException;
import org.jeinnov.jeitime.api.service.projet.ProjetException;
import org.jeinnov.jeitime.api.service.projet.ProjetManager;
import org.jeinnov.jeitime.api.service.projet.TacheManager;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.heure.GereHeure;
import org.jeinnov.jeitime.api.to.heure.SaisieHeureTO;
import org.jeinnov.jeitime.api.to.projet.NomTacheTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.ow2.opensuit.core.error.IError;
import org.ow2.opensuit.core.error.NonLocalizedError;
import org.ow2.opensuit.core.validation.LocalizedValidationError;
import org.ow2.opensuit.core.validation.ValidationErrors;


public class SaisieHeureUIBean {

	private final Logger logger = Logger.getLogger(this.getClass());

	private GestionHeureManager gestionHeureManager = GestionHeureManager
			.getInstance();

	private CalculDateIntervalle calculDateIntervalle = new CalculDateIntervalle();

	private TacheTO tache;
	private int idTache;
	private List<TacheTO> allTache;
	private String nomTache;

	private CollaborateurTO collab;

	private ProjetTO projet;
	private int idProjet;
	private List<ProjetTO> allProjet;
	private List<ProjetTO> allProjetPrAdmin;
	private String nomProjet;

	private Date date;
	private String nbHeure = "0";
	private String commentaire;

	private String dateMard;
	private String dateMerc;
	private String dateLun;
	private String dateJeud;
	private String dateVend;
	private String dateSem;
	private String mois;
	private int numJour;

	private float nbHeureLundi = 0;
	private float nbHeureMardi = 0;
	private float nbHeureMerc = 0;
	private float nbHeureJeudi = 0;
	private float nbHeureVend = 0;
	private GereHeure ttsHeure;
	private List<GereHeure> tablHeure = new ArrayList<GereHeure>();
	private float total;

	private boolean voisListSaisie = false;

	private String[] tablListSaisie;

	private boolean visible = true;
	private boolean editable = false;

	private int idCollab;

	private SaisieHeureTO saisie;
	//private List<SaisieHeureTO> allSaisie = new ArrayList<SaisieHeureTO>();
	private List<SaisieHeureTO> allHeures = new ArrayList<SaisieHeureTO>();

	private String ttHeure;

	public void load(int idCollab) throws IError, ProjetException, HeureException {
		allProjet = new ArrayList<ProjetTO>();
		ProjetTO aucunProjet = new ProjetTO();
		aucunProjet.setIdProjet(0);
		aucunProjet.setNomProjet("Tous les Projets");
		allProjet = ProjetManager.getInstance().getAllForCollabNotClose(
				idCollab);
		allProjet.add(aucunProjet);
		allHeures = null;
		tablListSaisie = createListSaisie(idCollab);
		date = new Date();
		voirSaisie(idCollab);
	}

	public void loadConsult() {
		allHeures = null;
	}

	public void loadCons(HttpServletRequest iRequest)
			throws CollaborateurException, ProjetException, IError, HeureException {
		allHeures = null;

		idCollab = Integer.parseInt(iRequest.getParameter("id"));
		collab = CollaborateurManager.getInstance().get(idCollab);

		allProjet = new ArrayList<ProjetTO>();
		ProjetTO aucunProjet = new ProjetTO();
		aucunProjet.setIdProjet(0);
		aucunProjet.setNomProjet("Tous les Projets");
		allProjet = ProjetManager.getInstance()
				.getAllForCollabNotLock(idCollab);
		allProjet.add(aucunProjet);

		nbHeure = "0";
		allHeures = null;
		tablListSaisie = createListSaisie(idCollab);
		date = new Date();
		voirSaisie(idCollab);
	}

	public void create(int idCollab) throws CollaborateurException,
			ProjetException, HeureException, IError {
		collab = CollaborateurManager.getInstance().get(idCollab);
		selectSaisie();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int numJ = cal.get(Calendar.DAY_OF_WEEK);

		if (saisie != null && saisie.getNbHeure() != 0) {
			if (numJ != 1 && numJ != 7) {
				try {
					gestionHeureManager.saveOrUpdate(saisie);
				} catch (HeureException e) {
					NonLocalizedError error = new NonLocalizedError(
							"Attention : ",
							"Soit le collaborateur, la date ou la tâche ne sont pas correctement spécifiés. "
									+ "La saise n'a pas pu être sauvegardée. ",
							e);
					error.setType(IError.FUNCTIONAL_ERROR);
					throw error;
				}
			}
			if (numJ == 1 || numJ == 7) {
				NonLocalizedError error = new NonLocalizedError(
						"Attention saisie sur un samedi ou un dimanche",
						"Il vous est impossible de saisir sur un samedi ou un dimanche ");
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
		}
		voirSaisie(idCollab);
		refresh();
	}

	public void update(int idColl, Date dateJ) throws IError, HeureException {
		TacheTO taches = new TacheTO(idTache);
		ProjetTO proj = new ProjetTO(idProjet);
		CollaborateurTO coll = new CollaborateurTO(idColl);
		float heure = parseHeure();

		saisie = new SaisieHeureTO(taches, proj, coll, date, heure, commentaire);

		if (heure == 0) {
			try {
				gestionHeureManager.delete(saisie);
			} catch (HeureException e) {
				NonLocalizedError error = new NonLocalizedError(
						"Attention : ",
						"Soit le collaborateur, la date ou la tâche ne sont pas correctement spécifiés. "
								+ "La saise n'a pas pu être sauvegardée. ", e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
		} else {
			try {
				gestionHeureManager.saveOrUpdate(saisie);
			} catch (HeureException e) {
				NonLocalizedError error = new NonLocalizedError(
						"Attention : ",
						"Soit le collaborateur, la date ou la tâche ne sont pas correctement spécifiés. "
								+ "La saise n'a pas pu être sauvegardée. ", e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
		}

		// voirSaisie(idCollab);
		visible = true;
		voirSaisie(idColl, dateJ);
		refresh();
	}

	public void cancel() {
		visible = true;
		nbHeure = "0";
		idTache = 0;
		idProjet = 0;
		tache = null;
		projet = null;
		saisie = null;
		//allSaisie = new ArrayList<SaisieHeureTO>();
		commentaire = null;
	}

	public void refresh() {
		nbHeure = "0";
		idTache = 0;
		idProjet = 0;
		tache = null;
		projet = new ProjetTO();
		saisie = null;
		//allSaisie = new ArrayList<SaisieHeureTO>();
		allTache = null;
		commentaire = null;
	}

	public void selectProjet(int idColl) throws IError {
		nbHeure = "0";
		if (idProjet == 0) {
			allTache = null;
		} else {
			try {
				allTache = TacheManager.getInstance()
						.getAllInProjectAndForACollaborateur(idProjet, idColl);
			} catch (ProjetException e) {
				NonLocalizedError error = new NonLocalizedError(
						"Attention : ",
						"Une erreur est survenue. Le projet ou le collaborateur ne sont pas correctement spécifiés",
						e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
			if (allTache == null || allTache.isEmpty()) {
				TacheTO t = new TacheTO();
				t.setIdTache(0);
				t.setNomtache(new NomTacheTO(0,
						"aucune tâche n'est rattachée à ce collaborateur"));
				allTache.add(t);
			}
		}
	}

	public Float getHeures(float v) {
		if (v == 0) {
			return null;
		}
		return v;
	}

	public void voirSaisie(int idColl, Date dateD) throws IError,
			HeureException {
		date = dateD;
		voirSaisie(idColl);
	}

	public void voirSaisie(int idColl) throws NonLocalizedError, HeureException {
		try {
			allHeures = gestionHeureManager.getAllByIdCollaborateurAndDate(
					idColl, date);
		} catch (HeureException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Aucune saisie n'est spécifiée", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		tablHeure = new ArrayList<GereHeure>();

		List<GereHeure> tablHeureTmp = new ArrayList<GereHeure>();
		if (allHeures != null) {
			for (int i = 0; i < allHeures.size(); i++) {
				createTtsHeure(i);
				tablHeureTmp.add(ttsHeure);
				Collections.sort(tablHeureTmp);
			}
		} else {
			allHeures = null;
		}
		tablHeure = gestionHeureManager.fusionElemTablHeure(tablHeureTmp);
		dateIntervalle();
	}

	public void afficheTacheSaisie(HttpServletRequest iRequest, int idColl,
			String dateJour) throws IError {
		idTache = Integer.parseInt(iRequest.getParameter("id"));
		Date dateJ = null;
		SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yy");
		try {
			dateJ = dateformat.parse(dateJour);
		} catch (Exception pe) {
			logger.error(pe.getMessage(), pe);
		}
		try {
			saisie = gestionHeureManager.getByIdCollaborateurAndIdTacheAndDate(
					idTache, idColl, dateJ);
		} catch (HeureException e) {
			NonLocalizedError error = new NonLocalizedError(
					"Attention : ",
					"Une erreur est survenue. La tâche ou le collaborateur ne sont pas correctement spécifiés",
					e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		if (saisie != null) {
			idProjet = saisie.getProjet().getIdProjet();
			nomProjet = saisie.getProjet().getNomProjet();
			try {
				allTache = TacheManager.getInstance().getAllTacheInProject(
						idProjet);
			} catch (ProjetException e) {
				NonLocalizedError error = new NonLocalizedError(
						"Attention : ",
						"Une erreur est survenue. le projet n'est peut être pas correctement spécifié",
						e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
			idTache = saisie.getTache().getIdTache();
			nomTache = saisie.getTache().getNomtache().getNomTache();
			nbHeure = String.valueOf(saisie.getNbHeure());
			commentaire = saisie.getCommentaire();
			date = saisie.getSaisiDate();
			afficheJour();
			visible = false;
		} else {
			visible = true;
		}
	}

	public void voirSemSuiv(int idColl, Date dateLun) throws IError,
			HeureException {

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateLun);

		// int numPremJour = cal.get(Calendar.DAY_OF_WEEK);
		int intLun = cal.get(Calendar.DAY_OF_MONTH) + 7; // Dimanche
		cal.set(Calendar.DAY_OF_MONTH, intLun);
		Date d = cal.getTime();

		voirSaisie(idColl, d);
	}

	public void voirSemPrec(int idColl, Date dateLun) throws IError,
			HeureException {

		Calendar cal = Calendar.getInstance();
		cal.setTime(dateLun);

		int intLun = cal.get(Calendar.DAY_OF_MONTH) - 7; // Dimanche
		cal.set(Calendar.DAY_OF_MONTH, intLun);
		Date d = cal.getTime();

		voirSaisie(idColl, d);
	}

	public boolean peutModifier(TacheTO tache, float heures)
			throws ProjetException {
		boolean verifProjet = false;
		if (heures == 0) {
			return false;
		}
		if (tache.getIdTache() != 0) {
			ProjetTO projetP = ProjetManager.getInstance().getProjectNotClose(
					tache.getProjet().getIdProjet());

			if (projetP.getDateFermeture() == null) {
				verifProjet = true;
			} else {
				verifProjet = false;
			}
		} else {
			verifProjet = false;
		}

		return verifProjet;
	}

	public boolean peutModifierC(TacheTO tache, float heures)
			throws ProjetException {
		boolean verifProjet = false;
		if (heures == 0) {
			return false;
		}
		if (tache.getIdTache() != 0) {
			ProjetTO projetP = ProjetManager.getInstance().getProjectNotLock(
					tache.getProjet().getIdProjet());
			if (projetP.getDateCloture() == null) {
				verifProjet = true;
			} else {
				verifProjet = false;
			}

		} else {
			verifProjet = false;
		}
		return verifProjet;
	}

	public boolean projetFerme(int idTache) throws ProjetException {
		boolean verifProjet = false;
		if (tache.getIdTache() != 0) {
			ProjetTO projetP = ProjetManager.getInstance().getProjectNotClose(
					tache.getProjet().getIdProjet());

			if (projetP.getDateFermeture() == null) {
				verifProjet = true;
			} else {
				verifProjet = false;
			}
		} else {
			verifProjet = false;
		}
		return verifProjet;
	}

	public String verifNbHeureSaisie(int idCollab) throws IError,
			HeureException, ProjetException {
		selectSaisie();
		String result = null;
		try {
			collab = CollaborateurManager.getInstance().get(idCollab);
		} catch (CollaborateurException e) {
			NonLocalizedError error = new NonLocalizedError("Attention : ",
					"Aucun collaborateur n'est sélectionné", e);
			error.setType(IError.FUNCTIONAL_ERROR);
			throw error;
		}
		Calendar cal = Calendar.getInstance();

		int numJ = 0;

		cal.setTime(date);
		numJ = cal.get(Calendar.DAY_OF_WEEK);
		float heure = parseHeure();
		if (heure != 0) {

			float heureTot = gestionHeureManager
					.getResultByIdCollaborateurAndDate(idCollab, date);
			heureTot = heureTot + heure;
			if (numJ == 2) {
				if (heure > collab.getNbHeureLundi()
						|| heureTot > collab.getNbHeureLundi()) {
					result = "DepassHeure";
				} else {
					result = "DepassPasHeure";
				}
			}
			if (numJ == 3) {
				if (heure > collab.getNbHeureMardi()
						|| heureTot > collab.getNbHeureMardi()) {
					result = "DepassHeure";
				} else {
					result = "DepassPasHeure";
				}
			}
			if (numJ == 4) {
				if (heure > collab.getNbHeureMercredi()
						|| heureTot > collab.getNbHeureMercredi()) {
					result = "DepassHeure";
				} else {
					result = "DepassPasHeure";
				}
			}
			if (numJ == 5) { 
				if (heure > collab.getNbHeureJeudi()
						|| heureTot > collab.getNbHeureJeudi()) {
					result = "DepassHeure";
				} else {
					result = "DepassPasHeure";
				}
			}
			if (numJ == 6) {
				if (heure > collab.getNbHeureVendredi()
						|| heureTot > collab.getNbHeureVendredi()) {
					result = "DepassHeure";
				} else {
					result = "DepassPasHeure";
				}
			}
			if (numJ == 1) {
				result = "DepassPasHeure";
			}
			if (numJ == 7) {
				result = "DepassPasHeure";
			}
		} else {
			result = "DepassPasHeure";
		}
		return result;
	}
	
	public void validate() throws ValidationErrors {
		ValidationErrors errors = new ValidationErrors();
		if (idProjet ==0 && !nbHeure.equalsIgnoreCase("0")) {
			errors.addItemError("saisieHeureBean.idProjet",
					new LocalizedValidationError("validation.projetSaisie"));
		}
		if(idProjet !=0 && idTache ==0){
			errors.addItemError("saisieHeureBean.idTache",
					new LocalizedValidationError("validation.tacheSaisie"));
		}
		if (nbHeure ==null || nbHeure=="" || nbHeure==" "){
			errors.addItemError("saisieHeureBean.nbHeure",
					new LocalizedValidationError("validation.heureSaisie"));
		}
		if (errors.hasErrors()) {
			throw errors;
		}
		
	}
	

	/*********************
	 *Privates methods
	 * 
	 * @throws ProjetException
	 * @throws IError 
	 * 
	 ******************/
	private void selectSaisie() throws ProjetException, IError {
		float heure = parseHeure();

		String nomprojet = null;


		if (idTache != 0 && idProjet != 0 && heure != 0) {
			tache = TacheManager.getInstance().get(idTache);
			NomTacheTO nomTacheTO = tache.getNomtache();
			tache = new TacheTO(idTache, nomTacheTO);

			projet = ProjetManager.getInstance().get(idProjet);
			nomprojet = projet.getNomProjet();
			projet = new ProjetTO(idProjet, nomprojet);

			saisie = new SaisieHeureTO(tache, projet, collab, date, heure,
					commentaire);
		}
		afficheJour();
	}

	private void afficheJour() {
		CalculDateIntervalle calDate = new CalculDateIntervalle();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		int numJourSem = cal.get(Calendar.DAY_OF_WEEK);
		numJour = cal.get(Calendar.DAY_OF_MONTH);

		dateSem = calDate.trouveJourSemaine(numJourSem);

		int numMois = cal.get(Calendar.MONTH);
		mois = calDate.trouveMois(numMois);
		
	}

	private String[] createListSaisie(int idC) throws NonLocalizedError {
		String[] listSaisie = null;

		listSaisie = gestionHeureManager
				.listSaisieVerifCollege(idC, listSaisie);
		if (listSaisie != null) {
			voisListSaisie = true;
		} else {
			voisListSaisie = false;
			String[] tablListSaisieTmp = new String[2];
			tablListSaisieTmp[0] = "Il n'y a pas de données pour le tableau";
			tablListSaisieTmp[1] = "Aucune info";
			tablListSaisie = tablListSaisieTmp;
		}
		return listSaisie;
	}

	private void dateIntervalle() {
		Locale locale = Locale.FRANCE;
		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT,
				locale);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int numPremJour = cal.get(Calendar.DAY_OF_WEEK);
		switch (numPremJour) {
		case 2:
			dateIntervalleLundi(dateFormat, cal);
			break;
		case 3:
			dateIntervalleMardi(dateFormat, cal);
			break;
		case 4:
			dateIntervalleMercredi(dateFormat, cal);
			break;
		case 5:
			dateIntervalleJeudi(dateFormat, cal);
			break;
		case 6:
			dateIntervalleVendredi(dateFormat, cal);
			break;
		case 7:
			dateIntervalleSamedi(dateFormat, cal);
			break;
		case 1:
			dateIntervalleDimanche(dateFormat, cal);
			break;
		}
	}

	private void dateIntervalleDimanche(DateFormat dateFormat, Calendar cal) {
		dateLun = calculDateIntervalle.dateIntervalleMoins6(dateFormat, cal,
				date);
		dateMard = calculDateIntervalle.dateIntervalleMoins5(dateFormat, cal,
				date);
		dateMerc = calculDateIntervalle.dateIntervalleMoins4(dateFormat, cal,
				date);
		dateJeud = calculDateIntervalle.dateIntervalleMoins3(dateFormat, cal,
				date);
		dateVend = calculDateIntervalle.dateIntervalleMoins2(dateFormat, cal,
				date);
	}

	private void dateIntervalleSamedi(DateFormat dateFormat, Calendar cal) {
		dateLun = calculDateIntervalle.dateIntervalleMoins5(dateFormat, cal,
				date);
		dateMard = calculDateIntervalle.dateIntervalleMoins4(dateFormat, cal,
				date);
		dateMerc = calculDateIntervalle.dateIntervalleMoins3(dateFormat, cal,
				date);
		dateJeud = calculDateIntervalle.dateIntervalleMoins2(dateFormat, cal,
				date);
		dateVend = calculDateIntervalle.dateIntervalleMoins1(dateFormat, cal,
				date);
	}

	private void dateIntervalleVendredi(DateFormat dateFormat, Calendar cal) {
		dateLun = calculDateIntervalle.dateIntervalleMoins4(dateFormat, cal,
				date);
		dateMard = calculDateIntervalle.dateIntervalleMoins3(dateFormat, cal,
				date);
		dateMerc = calculDateIntervalle.dateIntervalleMoins2(dateFormat, cal,
				date);
		dateJeud = calculDateIntervalle.dateIntervalleMoins1(dateFormat, cal,
				date);
		dateVend = dateFormat.format(date);
	}

	private void dateIntervalleJeudi(DateFormat dateFormat, Calendar cal) {
		dateLun = calculDateIntervalle.dateIntervalleMoins3(dateFormat, cal,
				date);
		dateMard = calculDateIntervalle.dateIntervalleMoins2(dateFormat, cal,
				date);
		dateMerc = calculDateIntervalle.dateIntervalleMoins1(dateFormat, cal,
				date);
		dateJeud = dateFormat.format(date);
		dateVend = calculDateIntervalle.dateIntervallePlus1(dateFormat, cal,
				date);
	}

	private void dateIntervalleMercredi(DateFormat dateFormat, Calendar cal) {
		dateLun = calculDateIntervalle.dateIntervalleMoins2(dateFormat, cal,
				date);
		dateMard = calculDateIntervalle.dateIntervalleMoins1(dateFormat, cal,
				date);
		dateMerc = dateFormat.format(date);
		dateJeud = calculDateIntervalle.dateIntervallePlus1(dateFormat, cal,
				date);
		dateVend = calculDateIntervalle.dateIntervallePlus2(dateFormat, cal,
				date);
	}

	private void dateIntervalleMardi(DateFormat dateFormat, Calendar cal) {
		dateLun = calculDateIntervalle.dateIntervalleMoins1(dateFormat, cal,
				date);
		dateMard = dateFormat.format(date);
		dateMerc = calculDateIntervalle.dateIntervallePlus1(dateFormat, cal,
				date);
		dateJeud = calculDateIntervalle.dateIntervallePlus2(dateFormat, cal,
				date);
		dateVend = calculDateIntervalle.dateIntervallePlus3(dateFormat, cal,
				date);
	}

	private void dateIntervalleLundi(DateFormat dateFormat, Calendar cal) {
		dateLun = dateFormat.format(date);
		dateMard = calculDateIntervalle.dateIntervallePlus1(dateFormat, cal,
				date);
		dateMerc = calculDateIntervalle.dateIntervallePlus2(dateFormat, cal,
				date);
		dateJeud = calculDateIntervalle.dateIntervallePlus3(dateFormat, cal,
				date);
		dateVend = calculDateIntervalle.dateIntervallePlus4(dateFormat, cal,
				date);
	}

	private float parseHeure() throws IError {
		float heure = 0;
		try {
			heure = Float.parseFloat(nbHeure);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				NumberFormat nf = NumberFormat.getInstance(Locale.FRANCE);
				heure = nf.parse(nbHeure).floatValue();
			} catch (ParseException pe) {
//				logger.error(pe.getMessage(), pe);
				NonLocalizedError error = new NonLocalizedError(
						"Attention : ",
						"La valeur saisie pour le nombre d'heure doit être numérique.",
						e);
				error.setType(IError.FUNCTIONAL_ERROR);
				throw error;
			}
		}
		return heure;
	}

	private void createTtsHeure(int i) {
		Date dateS;
		dateS = allHeures.get(i).getSaisiDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(dateS);

		int numJourTmp = cal.get(Calendar.DAY_OF_WEEK);

		float nbrHeure = allHeures.get(i).getNbHeure();
		String nomT = allHeures.get(i).getTache().getNomtache().getNomTache();
		TacheTO tacheTO = allHeures.get(i).getTache();

		String nomP = allHeures.get(i).getProjet().getNomProjet();
		int idTaches = allHeures.get(i).getTache().getIdTache();

		determineNbheureJour(numJourTmp, nbrHeure);

		float totalTmp = nbHeureLundi + nbHeureMardi + nbHeureMerc
				+ nbHeureJeudi + nbHeureVend;
		ttsHeure = new GereHeure(nbHeureLundi, nbHeureMardi, nbHeureMerc,
				nbHeureJeudi, nbHeureVend, totalTmp, nomT, nomP, idTaches);
		ttsHeure.setTache(tacheTO);
	}

	private void determineNbheureJour(int numJour, float nbrHeure) {
		switch (numJour) {
		case 2:
			nbHeureLundi = nbrHeure;
			nbHeureMardi = 0;
			nbHeureMerc = 0;
			nbHeureJeudi = 0;
			nbHeureVend = 0;
			break;
		case 3:
			nbHeureLundi = 0;
			nbHeureMardi = nbrHeure;
			nbHeureMerc = 0;
			nbHeureJeudi = 0;
			nbHeureVend = 0;
			break;
		case 4:
			nbHeureLundi = 0;
			nbHeureMardi = 0;
			nbHeureMerc = nbrHeure;
			nbHeureJeudi = 0;
			nbHeureVend = 0;
			break;
		case 5:
			nbHeureLundi = 0;
			nbHeureMardi = 0;
			nbHeureMerc = 0;
			nbHeureJeudi = nbrHeure;
			nbHeureVend = 0;
			break;
		case 6:
			nbHeureLundi = 0;
			nbHeureMardi = 0;
			nbHeureMerc = 0;
			nbHeureJeudi = 0;
			nbHeureVend = nbrHeure;
			break;
		}
	}

	/*********************
	 *Getters and Setters *
	 ******************/

	public TacheTO getTache() {
		return tache;
	}

	public void setTache(TacheTO tache) {
		this.tache = tache;
	}

	public CollaborateurTO getCollab() {
		return collab;
	}

	public void setCollab(CollaborateurTO collab) {
		this.collab = collab;
	}

	public ProjetTO getProjet() {
		return projet;
	}

	public void setProjet(ProjetTO projet) {
		this.projet = projet;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getNbHeure() {
		return nbHeure;
	}

	public void setNbHeure(String nbHeure) {
		this.nbHeure = nbHeure;
	}

	public String getCommentaire() {
		return commentaire;
	}

	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	public int getIdProjet() {
		return idProjet;
	}

	public void setIdProjet(int idProjet) {
		this.idProjet = idProjet;
	}

	public List<ProjetTO> getAllProjet() {
		return allProjet;
	}

	public void setAllProjet(List<ProjetTO> allProjet) {
		this.allProjet = allProjet;
	}

	public int getIdTache() {
		return idTache;
	}

	public void setIdTache(int idTache) {
		this.idTache = idTache;
	}

	public List<TacheTO> getAllTache() {
		return allTache;
	}

	public void setAllTache(List<TacheTO> allTache) {
		this.allTache = allTache;
	}

	public SaisieHeureTO getSaisie() {
		return saisie;
	}

	public void setSaisie(SaisieHeureTO saisie) {
		this.saisie = saisie;
	}

//	public List<SaisieHeureTO> getAllSaisie() {
//		return allSaisie;
//	}
//
//	public void setAllSaisie(List<SaisieHeureTO> allSaisie) {
//		this.allSaisie = allSaisie;
//	}

	public String getTtHeure() {
		return ttHeure;
	}

	public void setTtHeure(String ttHeure) {
		this.ttHeure = ttHeure;
	}

	public float getNbHeureLundi() {
		return nbHeureLundi;
	}

	public void setNbHeureLundi(float nbHeureLundi) {
		this.nbHeureLundi = nbHeureLundi;
	}

	public float getNbHeureMardi() {
		return nbHeureMardi;
	}

	public void setNbHeureMardi(float nbHeureMardi) {
		this.nbHeureMardi = nbHeureMardi;
	}

	public float getNbHeureMerc() {
		return nbHeureMerc;
	}

	public void setNbHeureMerc(float nbHeureMerc) {
		this.nbHeureMerc = nbHeureMerc;
	}

	public float getNbHeureJeudi() {
		return nbHeureJeudi;
	}

	public void setNbHeureJeudi(float nbHeureJeudi) {
		this.nbHeureJeudi = nbHeureJeudi;
	}

	public float getNbHeureVend() {
		return nbHeureVend;
	}

	public void setNbHeureVend(float nbHeureVend) {
		this.nbHeureVend = nbHeureVend;
	}

	public GereHeure getTtsHeure() {
		return ttsHeure;
	}

	public void setTtsHeure(GereHeure ttsHeure) {
		this.ttsHeure = ttsHeure;
	}

	public List<GereHeure> getTablHeure() {
		return tablHeure;
	}

	public void setTablHeure(List<GereHeure> tablHeure) {
		this.tablHeure = tablHeure;
	}

	public float getTotal() {

		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public List<SaisieHeureTO> getAllHeures() {
		return allHeures;
	}

	public void setAllHeures(List<SaisieHeureTO> allHeures) {
		this.allHeures = allHeures;
	}

	public String getDateLun() {
		return dateLun;
	}

	public void setDateLun(String dateLun) {
		this.dateLun = dateLun;
	}

	public String getDateMard() {
		return dateMard;
	}

	public void setDateMard(String dateMard) {
		this.dateMard = dateMard;
	}

	public String getDateMerc() {
		return dateMerc;
	}

	public void setDateMerc(String dateMerc) {
		this.dateMerc = dateMerc;
	}

	public String getDateJeud() {
		return dateJeud;
	}

	public void setDateJeud(String dateJeud) {
		this.dateJeud = dateJeud;
	}

	public String getDateVend() {
		return dateVend;
	}

	public void setDateVend(String dateVend) {
		this.dateVend = dateVend;
	}

	public String getDateSem() {
		return dateSem;
	}

	public void setDateSem(String dateSem) {
		this.dateSem = dateSem;
	}

	public int getNumJour() {
		return numJour;
	}

	public void setNumJour(int numJour) {
		this.numJour = numJour;
	}

	public String getMois() {
		return mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	public String[] getTablListSaisie() {
		return tablListSaisie;
	}

	public void setTablListSaisie(String[] tablListSaisie) {
		this.tablListSaisie = tablListSaisie;
	}

	public boolean isVoisListSaisie() {
		return voisListSaisie;
	}

	public void setVoisListSaisie(boolean voisListSaisie) {
		this.voisListSaisie = voisListSaisie;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getNomTache() {
		return nomTache;
	}

	public void setNomTache(String nomTache) {
		this.nomTache = nomTache;
	}

	public String getNomProjet() {
		return nomProjet;
	}

	public void setNomProjet(String nomProjet) {
		this.nomProjet = nomProjet;
	}

	public boolean isEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public int getIdCollab() {
		return idCollab;
	}

	public void setIdCollab(int idCollab) {
		this.idCollab = idCollab;
	}

	public List<ProjetTO> getAllProjetPrAdmin() {
		return allProjetPrAdmin;
	}

	public void setAllProjetPrAdmin(List<ProjetTO> allProjetPrAdmin) {
		this.allProjetPrAdmin = allProjetPrAdmin;
	}

}
