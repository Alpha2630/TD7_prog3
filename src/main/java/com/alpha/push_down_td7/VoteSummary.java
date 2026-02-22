package com.alpha.push_down_td7;

public class VoteSummary {
    private long validCount;
    private long blankCount;
    private long nullCount;

    public VoteSummary(long validCount, long blankCount, long nullCount) {
        this.validCount = validCount;
        this.blankCount = blankCount;
        this.nullCount = nullCount;
    }

    public long getValidCount() { return validCount; }
    public long getBlankCount() { return blankCount; }
    public long getNullCount() { return nullCount; }

    @Override
    public String toString() {
        return "VoteSummary(validCount=" + validCount +
                ", blankCount=" + blankCount +
                ", nullCount=" + nullCount + ")";
    }
}