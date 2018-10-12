package com.aconex.tech;

import com.aconex.tech.api.*;
import com.aconex.tech.core.ServiceFactory;
import com.aconex.tech.core.Services;
import com.aconex.tech.utilities.ArgumentValidator;

import java.util.ArrayList;
import java.util.Scanner;

public class MainProgram {
    /**
     *Main method to start
     */
    public static void main(String[] args) {

        //Take input list of candidates
        if(args.length == 0){
            System.out.println ("Please pass location of the candidates list" );
        }
        String fileLocation=args[0];
      //  String fileLocation = "C:\\Users\\hjawadwa.ORADEV\\IdeaProjects\\VotingService\\src\\test\\java\\com\\aconex\\tech\\resources\\CandidateList.txt";
        ArgumentValidator.checkArgumentNullOrEmpty (fileLocation);

        final Services services = ServiceFactory.createServices(fileLocation);

        CandidateListService candidateListService = services.getCandidateListService ();
        VoteService voteService = services.getVoteService ();
        TallyService tallyService = services.getTallyService ();

        String vote;
        try {
            //Creating candidate list by reading the candidates from the file location.

            do {
                candidateListService.readCandidateList (fileLocation);
                System.out.println ("Please provide your vote in order of preference:\n");
                candidateListService.displayCandidatesList ( );
                System.out.println (">");
                Scanner inputReader = new Scanner (System.in);
                //Read the vote, clear extra characters and convert it to upper case.
                vote = inputReader.nextLine ( ).toUpperCase ().trim ();
                ArgumentValidator.checkArgumentNullOrEmpty (vote);           // Check if the vote is valid, and register it, if not, ignore it.
                //Tally is the keyword used to start the vote counting
                if ((voteService.validateVoteSequence (vote)) && (!(vote.equals ("TALLY")))) {
                   voteService.casteVote (vote);
                    System.out.println ("vote registered as " + vote + "\n");
                }
                else {
                    if (!vote.equals ("TALLY")) {
                        System.out.println ("The vote you have entered is not valid, please try again.");
                    }
                }
            } while (!vote.equals ("TALLY"));

            // At this point, voting is completed and we need to proceed to tally the votes.

            if (vote.equals ("TALLY")){
                //Check if any votes have been registered before starting the tally
                if(Vote.voteCount==0){
                    System.out.println ("We are not able to find any valid registered votes to tally." );
                    System.out.println ("Please start again." );
                    throw new IllegalStateException ("Could not find any votes to tally");
                }
                //Calling the getWinner Method of Tally class by passing the ArrayList of votes.
                String Winner = tallyService.getWinner ();
                ArgumentValidator.checkArgumentNullOrEmpty (Winner);
                System.out.println ("And the winner is...... " + Winner );
            }
        }catch (Exception e){
            e.printStackTrace();
            System.exit (1);
        }

    }
}

