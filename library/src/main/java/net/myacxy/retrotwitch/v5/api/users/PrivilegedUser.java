package net.myacxy.retrotwitch.v5.api.users;

import com.google.gson.annotations.SerializedName;

public class PrivilegedUser extends SimpleUser {

    @SerializedName("email")
    private String email;
    @SerializedName("email_verified")
    private boolean emailVerified;
    @SerializedName("notifications")
    private Notifications notifications;
    @SerializedName("partnered")
    private boolean partnered;
    @SerializedName("twitter_connected")
    private boolean twitterConnected;

    public String getEmail() {
        return email;
    }

    public boolean getEmailVerified() {
        return emailVerified;
    }

    public Notifications getNotifications() {
        return notifications;
    }

    public boolean getPartnered() {
        return partnered;
    }

    public boolean getTwitterConnected() {
        return twitterConnected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        PrivilegedUser that = (PrivilegedUser) o;

        if (emailVerified != that.emailVerified) return false;
        if (partnered != that.partnered) return false;
        if (twitterConnected != that.twitterConnected) return false;
        if (!email.equals(that.email)) return false;
        return notifications.equals(that.notifications);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (emailVerified ? 1 : 0);
        result = 31 * result + notifications.hashCode();
        result = 31 * result + (partnered ? 1 : 0);
        result = 31 * result + (twitterConnected ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PrivilegedUser{");
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
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Notifications that = (Notifications) o;

            if (email != that.email) return false;
            return push == that.push;

        }

        @Override
        public int hashCode() {
            int result = (email ? 1 : 0);
            result = 31 * result + (push ? 1 : 0);
            return result;
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
