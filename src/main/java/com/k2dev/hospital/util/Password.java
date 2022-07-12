package com.k2dev.hospital.util;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

/**
 * @author Kamal
 * Used for validating a password
 * Password policy:
 * Minimum 8 characters long
 * Maximum 20 characters long
 * At least one upper case letter
 * At least one lower case letter
 * At least one special symbol[@#$%^&+=-_!]
 * At least one digit
 */
@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface Password {
	String message() default "Password must contain atleast 8 charcters.It should contain:\n "
			+ "one uppercase letter\n one lowercase letter \n one digit \n one special symbol";
	Class<?>[] groups() default { };

	Class<? extends Payload>[] payload() default { };
}
