package br.com.dextra.services;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import br.com.dextra.dtos.ApiResponseDTO;

@FeignClient(url= "http://us-central1-rh-challenges.cloudfunctions.net" , name = "potterApi")
public interface HouseService {
	
	@Cacheable(value = "houses")
	@GetMapping("/potterApi/houses")
	public ApiResponseDTO getHouses(@RequestHeader("apikey") String apikey); 

}
