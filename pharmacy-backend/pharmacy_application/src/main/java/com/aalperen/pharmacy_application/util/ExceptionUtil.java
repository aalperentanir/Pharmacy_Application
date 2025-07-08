package com.aalperen.pharmacy_application.util;

import com.aalperen.pharmacy_application.exception.BusinessException;
import com.aalperen.pharmacy_application.response.generic.ReturnCodes;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtil {

    public void handleException(Exception ex) {
        if (ex instanceof BusinessException e) {
            throw new BusinessException(e.getMessage(),
                    e.getCode(), ex.getMessage());
        }
        throw new BusinessException(ex.getMessage(),
                ReturnCodes.INTERNAL_SERVER_ERROR.intValue(), ex.getMessage());
    }
}
