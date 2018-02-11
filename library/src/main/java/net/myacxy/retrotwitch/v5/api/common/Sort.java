package net.myacxy.retrotwitch.v5.api.common;

public enum Sort {
    DEFAULT("time"),
    VIEWS("views"),
    TIME("time");

    private String sort;

    Sort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return sort;
    }
}
