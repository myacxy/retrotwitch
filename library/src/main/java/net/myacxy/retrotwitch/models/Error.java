package net.myacxy.retrotwitch.models;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Error
{
    @SerializedName("error")
    public String error;
    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;

    public Error(String error, int status, String message) {
        this.error = error;
        this.status = status;
        this.message = message;
    }

    public Type getType() {
        return Type.fromStatus(status);
    }

    @Override
    public String toString()
    {
        return String.format(Locale.getDefault(), "{\"error\":\"%s\",\"status\":%d,\"message\":\"%s\"}", error, status, message);
    }

    public enum Type {
        NOT_FOUND(404),
        UNPROCESSABLE_ENTITY(422),
        SERVICE_UNAVAILABLE(503),
        UNEXPECTED(-1);

        private int mStatus;

        Type(int status) {
            mStatus = status;
        }

        public int getStatus() {
            return mStatus;
        }

        public static Type fromStatus(int status) {
            for (Type type : values())
            {
                if(type.getStatus() == status) return type;
            }
            return UNEXPECTED;
        }
    }
}
