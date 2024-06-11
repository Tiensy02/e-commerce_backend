package SShop.mono;

import java.util.Arrays;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "SShop.mono.domain.repository.postgrerepo")
@EntityScan(basePackages = "SShop.mono.domain.entity.postgresql")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableMongoAuditing(auditorAwareRef = "auditorAware")
@EnableFeignClients
public class MonoApplication {

	public static void main(String[] args) {

		ApplicationContext ctx = SpringApplication.run(MonoApplication.class, args);


	}
}
