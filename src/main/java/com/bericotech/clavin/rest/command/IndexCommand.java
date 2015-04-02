package com.bericotech.clavin.rest.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.bericotech.clavin.index.IndexDirectoryBuilder;
import com.bericotech.clavin.rest.ClavinRestConfiguration;
import com.yammer.dropwizard.cli.ConfiguredCommand;
import com.yammer.dropwizard.config.Bootstrap;
import net.sourceforge.argparse4j.inf.Namespace;



public class IndexCommand extends ConfiguredCommand<ClavinRestConfiguration> {


    public IndexCommand() {
        super("index", "Index a geonames.org gazetteer");
    }


    @Override
    protected void run(Bootstrap<ClavinRestConfiguration> bootstrap,
                       Namespace namespace,
                       ClavinRestConfiguration configuration) throws Exception {


        List<String> argsList = new ArrayList<String>();

        for (String gazetteer : configuration.getGazetteerFiles()) {
            argsList.add("-gazetteer-files=" + gazetteer);
        }
        if (!configuration.getAlternateNamesFile().isEmpty()) {
            argsList.add("-alt-names-file=" + configuration.getAlternateNamesFile());
        }

        if (argsList.isEmpty()) {
            argsList.add("");
        }

        // Converting the ArrayList back to a String[] array
        String[] args = new String[ argsList.size() ];
        argsList.toArray( args );

        IndexDirectoryBuilder.main(args);
    }

}

