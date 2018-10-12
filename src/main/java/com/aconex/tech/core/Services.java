package com.aconex.tech.core;

import com.aconex.tech.api.CandidateList;
import com.aconex.tech.api.CandidateListService;
import com.aconex.tech.api.TallyService;
import com.aconex.tech.api.VoteService;

public interface Services {
    CandidateListService getCandidateListService();

    VoteService getVoteService();

    TallyService getTallyService();
}
