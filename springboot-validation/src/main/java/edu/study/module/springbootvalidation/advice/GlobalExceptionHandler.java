package edu.study.module.springbootvalidation.advice;

import edu.study.module.springbootvalidation.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author zl
 * @date 2021/1/24 11:41
 **/
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 请求方式不支持
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResultVO handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return ResultVO.error("不支持' " + e.getMethod() + "'请求");
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public ResultVO notFount(RuntimeException e) {
        log.error("运行时异常:", e);
        return ResultVO.error("运行时异常:" + e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public ResultVO handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ResultVO.error("服务器错误，请联系管理员");
    }


    /**
     * 校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResultVO exceptionHandler(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        StringBuilder errorMsg = new StringBuilder();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            String index = (bindingResult.getAllErrors().indexOf(fieldError) + 1) + ".";
            errorMsg.append(index).append(fieldError.getDefaultMessage()).append("! ");
        }
        return ResultVO.error(errorMsg.toString());
    }

    /**
     * 校验异常
     */
    @ExceptionHandler(value = BindException.class)
    public ResultVO validationExceptionHandler(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String errorMsg = "";
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMsg += fieldError.getDefaultMessage() + "!";
        }
        return ResultVO.error(errorMsg);
    }


    /**`
     * 校验异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResultVO constraintViolationExceptionHandler(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
        Iterator<ConstraintViolation<?>> iterator = constraintViolations.iterator();
        List<String> msgList = new ArrayList<>();
        while (iterator.hasNext()) {
            ConstraintViolation<?> cvl = iterator.next();
            msgList.add(cvl.getMessageTemplate());
        }
        return ResultVO.error(String.join(",", msgList));
    }

    /**
     * 权限校验失败 如果请求为ajax返回json，普通请求跳转页面
     */
//    @ExceptionHandler(AuthorizationException.class)
//    public Object handleAuthorizationException(HttpServletRequest request, AuthorizationException e) {
//        //log.error(e.getMessage(), e);
//        if (ServletUtils.isAjaxRequest(request)) {
//            return ResultVO.unauth(PermissionUtils.getMsg(e.getMessage()));
//        } else {
//            ModelAndView modelAndView = new ModelAndView();
//            modelAndView.setViewName("error/unauth");
//            return modelAndView;
//        }
//    }

    /**
     * 业务异常
     */
//    @ExceptionHandler(BusinessException.class)
//    public ResultVO businessException(BusinessException e) {
//        log.error(e.getMessage(), e);
//        return ResultVO.error(e.getMessage());
//    }

    /**
     * 演示模式异常
     */
//    @ExceptionHandler(DemoModeException.class)
//    public ResultVO demoModeException(DemoModeException e) {
//        return ResultVO.error("演示模式，不允许操作");
//    }

}
