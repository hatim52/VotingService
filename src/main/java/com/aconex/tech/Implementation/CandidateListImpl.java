package com.aconex.tech.Implementation;

import com.aconex.tech.api.CandidateListService;
import com.aconex.tech.core.AbstractService;
import com.aconex.tech.core.Services;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.aconex.tech.utilities.ArgumentValidator;

public class CandidateListImpl  extends AbstractService implements CandidateListService {

    //Creating a hashmap to store the candidates with their character notation.
    protected HashMap<Character, String> candidates = new HashMap<Character, String> ();
    public static int candidateCount;  // nned to update this
    public CandidateListImpl(Services services,String fileLocation) {
        super (services,fileLocation);
        readCandidateList (fileLocation);
    }
     /**
      * Reads the candidates from a file location and updates the static candidates hashmap.
      * @param fileLocation
      */
     public void readCandidateList (String fileLocation){
         char alphabet = 'A';
         try {
             //This java code reads in each word and puts it into the ArrayList:
             ArgumentValidator.checkArgumentNullOrEmpty (fileLocation);
             // Check if the location exits and is readable
             ArgumentValidator.checkLocationExists (fileLocation);
             ArgumentValidator.checkFileReadPermission (fileLocation);
             Scanner s = new Scanner (new File (fileLocation));
             while (s.hasNextLine( )) {
                 String candidate = s.nextLine ( ).trim ( );
                 if (candidate.equals ("")) {
                     continue;
                 }
                 candidates.put (alphabet,candidate );
                 alphabet++;
             }
             s.close ( );
         } catch (FileNotFoundException e) {
             e.printStackTrace ( );
         }
     }

     public HashMap<Character,String> getCandidatesList(){
         return this.candidates;
     }

     /**
      * Displays the candidate list on the console.
      */
     public void displayCandidatesList ( ){
            for (Map.Entry<Character, String> entry : candidates.entrySet()) {
                System.out.println(entry.getKey() + ":" + entry.getValue().toString());
            }
        }

        /**
         * Method returns the name of the candidate by passing the character which denotes the candidate int he candidate list.
         * @param candidateDenotor
         * @return
         */

     public String getCandidateName(Character candidateDenotor){
            ArgumentValidator.checkArgumentNullOrEmpty (candidateDenotor);
            return candidates.get (candidateDenotor);
        }

        /**
         * Returns the number of candidates from the candidate list.
         * @return
         */
     public Integer getCandidateCount(){
            return (candidates.size ());
        }

     public HashMap<Character,Integer> getVotingTemplate(){
         HashMap<Character, Integer> EmptyVoteMap = new HashMap<Character, Integer> ( );
         char alphabet = 'A';
         for (int i = 0; i < getCandidateCount(); i++) {
             EmptyVoteMap.put (alphabet, 0);
             alphabet++;
         }
         return EmptyVoteMap;
    }
}

