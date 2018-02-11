package net.myacxy.retrotwitch.v5.api.common;

import com.google.gson.annotations.SerializedName;

import java.util.Locale;

public class RetroTwitchError {
    //<editor-fold desc="Member">
    @SerializedName("error")
    private String error;
    @SerializedName("status")
    private int status;
    @SerializedName("message")
    private String message;
    private Throwable cause;
    //</editor-fold>

    //<editor-fold desc="Constructor">
    public RetroTwitchError(String error, int status, String message) {
        this.error = error;
        this.status = status;
        this.message = message;
    }

    public RetroTwitchError(String error, int status, String message, Throwable cause) {
        this(error, status, message);
        this.cause = cause;
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

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
    //</editor-fold>

    @Override
    public String toString() {
        return String.format(Locale.US, "{\"error\":\"%s\",\"status\":%d,\"message\":\"%s\"}", error, status, message);
    }

    //<editor-fold desc="Inner Classes">
    public enum ErrorType {
        UNEXPECTED(-100),
        UNKNOWN_HOST(-99),
        BAD_REQUEST(400),
        NOT_FOUND(404),
        UNPROCESSABLE_ENTITY(422),
        SERVICE_UNAVAILABLE(503);

        private int status;

        ErrorType(int status) {
            this.status = status;
        }

        public static ErrorType fromStatus(int status) {
            for (ErrorType errorType : values()) {
                if (errorType.getStatus() == status) return errorType;
            }
            return UNEXPECTED;
        }

        public int getStatus() {
            return status;
        }
    }
    //</editor-fold>
}
