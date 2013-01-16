package org.joshpeters.learning.web;

import javax.ejb.Stateless;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Stateless
@ApplicationPath( "/api" )
public class JaxRsApp
extends Application
{
}