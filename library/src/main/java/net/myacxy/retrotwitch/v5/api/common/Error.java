package net.myacxy.retrotwitch.v5.api.common;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class Error {
    //<editor-fold desc="Member">
    @SerializedName("error")
    private String error;
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public Error(String error, int status, String message) {
        this.error = error;
        this.status = status;
        this.message = message;
    }
    //</editor-fold>

    //<editor-fold desc="Getter">
    public String getError() {
        return error;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public ErrorType getErrorType() {
        return ErrorType.fromStatus(status);
    }
    //</editor-fold>

    @Override
    public String toString() {
        return String.format(Locale.US, "{\"error\":\"%s\",\"status\":%d,\"message\":\"%s\"}", error, status, message);
    }

    //<editor-fold desc="Inner Classes">
    public enum ErrorType {
        NOT_FOUND(404),
        UNKNOWN_HOST(0),
        UNPROCESSABLE_ENTITY(422),
        SERVICE_UNAVAILABLE(503),
        UNEXPECTED(-1);

        private int mStatus;

        ErrorType(int status) {
            mStatus = status;
        }

        public static ErrorType fromStatus(int status) {
            for (ErrorType errorType : values()) {
                if (errorType.getStatus() == status) return errorType;
            }
            return UNEXPECTED;
        }

        public int getStatus() {
            return mStatus;
        }
    }
    //</editor-fold>
}
