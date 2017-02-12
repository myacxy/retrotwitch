package net.myacxy.retrotwitch.v5.api.common;

public enum Scope {
    USER_READ("user_read"),
    USER_BLOCKS_EDIT("user_blocks_edit"),
    USER_BLOCKS_READ("user_blocks_read"),
    USER_FOLLOWS_EDIT("user_follows_edit"),
    CHANNEL_READ("channel_read"),
    CHANNEL_EDITOR("channel_editor"),
    CHANNEL_COMMERCIAL("channel_commercial"),
    CHANNEL_STREAM("channel_stream"),
    CHANNEL_SUBSCRIPTIONS("channel_subscriptions"),
    USER_SUBSCRIPTIONS("user_subscriptions"),
    CHANNEL_CHECK_SUBSCRIPTION("channel_check_subscription"),
    CHAT_LOGIN("chat_login");

    private String mScope;

    Scope(String scope) {
        mScope = scope;
    }

    @Override
    public String toString() {
        return mScope;
    }
}
