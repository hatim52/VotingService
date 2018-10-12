package com.aconex.tech.core;


import com.aconex.tech.Implementation.CandidateListImpl;
import com.aconex.tech.Implementation.TallyImpl;
import com.aconex.tech.Implementation.VoteImpl;
import com.aconex.tech.api.CandidateListService;
import com.aconex.tech.api.TallyService;
import com.aconex.tech.api.VoteService;

/**
 * Factory for building a new memory-based Voting service.
 */
public class ServiceFactory implements Services {
    private final CandidateListService candidateListService;
    private final VoteService voteService;
    private final TallyService tallyService;
    public static String fileLoc = null;
    public static Services createServices(String fileLocation) {
        fileLoc=fileLocation;
        return new ServiceFactory(fileLoc);
    }

    private ServiceFactory (String fileLoc) {
        this.fileLoc = fileLoc;
        candidateListService = new CandidateListImpl (this,fileLoc);
        voteService = new VoteImpl (this,fileLoc);
        tallyService = new TallyImpl (this,fileLoc);
    }

    public CandidateListService getCandidateListService() { return candidateListService; }

    public VoteService getVoteService() {
        return voteService;
    }

    public TallyService getTallyService() {
        return tallyService;
    }
}
