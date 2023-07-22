package org.jeinnov.jeitime.ui.collaborateur;

import java.text.NumberFormat;
import java.text.ParseException;

import org.apache.log4j.Logger;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.collaborateur.CollegeTO;


public class ResultTransformerUIBean {
	private final Logger logger = Logger.getLogger(this.getClass());

	public ResultTransformerUIBean() {
		super();

	}

	public String floatToStringLundi(Object o) {

		String strNbHeureLundi = null;
		if (o instanceof CollegeTO) {
			strNbHeureLundi = String.valueOf(((CollegeTO) o).getNbHeureLun());
		} else {
			strNbHeureLundi = String.valueOf(((CollaborateurTO) o)
					.getNbHeureLundi());
		}

		return strNbHeureLundi;
	}

	public String floatToStringMardi(Object o) {

		String strNbHeureMardi = null;
		if (o instanceof CollegeTO) {
			strNbHeureMardi = String.valueOf(((CollegeTO) o).getNbHeureMar());
		} else {
			strNbHeureMardi = String.valueOf(((CollaborateurTO) o)
					.getNbHeureMardi());
		}

		return strNbHeureMardi;

	}

	public String floatToStringMercredi(Object o) {

		String strNbHeureMercredi = null;
		if (o instanceof CollegeTO) {
			strNbHeureMercredi = String.valueOf(((CollegeTO) o)
					.getNbHeureMerc());
		} else {
			strNbHeureMercredi = String.valueOf(((CollaborateurTO) o)
					.getNbHeureMercredi());
		}

		return strNbHeureMercredi;
	}

	public String floatToStringJeudi(Object o) {
		String strNbHeureJeudi = null;
		if (o instanceof CollegeTO) {
			strNbHeureJeudi = String.valueOf(((CollegeTO) o).getNbHeureJeu());
		} else {
			strNbHeureJeudi = String.valueOf(((CollaborateurTO) o)
					.getNbHeureJeudi());
		}

		return strNbHeureJeudi;
	}

	public String floatToStringVendredi(Object o) {

		String strNbHeureVendredi = null;
		if (o instanceof CollegeTO) {
			strNbHeureVendredi = String
					.valueOf(((CollegeTO) o).getNbHeureVen());
		} else {
			strNbHeureVendredi = String.valueOf(((CollaborateurTO) o)
					.getNbHeureVendredi());
		}

		return strNbHeureVendredi;
	}

	public String floatToStringAnnuel(Object o) {

		String strNbHeureAnnuel = null;
		if (o instanceof CollegeTO) {
			strNbHeureAnnuel = String.valueOf(((CollegeTO) o)
					.getNbHeureAnnCollege());
		} else {
			strNbHeureAnnuel = String.valueOf(((CollaborateurTO) o)
					.getNbHeureAnn());
		}

		return strNbHeureAnnuel;
	}

	public String floatToStringHebdo(Object o) {

		String strNbHeureHeb = null;
		if (o instanceof CollegeTO) {
			strNbHeureHeb = String.valueOf(((CollegeTO) o).getNbHeureHeb());
		} else {
			strNbHeureHeb = String.valueOf(((CollaborateurTO) o)
					.getNbHeureHeb());
		}

		return strNbHeureHeb;
	}

	public String floatToStringMensuel(Object o) {

		String strNbHeureMens = null;
		if (o instanceof CollegeTO) {
			strNbHeureMens = String.valueOf(((CollegeTO) o)
					.getNbHeureMensCollege());
		} else {
			strNbHeureMens = String.valueOf(((CollaborateurTO) o)
					.getNbHeureMens());
		}
		return strNbHeureMens;
	}

	public float stringToFloatSalaire(NumberFormat nf, String salaireColl) {
		float salaire = 0;
		try {
			salaire = Float.parseFloat(salaireColl);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				salaire = nf.parse(salaireColl).floatValue();
			} catch (ParseException pe) {
				logger.error(pe.getMessage(), pe);
			}
		}
		return salaire;
	}

	public float stringToFloatChargeCollab(NumberFormat nf, String chargeColl) {
		float charge = 0;
		try {
			charge = Float.parseFloat(chargeColl);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				charge = nf.parse(chargeColl).floatValue();
			} catch (ParseException pe) {
				logger.error(pe.getMessage(), pe);
			}
		}
		return charge;
	}

	public float stringToFloatLundi(NumberFormat nf, String strNbHeureLundi) {
		float nbHeureLundi = 0;
		try {
			nbHeureLundi = Float.parseFloat(strNbHeureLundi);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				nbHeureLundi = nf.parse(strNbHeureLundi).floatValue();
			} catch (ParseException pe) {
				logger.error(pe.getMessage(), pe);
			}
		}
		return nbHeureLundi;
	}

	public float stringToFloatMardi(NumberFormat nf, String strNbHeureMardi) {
		float nbHeureMardi = 0;
		try {
			nbHeureMardi = Float.parseFloat(strNbHeureMardi);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				nbHeureMardi = nf.parse(strNbHeureMardi).floatValue();
			} catch (ParseException pe) {
				logger.error(pe.getMessage(), pe);
			}
		}
		return nbHeureMardi;
	}

	public float stringToFloatMercredi(NumberFormat nf,
			String strNbHeureMercredi) {
		float nbHeureMercredi = 0;
		try {
			nbHeureMercredi = Float.parseFloat(strNbHeureMercredi);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				nbHeureMercredi = nf.parse(strNbHeureMercredi).floatValue();
			} catch (ParseException pe) {
				logger.error(pe.getMessage(), pe);
			}
		}
		return nbHeureMercredi;
	}

	public float stringToFloatJeudi(NumberFormat nf, String strNbHeureJeudi) {
		float nbHeureJeudi = 0;
		try {
			nbHeureJeudi = Float.parseFloat(strNbHeureJeudi);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				nbHeureJeudi = nf.parse(strNbHeureJeudi).floatValue();
			} catch (ParseException pe) {
				logger.error(pe.getMessage(), pe);
			}
		}
		return nbHeureJeudi;
	}

	public float stringToFloatVendredi(NumberFormat nf,
			String strNbHeureVendredi) {
		float nbHeureVendredi = 0;
		try {
			nbHeureVendredi = Float.parseFloat(strNbHeureVendredi);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			try {
				nbHeureVendredi = nf.parse(strNbHeureVendredi).floatValue();
			} catch (ParseException pe) {
				logger.error(pe.getMessage(), pe);
			}
		}
		return nbHeureVendredi;
	}
}
