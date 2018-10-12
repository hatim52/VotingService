package com.aconex.tech;

import com.aconex.tech.api.CandidateListService;
import com.aconex.tech.api.TallyService;
import com.aconex.tech.api.VoteService;
import com.aconex.tech.core.ServiceFactory;
import com.aconex.tech.core.Services;
import com.aconex.tech.api.CandidateList;
import com.aconex.tech.utilities.ArgumentValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class TallyServiceTest {
    File resourcesDirectory = new File("src/test/java/com/aconex/tech/resources/CandidateList.txt");


    @Before
    public void setUp() {
        final Services services = ServiceFactory.createServices(resourcesDirectory.getAbsolutePath ());
        CandidateListService candidateListService = services.getCandidateListService ();
        VoteService voteService = services.getVoteService ();
        TallyService tallyService = services.getTallyService ();
        candidateListService.readCandidateList (resourcesDirectory.getAbsolutePath ());
    }

   @Test
    public void getWinnerTest1(){
       final Services services = ServiceFactory.createServices(resourcesDirectory.getAbsolutePath ());
       CandidateListService candidateListService = services.getCandidateListService ();
       VoteService voteService = services.getVoteService ();
       TallyService tallyService = services.getTallyService ();
       candidateListService.readCandidateList (resourcesDirectory.getAbsolutePath ());

       voteService.casteVote ("ABCD");
       voteService.casteVote ("BAD");
       voteService.casteVote ("CABD");
       voteService.casteVote ("CDAB");
       voteService.casteVote ("DA");
       voteService.casteVote ("DB");
       voteService.casteVote ("BAC");
       voteService.casteVote ("CBAD");

       String Winner  = tallyService.getWinner ();
       ArgumentValidator.checkArgumentNullOrEmpty (Winner);
       Assert.assertEquals (Winner,"Ten pin bowling");
    }

    @Test
    public void getWinnerTest2(){
        final Services services = ServiceFactory.createServices(resourcesDirectory.getAbsolutePath ());
        CandidateListService candidateListService = services.getCandidateListService ();
        VoteService voteService = services.getVoteService ();
        TallyService tallyService = services.getTallyService ();
        candidateListService.readCandidateList (resourcesDirectory.getAbsolutePath ());

        voteService.casteVote ("ACD");
        voteService.casteVote ("BD");
        voteService.casteVote ("ABC");
        voteService.casteVote ("CD");
        voteService.casteVote ("B");
        voteService.casteVote ("ACDB");

        String Winner  = tallyService.getWinner ();
        ArgumentValidator.checkArgumentNullOrEmpty (Winner);
        Assert.assertEquals (Winner,"Winery tour");

    }
}
