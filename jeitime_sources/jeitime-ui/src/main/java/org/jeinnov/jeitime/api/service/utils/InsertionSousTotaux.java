package org.jeinnov.jeitime.api.service.utils;

import java.util.ArrayList;
import java.util.List;

import org.jeinnov.jeitime.api.to.bilan.SousTotal;
import org.jeinnov.jeitime.api.to.bilan.Total;
import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;



public class InsertionSousTotaux {

	public InsertionSousTotaux() {

	}

	public List<Object> insertionSousTotal(List<Object> listItems) {
		List<Object> list = new ArrayList<Object>();

		for (int i = 0; i < listItems.size(); i++) {

			if (listItems.get(i) instanceof String) {
				list.add((String) listItems.get(i));
			} else if (listItems.get(i) instanceof ProjetTO) {
				list.add((ProjetTO) listItems.get(i));
			} else if (listItems.get(i) instanceof TacheTO) {

				if (listItems.get(i - 1) instanceof ProjetTO
						|| ((TacheTO) listItems.get(i - 1)).isEligible() == true
						&& ((TacheTO) listItems.get(i)).isEligible() == true) {
					list.add((TacheTO) listItems.get(i));
				}
				if (listItems.get(i - 1) instanceof TacheTO
						&& ((TacheTO) listItems.get(i - 1)).isEligible() == true
						&& ((TacheTO) listItems.get(i)).isEligible() == false) {
					SousTotal sst = new SousTotal();
					sst.setIdProjet(((TacheTO) listItems.get(i)).getProjet()
							.getIdProjet());
					sst.setRd(true);
					sst.setNomSousTotal("Sous Total R&D");
					list.add((SousTotal) sst);
					list.add((TacheTO) listItems.get(i));
				}
				if (listItems.get(i - 1) instanceof TacheTO
						&& ((TacheTO) listItems.get(i - 1)).isEligible() == false
						&& ((TacheTO) listItems.get(i)).isEligible() == false) {
					list.add((TacheTO) listItems.get(i));
				}
				if (listItems.get(i - 1) instanceof TacheTO
						&& ((TacheTO) listItems.get(i - 1)).isEligible() == false
						&& ((TacheTO) listItems.get(i)).isEligible() == true) {
					list.add((TacheTO) listItems.get(i));
				}
				if (listItems.get(i + 1) instanceof Integer
						&& ((TacheTO) listItems.get(i)).isEligible() == false) {
					SousTotal sst = new SousTotal();
					sst.setNomSousTotal("Sous Total non R&D");
					sst.setIdProjet(((TacheTO) listItems.get(i)).getProjet()
							.getIdProjet());
					sst.setRd(false);
					list.add((SousTotal) sst);
				}
				if (listItems.get(i + 1) instanceof Integer
						&& ((TacheTO) listItems.get(i)).isEligible() == true) {
					SousTotal sst = new SousTotal();
					sst.setNomSousTotal("Sous Total R&D");
					sst.setIdProjet(((TacheTO) listItems.get(i)).getProjet()
							.getIdProjet());
					sst.setRd(true);
					list.add((SousTotal) sst);
				}

				if (listItems.get(i + 1) instanceof ProjetTO
						&& ((TacheTO) listItems.get(i)).isEligible() == false) {
					SousTotal sst = new SousTotal();
					sst.setNomSousTotal("Sous Total non R&D");
					sst.setIdProjet(((TacheTO) listItems.get(i)).getProjet()
							.getIdProjet());
					sst.setRd(false);
					list.add((SousTotal) sst);
				}
				if (listItems.get(i + 1) instanceof ProjetTO
						&& ((TacheTO) listItems.get(i)).isEligible() == true) {
					SousTotal sst = new SousTotal();
					sst.setNomSousTotal("Sous Total R&D");
					sst.setIdProjet(((TacheTO) listItems.get(i)).getProjet()
							.getIdProjet());
					sst.setRd(true);
					list.add((SousTotal) sst);
				}
			} else {
				list.add((Integer) listItems.get(i));
			}
		}

		return list;
	}

	public List<Object> insertionSousTotalProjet(List<Object> listItems) {
		List<Object> list = new ArrayList<Object>();

		for (int i = 0; i < listItems.size(); i++) {
			if (listItems.get(i) instanceof String) {
				list.add((String) listItems.get(i));
			} else if (listItems.get(i) instanceof ProjetTO) {
				list.add((ProjetTO) listItems.get(i));
			} else if (listItems.get(i) instanceof TacheTO) {
				list.add((TacheTO) listItems.get(i));
			} else if (listItems.get(i) instanceof SousTotal) {
				if (listItems.get(i - 1) instanceof TacheTO) {
					list.add((SousTotal) listItems.get(i));
				}
				if (listItems.get(i + 1) instanceof ProjetTO
						|| listItems.get(i + 1) instanceof Integer) {
					Total t = new Total();
					t.setIdProjet(((SousTotal) listItems.get(i)).getIdProjet());
					list.add((Total) t);
				}
			} else {
				list.add((Integer) listItems.get(i));
			}
		}
		return list;
	}

	public List<Object> insertionSousTotalProjetMensuel(List<Object> listItems) {
		List<Object> list = new ArrayList<Object>();

		for (int i = 0; i < listItems.size(); i++) {

			if (listItems.get(i) instanceof String) {
				list.add((String) listItems.get(i));
			} else if (listItems.get(i) instanceof CollaborateurTO) {
				list.add((CollaborateurTO) listItems.get(i));
			} else if (listItems.get(i) instanceof Total) {
				list.add((Total) listItems.get(i));
			} else if (listItems.get(i) instanceof TacheTO) {
				if (listItems.get(i - 1) instanceof String
						|| ((TacheTO) listItems.get(i - 1)).isEligible() == true
						&& ((TacheTO) listItems.get(i)).isEligible() == true) {
					list.add((TacheTO) listItems.get(i));
				}
				if (listItems.get(i - 1) instanceof TacheTO
						&& ((TacheTO) listItems.get(i - 1)).isEligible() == true
						&& ((TacheTO) listItems.get(i)).isEligible() == false)

				{
					SousTotal sst = new SousTotal();
					sst.setIdProjet(((TacheTO) listItems.get(i)).getProjet()
							.getIdProjet());
					sst.setRd(true);
					sst.setIdCollab(((TacheTO) listItems.get(i)).getPriorite());
					sst.setNomSousTotal("Sous Total R&D");
					list.add((SousTotal) sst);
					list.add((TacheTO) listItems.get(i));
				}

				if (listItems.get(i - 1) instanceof TacheTO
						&& ((TacheTO) listItems.get(i - 1)).isEligible() == false
						&& ((TacheTO) listItems.get(i)).isEligible() == false)

				{
					list.add((TacheTO) listItems.get(i));
				}
				if (listItems.get(i + 1) instanceof Integer
						&& ((TacheTO) listItems.get(i)).isEligible() == false)

				{
					// list.add((TacheTO)listItems.get(i));
					SousTotal sst = new SousTotal();
					sst.setNomSousTotal("Sous Total non R&D");
					sst.setIdCollab(((TacheTO) listItems.get(i)).getPriorite());
					sst.setIdProjet(((TacheTO) listItems.get(i)).getProjet()
							.getIdProjet());
					sst.setRd(false);
					list.add((SousTotal) sst);
				}
				if (listItems.get(i + 1) instanceof Integer
						&& ((TacheTO) listItems.get(i)).isEligible() == true)

				{
					// list.add((TacheTO)listItems.get(i));
					SousTotal sst = new SousTotal();
					sst.setNomSousTotal("Sous Total R&D");
					sst.setIdCollab(((TacheTO) listItems.get(i)).getPriorite());
					sst.setIdProjet(((TacheTO) listItems.get(i)).getProjet()
							.getIdProjet());
					sst.setRd(false);
					list.add((SousTotal) sst);
				}
			} else {
				list.add((Integer) listItems.get(i));
			}
		}

		return list;
	}

}
