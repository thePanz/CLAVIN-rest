package com.bericotech.clavin.rest.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import com.bericotech.clavin.GeoParser;
import com.bericotech.clavin.resolver.ResolvedLocation;
import com.bericotech.clavin.rest.ClavinRestService;
import com.bericotech.clavin.rest.core.ResolvedLocations;
import com.bericotech.clavin.rest.core.ResolvedLocationsMinimum;

@Path("/v0")
@Produces(MediaType.APPLICATION_JSON)
public class ClavinRestResource {
    private final GeoParser parser;    

    final Logger logger = LoggerFactory.getLogger(ClavinRestService.class);
    
    public ClavinRestResource(GeoParser parser) {
        this.parser = parser;
    }

    @GET
    public String index() {
        return "CLAVIN-rest 0.1";
    }
    
        
    @POST
    @Path("/geotag")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response extractAndResolveSimpleLocationsFromText(String text) {
    	
    	logger.debug("Start of POST Method for path '/geotag'.");
    	
    	
        ResolvedLocations result = null;
        
        logger.debug("Entering Try/Catch Block for parsing 'text' @POST '/geotag'.");
        try {
        	logger.debug("In Try Block, about to parse 'text' @POST '/geotag', returning 'List<ResolvedLocation>'.");
            List<ResolvedLocation> resolvedLocations = parser.parse(text);
            logger.debug("In Try Block, finished parsing 'text' @POST '/geotag'.");
            
            logger.debug("In Try Block, about to set result = 'ResolvedLocations' @POST '/geotag'.");
            result = new ResolvedLocations(resolvedLocations);
            logger.debug("In Try Block, finished setting result = 'ResolvedLocations' @POST '/geotag'.");
        
        } catch (Exception e) {
        	
        	logger.debug("In Catch Block, an error has occured @POST '/geotag'.");
        	logger.error("Error Message In Catch Block @POST '/geotag': " + e.getMessage()); 
        	e.printStackTrace();
            
            logger.debug("In Catch Block, about to return @POST '/geotag'.");
            return Response.status(500).entity(e).build();
        }
        logger.debug("Exiting Try/Catch Block for parsing 'text' @POST '/geotag'.");
      
        logger.debug("End of POST Method for path '/geotag'; Returning...");
        return Response.status(200).entity(result).build();
        
    }
    
    
    @POST
    @Path("/geotagmin")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response extractAndResolveSimpleShortLocationsFromText(String text) {
    
    	logger.debug("Start of POST Method for path '/geotagmin'.");
    	
        ResolvedLocationsMinimum result = null;
        
        logger.debug("Entering Try/Catch Block for parsing 'text' @POST '/geotagmin'.");
        try {
            
        	logger.debug("In Try Block, about to parse 'text' @POST '/geotagmin', returning 'List<ResolvedLocation>'.");
        	List<ResolvedLocation> resolvedLocations = parser.parse(text);
        	logger.debug("In Try Block, finished parsing 'text' @POST '/geotagmin'.");
        	
        	logger.debug("In Try Block, about to set result = 'ResolvedLocations' @POST '/geotagmin'.");
            result = new ResolvedLocationsMinimum(resolvedLocations);
            logger.debug("In Try Block, finished setting result = 'ResolvedLocations' @POST '/geotagmin'.");
        
        } catch (Exception e) {
        	
        	logger.debug("In Catch Block, an error has occured @POST '/geotagmin'.");
        	logger.error("Error Message In Catch Block @POST '/geotagmin': " + e.getMessage()); 
            e.printStackTrace();
            
            logger.debug("In Catch Block, about to return @POST '/geotagmin'.");
            return Response.status(500).entity(e).build();
        }
        logger.debug("Exiting Try/Catch Block for parsing 'text' @POST '/geotagmin'.");
      
        logger.debug("End of POST Method for path '/geotagmin'; Returning...");
        return Response.status(200).entity(result).build();
        
    }
  
}