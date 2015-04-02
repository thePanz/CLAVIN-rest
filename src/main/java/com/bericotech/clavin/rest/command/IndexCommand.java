package com.bericotech.clavin.rest.command;

import java.util.ArrayList;
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

        if (!configuration.getGazetteerFiles().isEmpty()) {
            argsList.add("-gazetteer-files=" + configuration.getGazetteerFiles());
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

