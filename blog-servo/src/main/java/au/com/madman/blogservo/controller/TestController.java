package au.com.madman.blogservo.controller;

import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.RandomUtils;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.servo.annotations.DataSourceLevel;
import com.netflix.servo.annotations.DataSourceType;
import com.netflix.servo.annotations.Monitor;
import com.netflix.servo.annotations.MonitorTags;
import com.netflix.servo.monitor.Monitors;
import com.netflix.servo.tag.BasicTagList;
import com.netflix.servo.tag.TagList;

@RestController("testController")
public class TestController {

	@Monitor(name = "requestCounter", type = DataSourceType.COUNTER, description = "Total number of requests", level = DataSourceLevel.INFO)
	private final AtomicInteger requestCounter = new AtomicInteger(0);
	
	@Monitor(name = "aGauge", type = DataSourceType.GAUGE, description = "A random gauge", level = DataSourceLevel.CRITICAL)
	private final AtomicInteger aGauge = new AtomicInteger(0);
	
	@MonitorTags
	private final TagList tags = BasicTagList.of("id", "testController", "class", ClassUtils.getShortName(getClass()));
	
	@PostConstruct
	public void init() {
		Monitors.registerObject("testController", this);
	}
	
	@RequestMapping(value = "/sayhi", method = RequestMethod.GET )
	public String sayHi(@RequestParam String to) {
		requestCounter.incrementAndGet(); // increment counter
		aGauge.set(RandomUtils.nextInt(0, 100));
		return "hi " + to;
	}
	
}
