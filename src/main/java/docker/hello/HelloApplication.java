package docker.hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class HelloApplication {

	@GetMapping
    public String home() {
        return "Hello Docker World";
    }

	public static void main(String[] args) {
		SpringApplication.run(HelloApplication.class, args);
	}

}
 // gradle build를 터미널에서 입력한다. ./gradlew build 말고
 // build.gradle에 추가 설정한거 있다.