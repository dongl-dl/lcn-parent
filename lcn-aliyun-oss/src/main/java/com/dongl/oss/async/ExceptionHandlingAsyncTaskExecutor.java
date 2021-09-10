package com.dongl.oss.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * 异常处理类异步任务执行器.
 *
 * @author D-L
 */
@Slf4j
public class ExceptionHandlingAsyncTaskExecutor implements AsyncTaskExecutor, InitializingBean, DisposableBean {

	private final AsyncTaskExecutor executor;

	/**
	 * 实例化一个新的异常处理异步任务执行器.
	 *
	 * @param executor the executor
	 */
	ExceptionHandlingAsyncTaskExecutor(AsyncTaskExecutor executor) {
		this.executor = executor;
	}

	/**
	 * 执行器 Execute.
	 *
	 * @param task the task
	 */
	@Override
	public void execute(Runnable task) {
		executor.execute(createWrappedRunnable(task));
	}

	/**
	 * 执行器 Execute.
	 *
	 * @param task         the task
	 * @param startTimeout the start timeout
	 */
	@Override
	public void execute(Runnable task, long startTimeout) {
		executor.execute(createWrappedRunnable(task), startTimeout);
	}

	private <T> Callable<T> createCallable(final Callable<T> task) {
		return () -> {
			try {
				return task.call();
			} catch (Exception e) {
				handle(e);
				throw e;
			}
		};
	}

	private Runnable createWrappedRunnable(final Runnable task) {
		return () -> {
			try {
				task.run();
			} catch (Exception e) {
				handle(e);
			}
		};
	}

	/**
	 * Handle.
	 *
	 * @param e the e
	 */
	private void handle(Exception e) {
		log.error("Caught async exception", e);
	}

	/**
	 * Submit future.
	 *
	 * @param task the task
	 *
	 * @return the future
	 */
	@Override
	public Future<?> submit(Runnable task) {
		return executor.submit(createWrappedRunnable(task));
	}

	/**
	 * Submit future.
	 *
	 * @param <T>  the type parameter
	 * @param task the task
	 *
	 * @return the future
	 */
	@Override
	public <T> Future<T> submit(Callable<T> task) {
		return executor.submit(createCallable(task));
	}

	/**
	 * Destroy.
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void destroy() throws Exception {
		if (executor instanceof DisposableBean) {
			DisposableBean bean = (DisposableBean) executor;
			bean.destroy();
		}
	}

	/**
	 * After properties set.
	 *
	 * @throws Exception the exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (executor instanceof InitializingBean) {
			InitializingBean bean = (InitializingBean) executor;
			bean.afterPropertiesSet();
		}
	}
}
