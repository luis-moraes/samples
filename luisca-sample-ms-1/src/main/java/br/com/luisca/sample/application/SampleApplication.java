package br.com.luisca.sample.application;

import java.util.regex.Pattern;

import javax.ws.rs.QueryParam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.luisca.sample.domain.Pagina;
import br.com.luisca.sample.domain.SampleRepository;
import br.com.luisca.sample.exception.ApiException;

@RestController
@EnableAutoConfiguration
public class SampleApplication {
	
	private static final String CHARSET_UTF8 = ";charset=utf-8";

	@RequestMapping("/")
    @ResponseBody
    String home() {
        return "Hello World!";
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
