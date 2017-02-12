package net.myacxy.retrotwitch.v5.api.users;

import com.google.gson.annotations.SerializedName;

public class PrivilegedUser extends SimpleUser {

    @SerializedName("email")
    private String email;
    @SerializedName("email_verified")
    private Boolean emailVerified;
    @SerializedName("notifications")
    private Notifications notifications;
    @SerializedName("partnered")
    private Boolean partnered;
    @SerializedName("twitter_connected")
    private Boolean twitterConnected;

    public String getEmail() {
        return email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public Notifications getNotifications() {
        return notifications;
    }

    public Boolean getPartnered() {
        return partnered;
    }

    public Boolean getTwitterConnected() {
        return twitterConnected;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SimpleUser{");
        sb.append("id='").append(id).append('\'');
        sb.append(", bio='").append(bio).append('\'');
        sb.append(", createdAt='").append(createdAt).append('\'');
        sb.append(", displayName='").append(displayName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", emailVerified=").append(emailVerified);
        sb.append(", logo='").append(logo).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", notifications=").append(notifications);
        sb.append(", partnered=").append(partnered);
        sb.append(", twitterConnected=").append(twitterConnected);
        sb.append(", type='").append(type).append('\'');
        sb.append(", updatedAt='").append(updatedAt).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public class Notifications {
        @SerializedName("email")
        private boolean email;
        @SerializedName("push")
        private boolean push;

        public boolean isEmail() {
            return email;
        }

        public boolean isPush() {
            return push;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("Notifications{");
            sb.append("email=").append(email);
            sb.append(", push=").append(push);
            sb.append('}');
            return sb.toString();
        }
    }
}
