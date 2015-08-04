package au.com.madman;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import au.com.madman.blogservo.BlogNetflixServoApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BlogNetflixServoApplication.class)
@WebAppConfiguration
public class BlogNetflixServoApplicationTests {

	@Test
	public void contextLoads() {
	}

}
