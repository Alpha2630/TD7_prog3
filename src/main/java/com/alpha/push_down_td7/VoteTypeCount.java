package com.alpha.push_down_td7;



public class VoteTypeCount {
    private String voteType;
    private long count;

    public VoteTypeCount(String voteType, long count) {
        this.voteType = voteType;
        this.count = count;
    }

    public String getVoteType() { return voteType; }
    public long getCount() { return count; }

    @Override
    public String toString() {
        return "VoteTypeCount(voteType=" + voteType + ", count=" + count + ")";
    }
}
