package org.globaltester.base;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class ExecutorServiceHelperTest {

	@Test
	public void doTest() {
		System.out.println("Begin: " + Instant.now());
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		List<Integer> numbers = IntStream.rangeClosed(1, 4).boxed().collect(Collectors.toList());
		for (Integer number : numbers) {
			executorService.submit(() -> {
				Thread.currentThread().setName("Thread #" + number);
				try {
					Thread.sleep(Duration.ofSeconds(1).toMillis());
				} catch (InterruptedException e) {
					System.out.println("Sleep interrupted in " + Thread.currentThread().getName());
					// Preserve interrupt status
					Thread.currentThread().interrupt();
					return;
				}
				System.out.println("Finished " + Thread.currentThread().getName() + ": " + Instant.now());
			});
		}

		//ExecutorServiceHelper.shutdownAndAwaitTermination(
		//	executorService, ExecutorServiceHelper.DEFAULT_WAIT_FOR_TERMINATION_DURATION, "OptionalID"); // all threads finished
		ExecutorServiceHelper.shutdownAndAwaitTermination(executorService, Duration.ofSeconds(numbers.size() - 2L), "OptionalID"); // task interruption
		System.out.println("End: " + Instant.now());
	}
}
