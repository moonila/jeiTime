package org.jeinnov.jeitime.api.service.utils;

import java.util.ArrayList;
import java.util.List;

import org.jeinnov.jeitime.api.to.collaborateur.CollaborateurTO;
import org.jeinnov.jeitime.api.to.projet.ProjetTO;
import org.jeinnov.jeitime.api.to.projet.TacheTO;
import org.jeinnov.jeitime.persistence.bo.projet.TacheP;

public class SortList {

	public SortList() {
		super();
	}

	public List<ProjetTO> sortListTachePProjetNotLock(List<TacheP> listT) {
		List<ProjetTO> listTmp = new ArrayList<ProjetTO>();
		for (int i = 0; i < listT.size(); i++) {
			if (listT.get(i).getProjet().getDateCloture() == null) {
				ProjetTO p = new ProjetTO();
				p.setIdProjet(listT.get(i).getProjet().getIdProjet());
				p.setNomProjet(listT.get(i).getProjet().getNomProjet());
				listTmp.add(p);
			}
		}
		sortProjetTOTable(listTmp);

		return listTmp;
	}

	public List<ProjetTO> sortListTachePProjetNotClose(List<TacheP> listT) {

		List<ProjetTO> listTmp = new ArrayList<ProjetTO>();
		for (int i = 0; i < listT.size(); i++) {
			if (listT.get(i).getProjet().getDateFermeture() == null) {
				ProjetTO p = new ProjetTO();
				p.setIdProjet(listT.get(i).getProjet().getIdProjet());
				p.setNomProjet(listT.get(i).getProjet().getNomProjet());
				listTmp.add(p);
			}
		}
		sortProjetTOTable(listTmp);

		return listTmp;
	}

	private void sortProjetTOTable(List<ProjetTO> listTmp) {
		for (int i = 0; i < listTmp.size(); i++) {
			int j = i + 1;
			while (j < listTmp.size()) {
				if (listTmp.get(i).getIdProjet() == listTmp.get(j)
						.getIdProjet()) {
					listTmp.remove(j);
				} else {
					j++;
				}
			}
		}
	}

	public List<Object> trieListItem(List<Object> listItems) {

		for (int i = 0; i < listItems.size(); i++) {
			int j = i + 1;
			while (j < listItems.size()) {
				if (listItems.get(i) == listItems.get(j)) {
					listItems.remove(j);
				}
				if (listItems.get(i) instanceof ProjetTO
						&& listItems.get(j) instanceof ProjetTO) {
					if (((ProjetTO) listItems.get(i)).getIdProjet() == ((ProjetTO) listItems
							.get(j)).getIdProjet()) {
						listItems.remove(j);
					} else {
						j++;
					}
				} else {
					j++;
				}
			}
		}
		return listItems;
	}

	public void sortListItems(List<Object> listItems) {
		for (int i = 0; i < listItems.size(); i++) {
			int j = i + 1;
			while (j < listItems.size()) {
				if (listItems.get(i) == listItems.get(j)) {
					listItems.remove(j);
				} else {
					j++;
				}
			}
		}
	}

	public void sortListByCollaborateurTO(List<Object> listItems) {
		for (int i = 0; i < listItems.size(); i++) {
			int j = i + 1;
			while (j < listItems.size()) {
				if (listItems.get(i) instanceof CollaborateurTO
						&& listItems.get(j) instanceof CollaborateurTO) {
					if (((CollaborateurTO) listItems.get(i)).getIdColl() == ((CollaborateurTO) listItems
							.get(j)).getIdColl()) {
						listItems.remove(j);
					} else {
						j++;
					}
				}
				else {
					j++;
				}
			}
		}
	}

	public void sortListByTacheTO(List<Object> listItems) {
		for (int i = 0; i < listItems.size(); i++) {
			int j = i + 1;
			while (j < listItems.size()) {
				if (listItems.get(i) instanceof TacheTO
						&& listItems.get(j) instanceof TacheTO) {
					if (((TacheTO) listItems.get(i)).getIdTache() == ((TacheTO) listItems
							.get(j)).getIdTache()) {
						listItems.remove(j);
					} else {
						j++;
					}
				}
				else {
					j++;
				}
			}
		}
	}
}
