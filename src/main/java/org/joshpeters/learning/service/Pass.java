package org.joshpeters.learning.service;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Alternative;

/**
 * Shouldn't ever fail.
 */
@Alternative
@ApplicationScoped
public class Pass
	implements PassOrFailService
{
	@Override
	public void run()
	{
	}
}