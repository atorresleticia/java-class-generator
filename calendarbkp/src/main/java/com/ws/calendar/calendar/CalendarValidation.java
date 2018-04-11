package com.ws.calendar.calendar;

import com.ws.commons.server.validation.beanvalidation.integration.BeanValidationIntegrated;
import com.ws.commons.server.validation.logicvalidation.LogicValidator;

import javax.enterprise.inject.Default;

/**
 * @author Leticia Torres {@literal leticia.bertoldo@wssim.com.br}
 * @since 1.0.0 2018-04-10
 */

@Default
@BeanValidationIntegrated(Entity.class)
public class <Entity>Validation extends LogicValidator<<Entity>> {
}
