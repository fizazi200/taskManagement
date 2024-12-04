package kata.tasks.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {

    @JsonProperty("En cours")
    IN_PROGRESS("En cours"), // Par défaut

    @JsonProperty("Terminée")
    COMPLETED("Terminée"),

    @JsonProperty("Annulée")
    CANCELLED("Annulée");


    private final String value;

    Status(String s) {
        this.value=s;
    }

    public String getStatusTask() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static Status fromValue(String value) {
        for (Status status : values()) {
            if (status.value.equals (value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}
