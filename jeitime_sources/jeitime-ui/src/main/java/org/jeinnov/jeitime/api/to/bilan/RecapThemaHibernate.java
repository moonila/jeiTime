package org.jeinnov.jeitime.api.to.bilan;

public class RecapThemaHibernate {

	private String nomcoll;
	private int idcoll;
	private String nomprojet;
	private int idprojet;
	private String nomthematique;
	private Object total;

	public RecapThemaHibernate() {

	}

	public RecapThemaHibernate(String nomcoll, int idcoll, String nomprojet,
			int idprojet, String nomthematique, Object total) {
		super();
		this.nomcoll = nomcoll;
		this.idcoll = idcoll;
		this.nomprojet = nomprojet;
		this.idprojet = idprojet;
		this.nomthematique = nomthematique;
		this.total = total;
	}

	public String getNomcoll() {
		return nomcoll;
	}

	public void setNOMCOLL(String nomcoll) {
		this.nomcoll = nomcoll;
	}

	public int getIdcoll() {
		return idcoll;
	}

	public void setIDCOLL(int idcoll) {
		this.idcoll = idcoll;
	}

	public String getNomprojet() {
		return nomprojet;
	}

	public void setNOMPROJET(String nomprojet) {
		this.nomprojet = nomprojet;
	}

	public int getIdprojet() {
		return idprojet;
	}

	public void setIDPROJET(int idprojet) {
		this.idprojet = idprojet;
	}

	public String getNomthematique() {
		return nomthematique;
	}

	public void setNOMTHEMATIQUE(String nomthematique) {
		this.nomthematique = nomthematique;
	}

	public Object getTotal() {
		return total;
	}

	public void setTOTAL(Object total) {
		this.total = total;
	}

}
