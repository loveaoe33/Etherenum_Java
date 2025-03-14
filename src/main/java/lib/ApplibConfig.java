package lib;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.CipherException;

import com.fasterxml.jackson.databind.ObjectMapper;



@Configuration
public class ApplibConfig {

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
	@Bean
	public BlockConfig initBlockLog() throws IOException, CipherException {
		return new BlockConfig();
	}
}
