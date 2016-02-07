package net.myacxy.retrotwitch.api;

public enum SortBy
{
    DEFAULT("created_at"),
    CREATED_AT("created_at"),
    LAST_BROADCAST("last_broadcast"),
    LOGIN("login");

    private String mSortBy;

    SortBy(String sortBy)
    {
        mSortBy = sortBy;
    }

    @Override
    public String toString()
    {
        return mSortBy;
    }
}
