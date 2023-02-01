package com.zhaizq.framework.utils.validate;

import com.zhaizq.framework.common.exception.BusinessException;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

public class ValidateUtil {
    private final static Validator validator;

    static {
        validator = Validation
                .byProvider(HibernateValidator.class)
                .configure()
                .failFast(true) // true:快速模式 false:普通模式
                .buildValidatorFactory()
                .getValidator();
    }

    public static <T> void validate(T value) throws BusinessException {
        System.out.println(123);
        Set<ConstraintViolation<T>> result = validator.validate(value);

        if (result != null && result.size() > 0)
            for (ConstraintViolation<T> c : result)
                throw new BusinessException("字段[" + c.getPropertyPath() + "]" + c.getMessage());
    }
}