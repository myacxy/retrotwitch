package net.myacxy.retrotwitch.v5.api.channels;

public enum BroadcastType {
    DEFAULT(null),
    ARCHIVE("archive"),
    HIGHLIGHT("highlight"),
    UPLOAD("upload");

    private String broadcastType;

    BroadcastType(String broadcastType) {
        this.broadcastType = broadcastType;
    }

    @Override
    public String toString() {
        return broadcastType;
    }
}
