package com.tms.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    //Этот метод возвращает массив классов конфигурации всего ApplicationContext
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    //Этот метод возвращает массив классов конфигурации всего ApplicationContext
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{SpringConfig.class};
    }

    //На какие пути мы должны отвечать
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
