package au.com.madman.blogservo.servo;

import java.util.List;

import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.servo.Metric;
import com.netflix.servo.publish.BaseMetricObserver;
import com.netflix.servo.util.Preconditions;
import com.netflix.servo.util.Throwables;

/**
 * Write observations to logger of the format: <timestamp> : <name> <taglist>
 * <value>
 * 
 * @author rhslee2000
 *
 */
public class LoggerMetricObserver extends BaseMetricObserver {

	private Logger logger = LoggerFactory.getLogger(getClass());

	public LoggerMetricObserver(String name) {
		super(name);
	}

	@Override
	public void updateImpl(List<Metric> metrics) {
		Preconditions.checkNotNull(metrics, "metrics");
		try {
			for (Metric m : metrics) {
				logger.info("{}: name[{}] tags[{}] value[{}]", new LocalDateTime(m.getTimestamp()), m.getConfig()
						.getName(), m.getConfig().getTags(), m.getValue());
			}
		} catch (Throwable t) {
			incrementFailedCount();
			throw Throwables.propagate(t);
		}
	}

}
