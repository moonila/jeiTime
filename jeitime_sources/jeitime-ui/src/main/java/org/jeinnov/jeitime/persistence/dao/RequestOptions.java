package org.jeinnov.jeitime.persistence.dao;

public class RequestOptions {
	private int firstResult = 0;

	private int nbOfResults = Integer.MAX_VALUE;

	private String sortCriteria = "";

	private boolean sortAscendingly = true;

	private boolean caseSensitive = false;

	private String fetchCriteria = "";

	/**
	 * Determines whether pagination options are set
	 * 
	 * @return
	 */
	public boolean hasPagination() {
		return nbOfResults != Integer.MAX_VALUE;
	}

	/**
	 * Determines whether sort options are set
	 * 
	 * @return
	 */
	public boolean hasSortOption() {
		return !"".equals(sortCriteria);
	}

	/**
	 * Determines whether sort options are set
	 * 
	 * @return
	 */
	public boolean hasFetchOption() {
		return !"".equals(fetchCriteria);
	}

	/**
	 * Returns the requested first result index.
	 * <p>
	 * This the pagination offset
	 * </p>
	 * 
	 * @return
	 */
	public int getFirstResult() {
		return firstResult;
	}

	/**
	 * Sets the requested first result index.
	 * 
	 * @param firstResult
	 */
	public void setFirstResult(int firstResult) {
		this.firstResult = firstResult;
	}

	/**
	 * Returns the requested number of results.
	 * <p>
	 * This the pagination length
	 * </p>
	 * 
	 * @return
	 */
	public int getNbOfResults() {
		return nbOfResults;
	}

	/**
	 * Sets the requested number of results.
	 * 
	 * @param nbOfResults
	 */
	public void setNbOfResults(int nbOfResults) {
		this.nbOfResults = nbOfResults;
	}

	/**
	 * Determines whether sort is ascending.
	 * 
	 * @return
	 */
	public boolean isSortAscendingly() {
		return sortAscendingly;
	}

	/**
	 * Sets whether sort is ascending.
	 * 
	 * @param sortAscendingly
	 */
	public void setSortAscendingly(boolean sortAscendingly) {
		this.sortAscendingly = sortAscendingly;
	}

	/**
	 * Returns the sort criteria ID
	 * 
	 * @return
	 */
	public String getSortCriteria() {
		return sortCriteria;
	}

	/**
	 * Sets the sort criteria ID
	 * 
	 * @param sortColumn
	 */
	public void setSortCriteria(String sortColumn) {
		this.sortCriteria = sortColumn;
	}

	public boolean isCaseSensitive() {
		return caseSensitive;
	}

	public void setCaseSensitive(boolean caseSensitive) {
		this.caseSensitive = caseSensitive;
	}

	public String getFetchCriteria() {
		return fetchCriteria;
	}

	public void setFetchCriteria(String fetchCriteria) {
		this.fetchCriteria = fetchCriteria;
	}
}
