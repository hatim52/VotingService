package com.aconex.tech;

import com.aconex.tech.api.CandidateListService;
import com.aconex.tech.api.TallyService;
import com.aconex.tech.api.VoteService;
import com.aconex.tech.core.ServiceFactory;
import com.aconex.tech.core.Services;
import com.aconex.tech.api.CandidateList;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class CandidateServiceTest {

    File resourcesDirectory = new File("src/test/java/com/aconex/tech/resources/CandidateList.txt");

    @Test
    public void readCandidateListTest(){
        final Services services = ServiceFactory.createServices(resourcesDirectory.getAbsolutePath ());
        CandidateListService candidateListService = services.getCandidateListService ();
        candidateListService.readCandidateList (resourcesDirectory.getAbsolutePath ());
        VoteService voteService = services.getVoteService ();
        TallyService tallyService = services.getTallyService ();

        Assert.assertEquals (candidateListService.getCandidatesList ().get ('A'),"Winery tour" );
        Assert.assertEquals (candidateListService.getCandidatesList ().get ('B'),"Ten pin bowling" );
        Assert.assertEquals (candidateListService.getCandidatesList ().get ('C'),"Movie night" );
        Assert.assertEquals (candidateListService.getCandidatesList ().get ('D'),"Museum visit" );
        Assert.assertEquals (candidateListService.getCandidatesList ().get ('E'),null);
    }
/**
    @Test
    public void getCandidateNameTest(){
        CandidateList.readCandidateList (resourcesDirectory.getAbsolutePath ());
        Assert.assertEquals (CandidateList.getCandidateName ('A'),"Winery tour" );
        Assert.assertEquals (CandidateList.getCandidateName ('B'),"Ten pin bowling" );
        Assert.assertEquals (CandidateList.getCandidateName ('C'),"Movie night" );
        Assert.assertEquals (CandidateList.getCandidateName ('D'),"Museum visit" );
        Assert.assertEquals (CandidateList.getCandidateName ('E'),null );
    }
**/
    @Test
    public void getCandidateCountTest(){
        final Services services = ServiceFactory.createServices(resourcesDirectory.getAbsolutePath ());
        CandidateListService candidateListService = services.getCandidateListService ();
        candidateListService.readCandidateList (resourcesDirectory.getAbsolutePath ());

       // Assert.assertEquals (candidateListService.getCandidateCount(),4);
    }
}
