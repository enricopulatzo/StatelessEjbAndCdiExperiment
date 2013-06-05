package org.joshpeters.learning.service;

import com.google.common.base.Preconditions;
import edu.ilstu.bits.common.mail.MailSender;
import lombok.AccessLevel;
import lombok.Getter;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;
import org.skife.jdbi.v2.Update;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
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

	@PostConstruct
	private void init()
	{
		Preconditions.checkNotNull( myDs, "cannot work without a myDs" );
	}

	@Override
	public boolean logAccess( String thingToLog )
	{
		final Handle dbi = DBI.open( myDs );
		final Update query = dbi.createStatement( "INSERT INTO APP_LOG ( LOG_ID, LOG_MESSAGE ) VALUES ( :id, :message )" );
		query.bind( "id", UUID.randomUUID().toString() );
		query.bind( "message", thingToLog );
		final int results = query.execute();
		final String message = String.format( "Found %1$d results", results );
		final boolean result = 0 < results;
		dbi.close();
		return result;
	}
}