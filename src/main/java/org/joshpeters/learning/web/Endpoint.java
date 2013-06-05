package org.joshpeters.learning.web;

import com.google.common.base.Preconditions;
import edu.ilstu.bits.common.mail.MailSender;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.joshpeters.learning.service.AccessLoggerService;
import org.joshpeters.learning.service.PassOrFailService;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.net.URI;

@Stateless
//@TransactionAttribute( TransactionAttributeType.NEVER )
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@Path("/")
public class Endpoint
{
	@Inject
	@Setter(AccessLevel.PACKAGE)
	private AccessLoggerService accessLoggerService;

	@Inject
	@Setter(AccessLevel.PACKAGE)
	private PassOrFailService passOrFailService;

	@PostConstruct
	private void sanityCheck()
	{
		Preconditions.checkNotNull( accessLoggerService, "no logger service provided--I can't do my job under these conditions!" );
	}

	@Path("/doLog")
	@Produces("text/html")
	@GET
	public Response logForm(
		@DefaultValue("notPresent") @QueryParam("logResult") String logResult
	)
	{
		StringBuilder sb = new StringBuilder();
		sb
			.append( "<html><head><title>Log Form</title></head><body>" );
		if ( !"notPresent".equals( logResult ) ) {
			sb.append( "<h1>Log Result</h1><p>" ).append( logResult ).append( "</p>" );
		}
		sb
			.append( "<h1>Form</h1>" )
			.append( "<form method='post'><div><label>Thing to log: <input name='thing'></label><button type='submit'>Log It</button></form>" )
			.append( "</body></html>" );
		return Response.ok( sb.toString(), "text/html" ).build();
	}

	@Path("/doLog")
	@Produces("text/html")
	@POST
	public Response logFormAction( @FormParam("thing") String thingToLog )
	{
		final boolean logResult = accessLoggerService.logAccess( thingToLog );
		passOrFailService.run();
		return Response.seeOther( URI.create( "/doLog?logResult=" + logResult ) ).build();
	}
}