package com.example.trainerworkloadservice.validators;

import com.example.trainerworkloadservice.constraints.CustomDateTimeConstraint;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DateTimeFormatValidator implements ConstraintValidator<CustomDateTimeConstraint, String> {

    @Override
    public boolean isValid(String dateTimeValue, ConstraintValidatorContext context) {
        if (dateTimeValue == null) {
            return true;
        }

        try {
            LocalDateTime date = LocalDateTime.parse(dateTimeValue);
            log.debug("DateTime validation succeeded for: {}", date);
            return true;
        } catch (DateTimeParseException e) {
            log.debug("DateTime validation failed for value: {}", dateTimeValue, e);
            return false;
        }
    }
}
