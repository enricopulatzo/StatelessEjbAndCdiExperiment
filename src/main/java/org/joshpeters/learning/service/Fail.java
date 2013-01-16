package org.joshpeters.learning.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

/**
 * Always throws a RuntimeException in order to trigger JTA rollback.
 */
@Alternative
@ApplicationScoped
public class Fail
	implements PassOrFailService
{
	@Override
	public void run()
	{
		throw new RuntimeException( "fail!" );
	}
}