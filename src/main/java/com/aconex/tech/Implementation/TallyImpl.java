package com.aconex.tech.Implementation;

import com.aconex.tech.api.TallyService;
import com.aconex.tech.api.Vote;
import com.aconex.tech.core.AbstractService;
import com.aconex.tech.core.Services;
import com.aconex.tech.utilities.ArgumentValidator;
import java.util.*;

import static com.aconex.tech.Implementation.VoteImpl.GlobalVoteList;

public class TallyImpl  extends AbstractService implements TallyService {
    public TallyImpl(Services services,String fileLocation) {
        super (services,fileLocation);
    }

    public int winningNumber; //This cvariable stores the quota or winning number for each round of counting.
    private int round = 1; // Keeps a count of number of the round.
    private HashMap<Character,ArrayList<Vote> > hm = new HashMap<Character, ArrayList<Vote>> (); // The main map which keeps count of the active votes.
    private HashMap<Character,Integer> simpleMap = new HashMap<Character, Integer> ();  // A simpler map which is the gist of the above map.

    private int candidateCount = services.getCandidateListService ().getCandidateCount ();

    /**
     * Determines the winner based on the votes casted.
     * @return
     */
    public String getWinner() {
        String winner="";
        DistributeVotes();
        updateSimpleMap ( );
        do {
            System.out.println ("Starting Round #"+Integer.toString (round)+" of Vote Counting");
            if (round >1) {
                //During first round we dont have to eliminate any candidates or votes
                eliminateLeastPreferredCandidate ( );
                updateSimpleMap ();
            }
            //Calculate the winning number or quota.
            System.out.println ("Total number of votes in system are : "+getCurrentVoteCount());
            winningNumber = (getCurrentVoteCount()/2)+1;
            System.out.println ("Winning number for Round #"+Integer.toString (round)+" is "+Integer.toString (winningNumber));
            for (Map.Entry<Character, Integer> sm : simpleMap.entrySet ( )) {
                //Check if any candidate has enough votes to be declared as winner
                if (sm.getValue ( ) >= winningNumber) {
                    winner = services.getCandidateListService ().getCandidateName (sm.getKey ( ));
                    System.out.println ("Winner is "+winner );
                    break;
                }
            }
            if (winner.equals ("")) {
                System.out.println ("There was no clear winner at the end of round : " + Integer.toString ((round)));
                round++;
            }
        }while(winner.equals (""));
        cleanup();
        return winner;
    }


    /**
     * Constructor for Tally class which processed the votelist and updates the main hm map or the first time.
     *
     */
    private void DistributeVotes (){
        ArrayList<Vote> votesList=GlobalVoteList;
        //Validate if the votelist is not null
        ArgumentValidator.checkArgumentNullOrEmpty (votesList);
        for (Vote v: votesList){
            for (Map.Entry<Character, Integer> entry : v.voteMap.entrySet()) {
                //Assign the vote to the candidate based on first preference, hence the comparing with number 1.
                if (entry.getValue () == 1){
                    //check if already a vote is casted for this candidate.
                    if (hm.containsKey (entry.getKey ())) {
                        //if a vote is already casted, then update the votelist for the candidate by updating the map.
                        ArrayList<Vote> temp = new ArrayList<Vote> (hm.get (entry.getKey ( )));
                        temp.add (v);
                        hm.put (entry.getKey ( ), temp);
                    }
                    else{
                        //if this is the first vote for the candidate, then create a new arraylist of votes for the hm map.
                        ArrayList<Vote> temp1 = new ArrayList<Vote> ();
                        temp1.add (v);
                        hm.put (entry.getKey (),temp1);
                    }
                }
            }
        }
        // Its possible that some candidates were never the first preference, but we need to have an entry of such candidates in hm.
        NormalizeMap();
    }

    /**
     * Updated the hm map with any candidate which was never the first preference in any vote
     */
    private void NormalizeMap(){
        if ( hm.size () < candidateCount){
            //find out which candidates are not updated in hm
            Set<Character> hmSet = new HashSet<Character> (hm.keySet ());
            Set<Character> origSet = new HashSet<Character> (services.getCandidateListService ().getCandidatesList ().keySet ());
            origSet.removeAll (hmSet);
            //For the remaining candidates, update them in hm with a new empty arraylist
            for (Character c :origSet){
                hm.put (c,new ArrayList<Vote> ());
            }
        }
    }

    /**
     * Updating the simple map with the Candidate letter and current votecount
     */
    private void updateSimpleMap(){
        //First clear the map.
        simpleMap.clear ();
        for (Map.Entry<Character, ArrayList<Vote>> entry : hm.entrySet()) {
            simpleMap.put(entry.getKey (),hm.get (entry.getKey ()).size ());
            System.out.println("Candidate :" + entry.getKey().toString ());
            System.out.println ("Number of votes casted are:"+ hm.get (entry.getKey ()).size () );
        }
    }

    /**
     * Eliminates the candidate which is least preferred at the end of the round which does not have a clear winner.
     */
    private void eliminateLeastPreferredCandidate (){
        //Find the candidate with lowest number of votes.
        Character voteOutCandidate = findCandidateswithLowestVotes();
        ArgumentValidator.checkArgumentNullOrEmpty (voteOutCandidate);
        System.out.println ("Candidate "+voteOutCandidate+" is least preferred candidate, hence being eliminated" );
        //Validate if the votes which were assinged to this candidate.
        validateVote(voteOutCandidate);
    }



    /**
     * Determines the candidate which has the lowest number of votes
     * @return
     */
    private Character findCandidateswithLowestVotes(){
        Character [] voteOutList_temp= new Character[getCurrentCandidateCount()];
        Integer minValue = Collections.min (simpleMap.values ());

        //Find the candidates in simpleMap who has the lowest number of votes.
        Integer count =0;
        for (Map.Entry<Character, Integer> entry : simpleMap.entrySet ( )) {
            if (entry.getValue () == minValue){
                voteOutList_temp[count]=entry.getKey ();
                count++;
            }
        }
        /**Since there can be more than one candidate which have the lowest number of votes
         * We calcute the candidate to be eliminated randomly between the two.
         **/
        int rnd = new Random ().nextInt(count);
        //Finally we return a single candidate which needs to be eliminated.
        return voteOutList_temp[rnd];
    }

    /**
     * This method validates if a vote is still valid,
     * that is if any of the candidates which the voter had preferred are still active.
     * if, yes then the vote is moved to the next preferred candidate, if not the vote is invalidated.
     * @param eliminateCandidate
     */
    private void validateVote(Character eliminateCandidate){
        //Check if the vote is still valid
        //Create a local arraylist of the votes of the eliminated candidate.
        ArrayList<Vote> eliminatedCandidateVoteList = new ArrayList<Vote> (hm.get (eliminateCandidate));

        for( Vote v1 : eliminatedCandidateVoteList) {
            //eliminate vote for the candidates, mark the preference as zero for eliminated candidate.
            v1.voteMap.put (eliminateCandidate, 0);
            // Check vote is still valid, that is if it has a preference for a valid candidate.
            //As per our logic, we have to check if all the candidates have vote 0, then vote is invalid.
            boolean voteValid = false;
            for (Map.Entry<Character,Integer> votecheck :v1.voteMap.entrySet ()){
                if(votecheck.getValue () != 0){
                    //If none of the votes have a non-zero value, then the vote is invalid now
                    //if any non-zero preference exists, then the vote is valid.
                    voteValid=true;
                    System.out.println ("Vote "+Integer.toString (v1.voteNumber)+" is still valid." );
                    break;
                }
            }
            if (voteValid) {
                // Vote is valid, move it to corresponding candidate
                Character changeToCandidate = ' ';
                ArrayList<Integer> aliveVotes = new ArrayList<Integer> ();
                //Fetch the preferences in the current vote which have valid candidates attached to them.
                for (Map.Entry<Character, Integer> vmap : v1.voteMap.entrySet ( )) {
                    if(vmap.getValue ()!=0){
                        aliveVotes.add (vmap.getValue ());
                    }
                }

                Collections.sort (aliveVotes);
                //By sorting, we will get the least number, which is the next preferred candidate of this vote.
                changeToCandidate=v1.getCandidateFromPreference (aliveVotes.get (0));
                System.out.println ("Moving the vote to next preferred candidate which is :"+changeToCandidate.toString () );
                ArrayList<Vote> temp1 = new ArrayList<Vote> (hm.get (changeToCandidate));
                temp1.add (v1);
                hm.put (changeToCandidate, temp1);
            }
            // Else is not required as vote is invalid now, entire map entry will be deleted.
        }
        //Remove entry from the map of the candidate who has been eliminated in the current round of voting.
        hm.remove (eliminateCandidate);

        //Invalidate vote for the eliminated candidate in all the active votes
        for(Map.Entry<Character,ArrayList<Vote>> activeVotes : hm.entrySet ()){
            for(Vote v : activeVotes.getValue ()){
                v.voteMap.put (eliminateCandidate,0);
            }
        }
    }

    /**
     * Provides the list of current active votes, that is the votes which have a preferred candidate who is still valid for winning
     * @return
     */
    public ArrayList<Vote> getCurrentActiveVotes (){
        ArrayList<Vote> tempArrayList = new ArrayList<Vote> ();
        for (Map.Entry<Character,ArrayList<Vote>> active : hm.entrySet () ){
            tempArrayList.addAll (active.getValue ());
        }
        return tempArrayList;
    }

    /**
     * Provides the count of current valid votes
     * @return
     */
    private int getCurrentVoteCount() {
        int voteCount = 0;
        for (Map.Entry<Character, ArrayList<Vote>> entry : hm.entrySet ( )) {
            simpleMap.put (entry.getKey ( ), hm.get (entry.getKey ( )).size ( ));
            voteCount = voteCount + (hm.get (entry.getKey ( )).size ( ));
        }
        return voteCount;
    }

    /**
     * Provides the latest valid candidate count.
     * @return
     */
    private int getCurrentCandidateCount() {
        return hm.keySet ().size ();
    }

    private void cleanup() {
        GlobalVoteList.clear ();
        hm.clear ();
        simpleMap.clear();
        Vote.voteCount=0;
    }
}

