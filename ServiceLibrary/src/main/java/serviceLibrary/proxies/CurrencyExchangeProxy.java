package serviceLibrary.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import serviceLibrary.dto.currencyExchange.CurrencyExchangeDto;

//Feign client name mora da se poklapa sa spring.application.name vrednoscu
// zeljenog servisa koja se nalazi u application.properties fajlu
@FeignClient(name = "currency-exchange")
public interface CurrencyExchangeProxy {

	@GetMapping("/currency-exchange")
	ResponseEntity<CurrencyExchangeDto> getExchangeFeign(@RequestParam String from, 
			@RequestParam String to);
}
