package kata.tasks.config;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import kata.tasks.model.Status;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {

    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status == null) {
            return null;
        }
        return status.getStatusTask();
    }

    @Override
    public Status convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        return Status.fromValue(dbData);
    }
}

