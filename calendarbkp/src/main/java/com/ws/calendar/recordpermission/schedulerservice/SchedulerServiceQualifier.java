package com.ws.calendar.recordpermission.schedulerservice;

import javax.inject.Qualifier;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Qualifier class used to allow specifics Cdi behaviors during scheduler actions.
 *
 * @author Evaristo W. Benfatti
 * @since 1.0.0 Created on 10/05/2017.
 */
@Qualifier
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@interface SchedulerServiceQualifier {
}
