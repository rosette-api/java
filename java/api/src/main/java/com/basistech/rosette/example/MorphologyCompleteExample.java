package com.basistech.rosette.example;

import com.basistech.rosette.api.RosetteAPI;
import com.basistech.rosette.api.RosetteAPIException;
import com.basistech.rosette.apimodel.Decompounding;
import com.basistech.rosette.apimodel.HanReadings;
import com.basistech.rosette.apimodel.Lemma;
import com.basistech.rosette.apimodel.MorphologyResponse;
import com.basistech.rosette.apimodel.PartOfSpeech;

import java.io.IOException;
import java.net.URISyntaxException;

public final class MorphologyCompleteExample extends AbstractExample {

    /**
     * Main program.
     * Creates a RosetteAPI instance with the API key defined in rosette.api.key property.
     * Gets complete morphology analysis as a demonstration of usage.
     *
     * @param args not used 
     * @throws java.net.URISyntaxException
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        init();
        doMorphology(text);
    }
    
    /**
     * Sends complete morphology request from text.
     * @param text
     */
    private static void doMorphology(String text) {
        try {
            MorphologyResponse response = rosetteAPI.getMorphology(RosetteAPI.MorphologicalFeature.COMPLETE, text, null, null);
            print(response);
        } catch (RosetteAPIException e) {
            System.err.println(e.toString());
        } catch (IOException e) {
            System.err.println(e.toString());
        }
    }
    
    /**
     * Prints complete morphology response.
     * @param response
     */
    private static void print(MorphologyResponse response) {
        System.out.println(response.getRequestId());
        String result = "===== posTags =====\n";
        if (response.getPosTags() != null) {
            for (PartOfSpeech partOfSpeech : response.getPosTags()) {
                result += partOfSpeech.getText() + "\t" + partOfSpeech.getPos() + "\n";
            }
        }
        result += "===== lemmas =====\n";
        if (response.getLemmas() != null) {
            for (Lemma lemma : response.getLemmas()) {
                result += lemma.getText() + "\t" + lemma.getLemma() + "\n";
            }
        }
        result += "===== compounds =====\n";
        if (response.getCompounds() != null) {
            for (Decompounding compoud : response.getCompounds()) {
                result += compoud.getText() + "\t" + compoud.getCompoundComponents() + "\n";
            }
        }
        result += "===== hanReadings =====\n";
        if (response.getHanReadings() != null) {
            for (HanReadings reading : response.getHanReadings()) {
                result += reading.getText() + "\t" + reading.getHanReadings() + "\n";
            }
        }
        result += "\n";
        System.out.println(result);
    }
}
