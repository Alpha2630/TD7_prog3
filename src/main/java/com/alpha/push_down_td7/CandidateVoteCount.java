package com.alpha.push_down_td7;

public class CandidateVoteCount {
    private String candidateName;
    private long validVoteCount;

    public CandidateVoteCount(String candidateName, long validVoteCount) {
        this.candidateName = candidateName;
        this.validVoteCount = validVoteCount;
    }

    public String getCandidateName() { return candidateName; }
    public long getValidVoteCount() { return validVoteCount; }

    @Override
    public String toString() {
        return candidateName + "=" + validVoteCount;
    }
}
