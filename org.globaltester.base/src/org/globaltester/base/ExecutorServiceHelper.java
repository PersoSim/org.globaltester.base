package org.globaltester.base;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public interface ExecutorServiceHelper {

	public static final Duration DEFAULT_WAIT_FOR_TERMINATION_DURATION = Duration.ofSeconds(5);

	public static void shutdownAndAwaitTermination(final ExecutorService executorService,
			final Duration waitForTermination, String optionalID) {
		if (executorService == null || waitForTermination == null)
			return;
		executorService.shutdown(); // Disallow any more task submissions to this executor service
		try {
			// Wait a while for existing tasks to terminate
			if (!executorService.awaitTermination(waitForTermination.toMillis(), TimeUnit.MILLISECONDS)) {
				executorService.shutdownNow(); // Cancel all currently executing tasks
				// Wait a while for tasks to respond to being cancelled
				if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
					String id = optionalID != null ? optionalID + " " : "";
					System.err.println("Executor service " + id + "did not terminate. " + Instant.now()); // NOSONAR"
				}
			}
		} catch (InterruptedException ex) {
			// (Re-)Cancel if current thread also interrupted
			executorService.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}
}
