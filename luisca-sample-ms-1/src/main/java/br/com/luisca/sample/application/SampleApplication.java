package br.com.luisca.sample.application;

import java.util.regex.Pattern;

import javax.ws.rs.QueryParam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.luisca.sample.domain.Pagina;
import br.com.luisca.sample.domain.SampleRepository;
import br.com.luisca.sample.exception.ApiException;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@RestController
@EnableAutoConfiguration
@EnableSwagger2
public class SampleApplication {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";
	
	 @Bean
	    public Docket swaggerSettings() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.any())
	                .paths(PathSelectors.any())
	                .build()
	                .pathMapping("/");
	    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(SampleApplication.class, args);
    }
    
    @RequestMapping(value = "/sample", method = RequestMethod.GET)
	public ResponseEntity<Pagina> get(@QueryParam("page") String page) throws ApiException {
    	
    	if(page == "0"){
    		return new ResponseEntity<Pagina>(HttpStatus.BAD_REQUEST);
		}
		
		if(!Pattern.matches("^\\d+", page)){
			return new ResponseEntity<Pagina>(HttpStatus.BAD_REQUEST);
		}
		
		SampleRepository repository = new SampleRepository();
				
		if((page == null) || (page.isEmpty())){
			return new ResponseEntity<Pagina>(new Pagina(repository.getAll()),HttpStatus.OK);
		}
		
	
		
		return new ResponseEntity<Pagina>(
				new Pagina(repository.getByRange(Integer.parseInt(page))),HttpStatus.OK);
	}
    
    
    
	
}
