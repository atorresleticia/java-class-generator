package com.ws.<api>.<entityPkg>;

import com.ws.commons.server.validation.beanvalidation.integration.BeanValidationIntegrated;
import com.ws.commons.server.validation.logicvalidation.LogicValidator;

import javax.enterprise.inject.Default;

/**
 * @author <author> {@literal <mail>}
 * @since <version> <date>
 */

@Default
@BeanValidationIntegrated(Entity.class)
public class <Entity>Validation extends LogicValidator<<Entity>> {
}