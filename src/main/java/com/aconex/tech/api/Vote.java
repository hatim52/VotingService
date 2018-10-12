package com.aconex.tech.api;

import com.aconex.tech.core.ServiceFactory;
import com.aconex.tech.core.Services;

import java.util.HashMap;
import java.util.Map;

public class Vote {

    public static int voteCount;
    public int voteNumber;
    public HashMap<Character, Integer> voteMap;

    public Vote(String voteseq,HashMap<Character,Integer> voteMapTemplate){
        voteMap = new HashMap<Character, Integer> (voteMapTemplate );
        //voteMap.putAll (voteMapTemplate);
        char alphabet = 'A';
        // System.out.println ("Candidate count is "+Integer.toString (candidateCount) );
        char[] eachVote = voteseq.toCharArray ( );
        for (int j = 0; j < eachVote.length; j++) {
            voteMap.put (eachVote[j], j + 1);
        }
        voteCount++;
        voteNumber = voteCount;
    }

    /**
     * Prints the vote result on the console
     */
    public void printVote() {
        for (Map.Entry<Character, Integer> entry : this.voteMap.entrySet ( )) {
            System.out.println (entry.getKey ( ) + ":" + entry.getValue ( ).toString ( ));
        }
    }

    /**
     * Provides the vote preference of the candidate based on the candinate denoter
     * @param preference
     * @return
     */
    public Character getCandidateFromPreference(Integer preference) {
        for (Map.Entry<Character, Integer> entry : voteMap.entrySet ( )) {
            if (entry.getValue ( ) == preference) {
                return entry.getKey ( );
            }
        }
        return null;
    }

}
