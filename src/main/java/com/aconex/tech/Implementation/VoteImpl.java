package com.aconex.tech.Implementation;

import com.aconex.tech.api.CandidateList;
import com.aconex.tech.api.Vote;
import com.aconex.tech.api.VoteService;
import com.aconex.tech.core.AbstractService;
import com.aconex.tech.core.ServiceFactory;
import com.aconex.tech.core.Services;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class VoteImpl  extends AbstractService implements VoteService {


    public static ArrayList<Vote> GlobalVoteList = new ArrayList<Vote> ();
    public HashMap<Character, Integer> voteMap;

    private int candidateCount = services.getCandidateListService ().getCandidateCount ();
    private HashMap<Character, Integer> voteMapTemplate;
    public VoteImpl(Services services,String fileLocation) {
        super (services,fileLocation);
    }


    public void casteVote(String voteseq) {
        generateVoteMapTemplate();
        Vote vote = new Vote (voteseq,voteMapTemplate);
        GlobalVoteList.add (vote);
    }
    /**
     * Validates whether the votesequence provided by user is valid or not.
     * @param voteseq
     * @return
     */
    public boolean validateVoteSequence(String voteseq) {
        generateVoteMapTemplate();
        //Check if vote has any duplicate characters
        // An integer to store presence/absence
        // of 26 characters using its 32 bits.
        int checker = 0;
        for (int i = 0; i < voteseq.length ( ); ++i) {
            int val = (voteseq.charAt (i) - 'a');
            // If bit corresponding to current
            // character is already set
            if ((checker & (1 << val)) > 0)
                return false;
            // set bit in checker
            checker |= (1 << val);
            //Check if vote has any characters other than candidate list
            if (!voteMapTemplate.containsKey (voteseq.charAt (i)))
                return false;
        }
        return true;
    }

    public Integer getGlobalVoteListsize(){
        return GlobalVoteList.size ();
    }
    private void generateVoteMapTemplate() {
        voteMapTemplate = new HashMap<Character, Integer> (services.getCandidateListService ( ).getVotingTemplate ( ));
    }
}

