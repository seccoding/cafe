package com.ktdsuniversity.edu.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect // AOP 모듈을 정의하는 애노테이션
        // 특정 기능의 전/후/예외발생시 동작해야 할 공통 로직을 개발할 때 유용.
@Component // Spring Bean으로 정의하는 애노테이션
           // @Controller, @Service, @Repository의 부모 애노테이션
           // Spring Bean으로 등록하려는 클래스가
           // Controller, Service, Repository의 성격이 아닐 때 사용.
public class TimingAspect {

	private Logger logger = LoggerFactory.getLogger(TimingAspect.class);
	
	/**
	 * AOP가 개입할 범위를 지정.
	 * 접근제어지시자: public
	 * 반환타입: * (모든 반환타입)
	 * 패키지: com.ktdsuniversity.edu 밑에 있는 모든 service 패키지
	 * 클래스: 해당 패키지 안에 있는 ServiceImpl로 끝나는 모든 클래스
	 * 메소드: 모든 메소드.  *(..)
	 */
	@Pointcut("execution(public * com.ktdsuniversity.edu..service.*ServiceImpl.*(..))")
	private void aroundTarget() {}
	
	@Pointcut("execution(public * com.ktdsuniversity.edu..dao.*Impl.*(..))")
	private void aroundDaoTarget() {}
	
	/**
	 * Pointcut으로 정의한 범위의 모든 대상들을 AOP가 개입하도록 한다.
	 * 여기에서 정의된 코드가 개입된다.
	 * 
	 * 원래 실행될 메소드의 전, 후에 공통코드를 실행한다.
	 * @param pjp 원래 실행될 클래스와 메소드의 정보.
	 * @return 원래 실행될 메소드의 반환 값
	 * @throws Throwable 
	 */
	@Around("aroundTarget()")
	public Object timingAdvice(ProceedingJoinPoint pjp) throws Throwable {
		
		// 원래 실행될 메소드의 반환값을 저장할 변수.
		Object result = null;
		
		// 메소드 경과 시간을 체크하기 위해 StopWatch 생성.
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		// =======================================
		// 위 까지가 원래 실행될 메소드의 실행 전에 수행할 코드.
		
		// 여기부터 원래 실행될 메소드를 실행 시킴.
		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			// 원래 실행될 메소드가 예외를 던졌을 때 
			// AOP가 개입할 코드를 여기에 작성.
			throw e;
		} finally {
			
			// 여기 부터는 원래 실행될 메소드가 실행 완료되었을 때 수행되는 코드.
			stopWatch.stop();
			
			// 원래 실행되어야 할 클래스의 정보를 추출.
			String classPath = pjp.getTarget().getClass().getName();
			// 원래 실행되어야 할 메소드의 정보를 추출.
			String methodName = pjp.getSignature().getName();
			
			logger.debug("{}.{} 걸린시간: {}밀리초", 
					       classPath, 
					       methodName, 
					       stopWatch.getLastTaskTimeMillis());
			
		}
		
		return result;
	}
	
	@Around("aroundDaoTarget()")
	public Object daoTimingAdvice(ProceedingJoinPoint pjp) throws Throwable {
		
		// 원래 실행될 메소드의 반환값을 저장할 변수.
		Object result = null;
		
		// 메소드 경과 시간을 체크하기 위해 StopWatch 생성.
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		// =======================================
		// 위 까지가 원래 실행될 메소드의 실행 전에 수행할 코드.
		
		// 여기부터 원래 실행될 메소드를 실행 시킴.
		try {
			result = pjp.proceed();
		} catch (Throwable e) {
			// 원래 실행될 메소드가 예외를 던졌을 때 
			// AOP가 개입할 코드를 여기에 작성.
			throw e;
		} finally {
			
			// 여기 부터는 원래 실행될 메소드가 실행 완료되었을 때 수행되는 코드.
			stopWatch.stop();
			
			// 원래 실행되어야 할 클래스의 정보를 추출.
			String classPath = pjp.getTarget().getClass().getName();
			// 원래 실행되어야 할 메소드의 정보를 추출.
			String methodName = pjp.getSignature().getName();
			
			logger.debug("{}.{} 걸린시간: {}밀리초", 
					       classPath, 
					       methodName, 
					       stopWatch.getLastTaskTimeMillis());
			
		}
		
		return result;
	}
	
}
