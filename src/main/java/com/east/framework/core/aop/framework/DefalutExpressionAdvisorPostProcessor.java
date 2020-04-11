package com.east.framework.core.aop.framework;

import org.aopalliance.aop.Advice;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.AbstractAdvisingBeanPostProcessor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.InitializingBean;

/**
 * DefalutExpressionAdvisorPostProcessor Post Processor.
 * 
 * Updated on : 2015-11-11 Updated by : love.
 */
public class DefalutExpressionAdvisorPostProcessor extends AbstractAdvisingBeanPostProcessor implements InitializingBean {

	private static final long serialVersionUID = 1L;

	private String expression;

	private Advice userAdvice;

	@Override
	public void afterPropertiesSet() {

		AspectJExpressionPointcut pc = new AspectJExpressionPointcut();
		pc.setExpression(this.expression);

		this.advisor = new DefaultPointcutAdvisor(pc, this.userAdvice);

	}

	/**
	 * <pre>
	 * expression setter.
	 * </pre>
	 * 
	 * @param expression
	 *            expression
	 */
	public void setExpression(String expression) {
		this.expression = expression;
	}

	/**
	 * <pre>
	 * 사용자 Advice.
	 * </pre>
	 * 
	 * @param userAdvice
	 *            userAdvice
	 */
	public void setUserAdvice(Advice userAdvice) {
		this.userAdvice = userAdvice;
	}

}
