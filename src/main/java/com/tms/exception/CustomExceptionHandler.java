package com.tms.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice //говорит Spring что это глобальный обработчик исключений
public class CustomExceptionHandler {

    @ExceptionHandler(SecurityNotFound.class)
    public ModelAndView securityNotFoundExceptionHandler(SecurityNotFound e) {
        System.out.println("ExceptionHandler: " + e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errors", e.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception e) {
        System.out.println("ExceptionHandler: " + e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("errors", e.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
