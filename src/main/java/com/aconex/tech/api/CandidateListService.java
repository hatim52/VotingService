package com.aconex.tech.api;
import java.util.HashMap;

public interface CandidateListService {

    void readCandidateList (String fileLocation);

    public HashMap<Character, String> getCandidatesList ( );

    public HashMap<Character,Integer> getVotingTemplate();

    Integer getCandidateCount ( );

    void displayCandidatesList ( );

    String getCandidateName (Character candidateDenotor);
}