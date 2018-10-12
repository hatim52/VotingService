package com.aconex.tech.api;

public interface VoteService {


    boolean validateVoteSequence(String voteseq);

    void casteVote(String voteseq);

    Integer getGlobalVoteListsize();


}
