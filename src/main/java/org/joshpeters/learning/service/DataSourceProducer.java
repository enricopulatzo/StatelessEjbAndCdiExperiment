package org.joshpeters.learning.service;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.enterprise.inject.Produces;
import javax.sql.DataSource;

/**
 * Provides DataSources to CDI beans requiring them.
 */
@Stateless
public class DataSourceProducer
{
	@Resource( name = "jdbc/forExperiment" )
	private DataSource myDs;

	@Produces
	public DataSource getDatasource()
	{
		return myDs;
	}
}