package com.aconex.tech;

import com.aconex.tech.Implementation.VoteImpl;
import com.aconex.tech.api.*;
import com.aconex.tech.core.ServiceFactory;
import com.aconex.tech.core.Services;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import java.io.File;


public class VoteServiceTest {
    File resourcesDirectory = new File("src/test/java/com/aconex/tech/resources/CandidateList.txt");

    @Before
    public void setUp(){
        final Services services = ServiceFactory.createServices(resourcesDirectory.getAbsolutePath ());
        CandidateListService candidateListService = services.getCandidateListService ();
        VoteService voteService = services.getVoteService ();
        candidateListService.readCandidateList (resourcesDirectory.getAbsolutePath ());
    }
    @Test
    public void casteVoteTest1() {
        final Services services = ServiceFactory.createServices(resourcesDirectory.getAbsolutePath ());
        CandidateListService candidateListService = services.getCandidateListService ();
        VoteService voteService = services.getVoteService ();
        candidateListService.readCandidateList (resourcesDirectory.getAbsolutePath ());

        String sampleVote = "ABCD";
        voteService.casteVote (sampleVote);
        if (services.getVoteService ().getGlobalVoteListsize ()> 0) {
            System.out.println ("Test successful" );
        }
    }

    @Test
    public void casteVoteTest2() {
        final Services services = ServiceFactory.createServices(resourcesDirectory.getAbsolutePath ());
        CandidateListService candidateListService = services.getCandidateListService ();
        VoteService voteService = services.getVoteService ();
        candidateListService.readCandidateList (resourcesDirectory.getAbsolutePath ());
        String sampleVote = "ABCD";
        Vote v1=new Vote  (sampleVote,services.getCandidateListService ( ).getVotingTemplate ( ));
        Assert.assertEquals (Integer.parseInt (String.valueOf (v1.voteMap.get ('A'))), 1);
        Assert.assertEquals (Integer.parseInt (String.valueOf (v1.voteMap.get ('B'))), 2);
        Assert.assertEquals (Integer.parseInt (String.valueOf (v1.voteMap.get ('C'))), 3);
        Assert.assertEquals (Integer.parseInt (String.valueOf (v1.voteMap.get ('D'))), 4);
    }

    @Test
    public void casteVoteTest3() {
        final Services services = ServiceFactory.createServices(resourcesDirectory.getAbsolutePath ());
        CandidateListService candidateListService = services.getCandidateListService ();
        VoteService voteService = services.getVoteService ();
        candidateListService.readCandidateList (resourcesDirectory.getAbsolutePath ());
        String sampleVote = "BA";
        Vote v3=new Vote  (sampleVote,services.getCandidateListService ( ).getVotingTemplate ( ));
        Assert.assertEquals (Integer.parseInt (String.valueOf (v3.voteMap.get ('A'))), 2);
        Assert.assertEquals (Integer.parseInt (String.valueOf (v3.voteMap.get ('B'))), 1);
        System.out.println (String.valueOf (v3.voteMap.get ('C')));
        Assert.assertEquals (Integer.parseInt (String.valueOf (v3.voteMap.get ('C'))), 0);
        Assert.assertEquals (Integer.parseInt (String.valueOf (v3.voteMap.get ('D'))), 0);
    }

    @Test
    public void casteVoteTest4() {
        final Services services = ServiceFactory.createServices(resourcesDirectory.getAbsolutePath ());
        CandidateListService candidateListService = services.getCandidateListService ();
        VoteService voteService = services.getVoteService ();
        candidateListService.readCandidateList (resourcesDirectory.getAbsolutePath ());
        String sampleVote = "";
        Vote v4=new Vote  (sampleVote,services.getCandidateListService ( ).getVotingTemplate ( ));
        Assert.assertEquals (Integer.parseInt (String.valueOf (v4.voteMap.get ('A'))), 0);
        Assert.assertEquals (Integer.parseInt (String.valueOf (v4.voteMap.get ('B'))), 0);
        Assert.assertEquals (Integer.parseInt (String.valueOf (v4.voteMap.get ('C'))), 0);
        Assert.assertEquals (Integer.parseInt (String.valueOf (v4.voteMap.get ('D'))), 0);
    }

    @Test
    public void validateVoteSequenceTest() {
        final Services services = ServiceFactory.createServices(resourcesDirectory.getAbsolutePath ());
        CandidateListService candidateListService = services.getCandidateListService ();
        VoteService voteService = services.getVoteService ();
        candidateListService.readCandidateList (resourcesDirectory.getAbsolutePath ());

        String sampleVote = "ABCD";
        Assert.assertEquals (services.getVoteService ().validateVoteSequence (sampleVote),true);

        String sampleVotea = "A";
        Assert.assertEquals (services.getVoteService ().validateVoteSequence (sampleVotea),true);

        String sampleVoteb = "D";
        Assert.assertEquals (services.getVoteService ().validateVoteSequence (sampleVoteb),true);

        String sampleVotec = "AC";
        Assert.assertEquals (services.getVoteService ().validateVoteSequence (sampleVotec),true);

        String sampleVoted = "BAD";
        Assert.assertEquals (services.getVoteService ().validateVoteSequence (sampleVoted),true);

        String sampleVote1 = "AABC";
        Assert.assertEquals (services.getVoteService ().validateVoteSequence (sampleVote1),false);

        String sampleVote2 = "XAFK";
        Assert.assertEquals (services.getVoteService ().validateVoteSequence (sampleVote2),false);

        String sampleVote3 = "XAFK";
        Assert.assertEquals (services.getVoteService ().validateVoteSequence (sampleVote3),false);

        String sampleVote4 = "!#^*&(*^($";
        Assert.assertEquals (services.getVoteService ().validateVoteSequence (sampleVote4),false);

        String sampleVote5 = "1234";
        Assert.assertEquals (services.getVoteService ().validateVoteSequence (sampleVote5),false);

        String sampleVote6 = "XAFK";
        Assert.assertEquals (services.getVoteService ().validateVoteSequence (sampleVote6),false);
    }

    @Test
    public void getCandidateFromPreferenceTest(){
        final Services services = ServiceFactory.createServices(resourcesDirectory.getAbsolutePath ());
        CandidateListService candidateListService = services.getCandidateListService ();
        VoteService voteService = services.getVoteService ();
        candidateListService.readCandidateList (resourcesDirectory.getAbsolutePath ());

        String sampleVote = "ABCD";
        Vote v1=new Vote  (sampleVote,services.getCandidateListService ( ).getVotingTemplate ( ));
        Assert.assertEquals (String.valueOf (v1.getCandidateFromPreference (1)).charAt (0),'A');
        Assert.assertEquals (String.valueOf (v1.getCandidateFromPreference (2)).charAt (0),'B');
        Assert.assertEquals (String.valueOf (v1.getCandidateFromPreference (3)).charAt (0),'C');
        Assert.assertEquals (String.valueOf (v1.getCandidateFromPreference (4)).charAt (0),'D');
    }
}