package org.joshpeters.learning.service;

import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Update;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.util.UUID;

@ApplicationScoped
public class AccessLoggerServiceBean
	implements AccessLoggerService
{
	@Inject
	private DataSource myDs;

	@Override
	public boolean logAccess( String thingToLog )
	{
		final Handle dbi = DBI.open( myDs );
		final Update query = dbi.createStatement( "INSERT INTO APP_LOG ( LOG_ID, LOG_MESSAGE ) VALUES ( :id, :message )" );
		query.bind( "id", UUID.randomUUID().toString() );
		query.bind( "message", thingToLog );
		final boolean result = 0 < query.execute();
		dbi.close();
		return result;
	}
}