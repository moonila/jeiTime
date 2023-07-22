package org.jeinnov.jeitime.ws;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.dialect.Dialect;
import org.hibernate.jdbc.Work;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;

@WebService
public class DatabaseManagerImpl implements DatabaseManager {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	public void updateDatabaseSchema() throws Exception {
		// logger.info("Updating database schema for Hibernate SessionFactory");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.doWork(new Work() {
				public void execute(Connection con) throws SQLException {
					Dialect dialect = Dialect.getDialect(HibernateUtil
							.getConfiguration().getProperties());
					DatabaseMetadata metadata = new DatabaseMetadata(con,
							dialect);
					String[] sql = HibernateUtil.getConfiguration()
							.generateSchemaUpdateScript(dialect, metadata);
					executeSchemaScript(con, sql);

				}
			});

		} finally {
			session.getTransaction().commit();
		}
	}

	public void dropDatabaseSchema() throws Exception {
		// logger.info("Updating database schema for Hibernate SessionFactory");
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.doWork(new Work() {
				public void execute(Connection con) throws SQLException {
					Dialect dialect = Dialect.getDialect(HibernateUtil
							.getConfiguration().getProperties());
					String[] sql = HibernateUtil.getConfiguration()
							.generateDropSchemaScript(dialect);
					executeSchemaScript(con, sql);
				}
			});
		} finally {
			session.getTransaction().commit();

		}
	}

	public void initUserAndRole() {
		createRole();
		createUser();
		createRoleForUser();
	}

	private void createRole() {
		// query for administrateur
		String querySelect = "select * from ROLES r where r.roles ='administrateur' ";
		String queryInsert = "insert into ROLES (roles) values ('administrateur');";
		createDataInDatabase(querySelect, queryInsert);

		// query for collaborateur
		querySelect = "select * from ROLES r where r.roles ='collaborateur' ";
		queryInsert = "insert into ROLES (roles) values ('collaborateur');";
		createDataInDatabase(querySelect, queryInsert);

		// query for chef_projet
		querySelect = "select * from ROLES r where r.roles ='chef_de_projet'";
		queryInsert = "insert into ROLES (roles) values ('chef_de_projet');";
		createDataInDatabase(querySelect, queryInsert);

		// query for gestionnaire
		querySelect = "select * from ROLES r where r.roles ='gestionnaire'  ";
		queryInsert = "insert into ROLES (roles) values ('gestionnaire');";
		createDataInDatabase(querySelect, queryInsert);
	}

	private void createUser() {
		String querySelect = "select * from COLLABORATEUR c where c.login ='admin'";
		String queryInsert = "insert into COLLABORATEUR "
				+ "(CHARGEANN,CONTRAT,LOGIN,"
				+ "NBHEURANN,NBHEUREHEB,NBHEURJEUDI,NBHEURLUNDI,"
				+ "NBHEURMARDI,NBHEUREMEN,NBHEURMERCREDI,NBHEURVENDREDI,NOMCOLL,"
				+ "PASSWORD,PRENOMCOLL,SALAIRANN,STATUT) "
				+ "values (0,0,'admin',0,0,0,0,0,0,0,0,'admin','21232f297a57a5a743894a0e4a801fc3',"
				+ "'admin',0,0);";
		createDataInDatabase(querySelect, queryInsert);
	}

	private void createRoleForUser() {

		String querySelect = "select * from ROLECOLLAB  where idcoll =1 ";
		String queryInsert = "insert into ROLECOLLAB (IDCOLL,ROLES) values (1,'administrateur');";
		createDataInDatabase(querySelect, queryInsert);
	}

	private void createDataInDatabase(final String querySelect,
			final String queryInsert) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		try {
			session.doWork(new Work() {
				public void execute(Connection con) throws SQLException {
						Statement stt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
								ResultSet.CONCUR_UPDATABLE);
						try {
							ResultSet rs = stt.executeQuery(querySelect);
							if (rs.first() == false) {
								stt.executeUpdate(queryInsert);
							}
						}catch(RuntimeException e){
							logger.error(e.getMessage(), e);
							} 
						finally {
							con.commit();
							stt.close();
						}
				}
			});
		} finally {
			session.getTransaction().commit();
		}
	}

	protected void executeSchemaScript(Connection con, String[] sql)
			throws SQLException {
		if (sql != null && sql.length > 0) {
			boolean oldAutoCommit = con.getAutoCommit();
			if (!oldAutoCommit) {
				con.setAutoCommit(true);
			}
			try {
				Statement stmt = con.createStatement();
				try {
					for (String sqlStmt : sql) {
						executeSchemaStatement(stmt, sqlStmt);
					}
				} finally {
					stmt.close();
				}
			} finally {
				if (!oldAutoCommit) {
					con.setAutoCommit(false);
				}
			}
		}
	}

	protected void executeSchemaStatement(Statement stmt, String sql)
			throws SQLException {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			logger.error(ex.getMessage(), ex);
		}
	}
}
