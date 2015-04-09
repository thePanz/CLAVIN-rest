package org.panz.extractor;

import com.bericotech.clavin.extractor.LocationExtractor;
import com.bericotech.clavin.extractor.LocationOccurrence;
import edu.stanford.nlp.util.Triple;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.analysis.tokenattributes.OffsetAttribute;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleExtractor implements LocationExtractor {

    protected Analyzer analyzer;
    protected Version luceneVersion = Version.LUCENE_4_9;


    public SimpleExtractor() {

    }


    /**
     * @param plainText
     * @return
     *
     * @todo implement language identification and correct Analyzer building!
     */
    protected Analyzer getAnalyzerFromText(String plainText) {
        return new EnglishAnalyzer(luceneVersion);
    }

    /**
     * @param plainText  Contents of text document
     * @return List of location names and positions
     */
    public List<LocationOccurrence> extractLocationNames(String plainText) {
        if(plainText == null) {
            throw new IllegalArgumentException("plaintext input to extractLocationNames should not be null");
        }

        analyzer = getAnalyzerFromText(plainText);
        List<LocationOccurrence> locationsList = new ArrayList<LocationOccurrence>();
        String currentTerm;
        int currentPosition;

        try {
            TokenStream stream  = analyzer.tokenStream(null, new StringReader(plainText));
            stream.reset();
            while (stream.incrementToken()) {
                currentTerm = stream.getAttribute(CharTermAttribute.class).toString();
                currentPosition = stream.getAttribute(OffsetAttribute.class).startOffset();
                /*
                 System.out.println("extractLocationNames");
                 System.out.println("  + currentTerm=" + currentTerm);
                 System.out.println("  + currentPosition=" + currentPosition);
                */
                locationsList.add(new LocationOccurrence(currentTerm, currentPosition));
            }
            stream.end();
            stream.close();
        } catch (IOException e) {
            // not thrown b/c we're using a string reader...
            throw new RuntimeException(e);
        }

        return locationsList;
    }


}
