package com.east.framework.core.util.schedule;

/**
 * 스케쥴링 서비스의 공통 코딩을 위한 Abstract 서비스.
 * 
 * Updated on : 2015-10-13 Updated by : love.
 */
public abstract class AbstractScheduleService {

	/**
	 * 스케쥴 실행.
	 */
	public void run() {
		if (this.isRunAble()) {
			before();
			excute();
			after();
		}
	}

	/**
	 * 배치 실행 여부.
	 * 
	 * @return true : 실행, false : skip
	 */
	protected abstract boolean isRunAble();

	/**
	 * 배치 실행 전 준비 작업.
	 * 
	 */
	protected abstract void before();

	/**
	 * 배치 실행 구문.
	 */
	protected abstract void excute();

	/**
	 * 배치 실행 후 작업.
	 */
	protected abstract void after();

}
