package com.bericotech.clavin.rest;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.DefaultValue;

import org.hibernate.validator.constraints.NotEmpty;

import com.bazaarvoice.dropwizard.assets.AssetsBundleConfiguration;
import com.bazaarvoice.dropwizard.assets.AssetsConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;

public class ClavinRestConfiguration extends Configuration implements AssetsBundleConfiguration{

	@NotEmpty
    @JsonProperty
    private String luceneDir;
    
    @NotNull
    @JsonProperty
    private Integer maxHitDepth;
    
    @NotNull
    @JsonProperty
    private Integer maxContextWindow; 
    
    @NotNull
    @JsonProperty
    private Boolean fuzzy;

    @JsonProperty
    private String[] gazetteerFiles;

    @JsonProperty
    private String alternateNamesFile;

    @NotNull
    @JsonProperty
    private Boolean useStandfordExtractor;
    
    @Valid
    @NotNull
    @JsonProperty
    private final AssetsConfiguration assets = new AssetsConfiguration();
    
    public String getLuceneDir() {
        return luceneDir;
    }
    
    public Integer getMaxHitDepth() {
        return maxHitDepth;
    }
    
    public Boolean getFuzzy() {
    	return fuzzy;
    }
    
    public Integer getMaxContextWindow() {
    	return maxContextWindow;
    }

	public AssetsConfiguration getAssetsConfiguration() {
		return assets;
	}

    public Boolean getUseStandfordExtractor() {
        return useStandfordExtractor;
    }
    
    public String[] getGazetteerFiles() {
        if (gazetteerFiles == null)
            return new String[]{""};
        return gazetteerFiles;
    }

    public String getAlternateNamesFile() {
        if (alternateNamesFile == null)
            return "";
        return alternateNamesFile;
    }
    
}