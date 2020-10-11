package com.study.module.springbooteasyexcel.util;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * @author Administrator
 * @date 2020/10/11 下午 2:51
 */
public class ValidationUtils {
    public static Validator getValidator() {
        return validator;
    }

    static Validator validator;

    static {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }
}
