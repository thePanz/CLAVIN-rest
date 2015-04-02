package com.bericotech.clavin.rest;

import java.io.IOException;

import com.bericotech.clavin.ClavinException;
import com.bericotech.clavin.GeoParserFactory;
import com.bericotech.clavin.rest.command.IndexCommand;
import org.apache.lucene.queryparser.classic.ParseException;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.bazaarvoice.dropwizard.assets.ConfiguredAssetsBundle;
import com.bericotech.clavin.GeoParser;
import com.bericotech.clavin.nerd.StanfordExtractor;
import com.bericotech.clavin.rest.resource.ClavinRestResource;




public class ClavinRestService extends Service<ClavinRestConfiguration> {
	
    public static void main(String[] args) throws Exception {
        new ClavinRestService().run(args);
    }

    @Override
    public void initialize(Bootstrap<ClavinRestConfiguration> bootstrap) {
        bootstrap.setName("clavin-rest");
        //bootstrap.addBundle(new AssetsBundle("/assets/", "/"));
        bootstrap.addBundle(new ConfiguredAssetsBundle("/assets/", "/"));
        bootstrap.addCommand(new IndexCommand());
    }

    @Override
    public void run(ClavinRestConfiguration configuration,
                    Environment environment) throws ClassCastException, ClassNotFoundException, IOException, ParseException, ClavinException {
        final String luceneDir = configuration.getLuceneDir();
        final Integer maxHitDepth = configuration.getMaxHitDepth();
        final Integer maxContextWindow = configuration.getMaxContextWindow();
        final Boolean fuzzy = configuration.getFuzzy();
        final Boolean useNERDExtraction = configuration.getUseStandfordExtractor();

        GeoParser parser = null;
        if (configuration.getUseStandfordExtractor() == true) {
            StanfordExtractor extractor = new StanfordExtractor();
            parser = GeoParserFactory.getDefault(luceneDir, extractor, maxHitDepth, maxContextWindow, fuzzy);
        }
        else {
            parser = GeoParserFactory.getDefault(luceneDir, maxHitDepth, maxContextWindow, fuzzy);
        }

        environment.addResource(new ClavinRestResource(parser));
    }

}
