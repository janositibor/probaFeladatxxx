package TZJanosi.probaFeladat;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProbaFeladatApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProbaFeladatApplication.class, args);

	}
	
	@Bean
	public ModelMapper modelMapper(){
		ModelMapper modelMapper=new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}

}
