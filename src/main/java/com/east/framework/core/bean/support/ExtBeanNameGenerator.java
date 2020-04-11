/*
 * Copyright (c) 2015 by east.
 * All right reserved.
 */
package com.east.framework.core.bean.support;

import java.beans.Introspector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;

/**
 * Bean 이름을 생성할때 Full name 기준으로 생성한다.
 *
 * Updated on : 2015-09-25 Updated by : love.
 */
public class ExtBeanNameGenerator implements BeanNameGenerator {

	/** The logger. */
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.beans.factory.support.BeanNameGenerator#generateBeanName
	 * (org.springframework.beans.factory .config.BeanDefinition,
	 * org.springframework.beans.factory.support.BeanDefinitionRegistry)
	 */
	@Override
	public String generateBeanName(BeanDefinition definition, BeanDefinitionRegistry registry) {
		String beanName = Introspector.decapitalize(definition.getBeanClassName());
		logger.info("ExtBeanNameGenerator.generateBeanName : {}", beanName);

		return beanName;
	}

}
