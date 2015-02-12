package com.bericotech.clavin.rest;

import java.io.File;
import java.io.IOException;

import com.bericotech.clavin.ClavinException;
import com.bericotech.clavin.gazetteer.query.Gazetteer;
import com.bericotech.clavin.gazetteer.query.LuceneGazetteer;
import com.bericotech.clavin.rest.command.IndexCommand;
import org.apache.lucene.queryparser.classic.ParseException;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.bazaarvoice.dropwizard.assets.ConfiguredAssetsBundle;
import com.bericotech.clavin.GeoParser;
import com.bericotech.clavin.nerd.StanfordExtractor;
import com.bericotech.clavin.rest.resource.ClavinRestResource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ClavinRestService extends Service<ClavinRestConfiguration> { 
	
    public static void main(String[] args) throws Exception {
		Logger logger = LoggerFactory.getLogger(ClavinRestService.class);
        
		logger.debug("Start of the 'main' method.");
		logger.info("CLAVIN-REST is starting...");
    	
        logger.debug("About to execute the RUN method.");
        new ClavinRestService().run(args);
        logger.debug("Returned from executing the RUN method");
        
        logger.debug("End of the 'main' method.");
    }

    @Override
    public void initialize(Bootstrap<ClavinRestConfiguration> bootstrap) {
    	Logger logger = LoggerFactory.getLogger(ClavinRestService.class);
    	logger.debug("Start of the 'initialize' method.");
    	
    	logger.debug("About to use dropwizard bootstrap to setName 'clavin-rest'.");
    	bootstrap.setName("clavin-rest");
    	logger.debug("Finished setName.");
    	
    	//bootstrap.addBundle(new AssetsBundle("/assets/", "/"));

    	logger.debug("About to use dropwizard bootstrap to addBundle 'assets'.");
    	bootstrap.addBundle(new ConfiguredAssetsBundle("/assets/", "/"));
    	logger.debug("Finished addBundle.");
    	
    	logger.debug("About to use dropwizard bootstrap to addCommand IndexCommand.");
    	bootstrap.addCommand(new IndexCommand());
    	logger.debug("Finished addCommand.");
    	
    	logger.debug("End of the 'initialize' method.");
    }

    @Override
    public void run(ClavinRestConfiguration configuration,
                    Environment environment) throws ClassCastException, ClassNotFoundException, IOException, ParseException, ClavinException {
    	
    	Logger logger = LoggerFactory.getLogger(ClavinRestService.class);
    	
    	logger.debug("Start of the 'run' method.");
    	
    	logger.debug("Setting environment variables for luceneDir, maxHitDepth, maxContextWindow.");
    	final String luceneDir = configuration.getLuceneDir();
        final Integer maxHitDepth = configuration.getMaxHitDepth();
        final Integer maxContextWindow = configuration.getMaxContextWindow();
        // final Boolean fuzzy = configuration.getFuzzy();
        
        logger.debug("Initializing the Lucene Gazetteer.");
        Gazetteer gazetteer = new LuceneGazetteer(new File(luceneDir));
        logger.debug("Finished initializing gazetteer.");
        
        logger.debug("Initializing the CLAVIN-NERD 'StanfordExtractor'.");
        StanfordExtractor extractor = new StanfordExtractor();
        logger.debug("Finished initializing StanfordExtractor.");
        
        logger.debug("Initializing the CLAVIN 'GeoParser'.");
        GeoParser parser = new GeoParser(extractor, gazetteer, maxHitDepth, maxContextWindow, false);
        logger.debug("Finished initializing GeoParser.");
        
        logger.debug("Registering the 'ClavinRestResource(parser)' with DropWizard.");
        environment.addResource(new ClavinRestResource(parser));
        logger.debug("Finished registering the Resource environment.");
        
        logger.debug("End of the 'run' method.");
    }

}
