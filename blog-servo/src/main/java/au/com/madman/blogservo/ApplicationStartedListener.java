package au.com.madman.blogservo;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import au.com.madman.blogservo.servo.LoggerMetricObserver;

import com.netflix.servo.publish.BasicMetricFilter;
import com.netflix.servo.publish.CounterToRateMetricTransform;
import com.netflix.servo.publish.MetricObserver;
import com.netflix.servo.publish.MonitorRegistryMetricPoller;
import com.netflix.servo.publish.PollRunnable;
import com.netflix.servo.publish.PollScheduler;

/**
 * Application event listener for context refreshed. Create index for
 * collections
 *
 */
public class ApplicationStartedListener implements ApplicationListener<ContextRefreshedEvent> {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		logger.info("Got event {}", event.getSource());
		initMetricsPublishing();
	}
	
	private void initMetricsPublishing() {
		PollScheduler scheduler = PollScheduler.getInstance();
		scheduler.start();

		MetricObserver logObserver = new LoggerMetricObserver("logger-observer");
		
		MetricObserver logObserverRate = new LoggerMetricObserver("logger-observer-rate");
		MetricObserver transform = new CounterToRateMetricTransform(logObserverRate, 1, TimeUnit.MINUTES);
		PollRunnable task = new PollRunnable(
		    new MonitorRegistryMetricPoller(),
		    BasicMetricFilter.MATCH_ALL,
		    logObserver, transform);
		scheduler.addPoller(task, 1, TimeUnit.MINUTES);
	}

}
