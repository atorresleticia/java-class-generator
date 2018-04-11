package com.ws.calendar.calendartype;

import com.ws.commons.server.validation.beanvalidation.integration.BeanValidationIntegrated;
import com.ws.commons.server.validation.logicvalidation.LogicValidator;

import javax.enterprise.inject.Default;

/**
 * @author Leticia Torres {@literal <leticia.bertoldo@wssim.com.br>}
 * @since 6.1.0 18-03-27
 */

@Default
@BeanValidationIntegrated(CalendarType.class)
public class CalendarTypeValidation extends LogicValidator<CalendarType> {
}