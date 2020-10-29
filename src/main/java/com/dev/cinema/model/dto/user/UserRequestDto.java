package com.dev.cinema.model.dto.user;

import com.dev.cinema.annotation.CustomEmailConstraint;
import com.dev.cinema.annotation.FieldsValueMatch;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class UserRequestDto {
    @CustomEmailConstraint
    private String email;
    @Size(min = 4)
    private String password;
    @Size(min = 4)
    private String repeatPassword;
}
