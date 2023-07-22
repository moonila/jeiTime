package org.jeinnov.jeitime.utils;

import java.sql.Types;

import org.hibernate.dialect.MySQLInnoDBDialect;

public class MysqlDialect extends MySQLInnoDBDialect

{

	public MysqlDialect() {
		super();
		registerColumnType(Types.REAL, "number($p,$s)");
		registerHibernateType(Types.REAL, "float");
	}

}
