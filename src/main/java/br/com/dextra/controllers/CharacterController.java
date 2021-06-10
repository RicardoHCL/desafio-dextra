package br.com.dextra.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.dextra.constants.GenericConstants;
import br.com.dextra.models.Character;
import br.com.dextra.services.CharacterService;
import io.swagger.annotations.Api;

@RestController
@Api(value = "Characters", description = "Endpoint of characters", tags = "Characters")
@RequestMapping(GenericConstants.CHARACTERS_URL)
public class CharacterController {
	
	@Autowired
	private CharacterService service;
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Character create(@RequestBody Character character) {
		return this.service.save(character);
	}

	@GetMapping("/{id}")
	public Character consultById(@PathVariable Long id) {
		return this.service.consultById(id);
	}
	
	@GetMapping
	public List<Character> consultCharacters(@RequestParam(required = false) String house) {
		return this.service.consultCharacters(house);
	}
	
	@PutMapping("/{id}")
	public Character update(@PathVariable Long id, @RequestBody Character character) {
		character.setId(id);
		return this.service.save(character);
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		this.service.delete(id);
	}
}
