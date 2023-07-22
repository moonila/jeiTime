package org.jeinnov.jeitime.api.to.bilan;

public class RecapTypePHibernate {
	private String nomcoll;
	private int idcoll;
	private String nomprojet;
	private int idprojet;
	private String nomtypeprojet;
	private Object total;
	private int idtypeprojet;

	public RecapTypePHibernate() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RecapTypePHibernate(String nomcoll, int idcoll, String nomprojet,
			int idprojet, String nomtypeprojet, float total) {
		super();
		this.nomcoll = nomcoll;
		this.idcoll = idcoll;
		this.nomprojet = nomprojet;
		this.idprojet = idprojet;
		this.nomtypeprojet = nomtypeprojet;
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

	public String getNomtypeprojet() {
		return nomtypeprojet;
	}

	public void setNOMTYPEPROJET(String nomtypeprojet) {
		this.nomtypeprojet = nomtypeprojet;
	}

	public int getIdtypeprojet() {
		return idtypeprojet;
	}

	public void setIdTYPEPROJET(int idtypeprojet) {
		this.idtypeprojet = idtypeprojet;
	}

	public Object getTotal() {
		return total;
	}

	public void setTOTAL(Object total) {
		this.total = total;
	}

}
