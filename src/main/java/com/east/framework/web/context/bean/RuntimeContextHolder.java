package com.east.framework.web.context.bean;

/**
 * Runtime Context Holder
 * 
 * Updated on : 2015-11-12 Updated by : love.
 */
public class RuntimeContextHolder {
	private static ThreadLocal<RuntimeContext> context = new ThreadLocal<RuntimeContext>();

	/**
	 * RuntimeContext Setter.
	 * 
	 * @return RuntimeContext 런타임 컨택스트
	 */
	public static RuntimeContext getRuntimeContext() {
		return context.get();
	}

	/**
	 * RuntimeContext Setter.
	 * 
	 * @param runtimeContext
	 *            런타임 컨택스트
	 */
	public static void setRuntimeContext(RuntimeContext runtimeContext) {
		context.set(runtimeContext);
	}

	/**
	 * RuntimeContext remove.
	 */
	public static void removeRuntimeContext() {
		context.remove();
	}

}
