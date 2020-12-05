package com.lizhihai.miaosha.validator.isMobile;

import com.lizhihai.miaosha.util.ValidatorUtil;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {
    private boolean require=false;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (require){
            return ValidatorUtil.isMobile(s);
        }else{
            if (StringUtils.isEmpty(s)){
                return true;
            }else{
                return ValidatorUtil.isMobile(s);
            }
        }
    }

    @Override
    public void initialize(IsMobile constraintAnnotation) {
        require=constraintAnnotation.required();
    }
}
