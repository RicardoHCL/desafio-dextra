package br.com.dextra.services;

import java.text.MessageFormat;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;

import br.com.dextra.configs.ApiConfig;
import br.com.dextra.constants.GenericConstants;
import br.com.dextra.dtos.HouseDTO;
import br.com.dextra.exceptions.InvalidDataException;
import br.com.dextra.exceptions.ResourceNotFound;
import br.com.dextra.models.Character;
import br.com.dextra.repositories.CharacterRepository;

@Service
public class CharacterService {

	@Autowired
	private ApiConfig apiConfig;

	@Autowired
	private HouseService houseService;

	@Autowired
	private CharacterRepository repository;

	@CachePut(value = "character", key = "#character.id", condition = "#character.id!=null")
	public Character save(Character character) {
		this.validateCharacter(character);
		this.validateHouse(character.getHouse());
		return this.repository.save(character);
	}

	@Cacheable(value = "character", key = "#id")
	public Character consultById(Long id) {
		return this.repository.findById(id).orElseThrow(
				() -> new ResourceNotFound(MessageFormat.format(GenericConstants.RESOURCE_NOT_FOUND, "Character")));
	}

	public List<Character> consultCharacters(String houseId) {
		if (!Strings.isNullOrEmpty(houseId)) {
			this.validateHouse(houseId);
			return this.repository.findByHouse(houseId);
		}

		return this.repository.findAll();
	}

	@CacheEvict(value = "character", key = "#id")
	public void delete(Long id) {
		this.consultById(id);
		this.repository.deleteById(id);
	}

	private void validateCharacter(Character character) {

		if (Strings.isNullOrEmpty(character.getName()) || character.getName().isBlank())
			throw new InvalidDataException(MessageFormat.format(GenericConstants.INVALID_FIELD, "Name"));

		if (Strings.isNullOrEmpty(character.getRole()) || character.getRole().isBlank())
			throw new InvalidDataException(MessageFormat.format(GenericConstants.INVALID_FIELD, "Role"));

		if (Strings.isNullOrEmpty(character.getSchool()) || character.getSchool().isBlank())
			throw new InvalidDataException(MessageFormat.format(GenericConstants.INVALID_FIELD, "School"));

		if (Strings.isNullOrEmpty(character.getHouse()) || character.getHouse().isBlank())
			throw new InvalidDataException(MessageFormat.format(GenericConstants.INVALID_FIELD, "House"));

		if (Strings.isNullOrEmpty(character.getPatronus()) || character.getPatronus().isBlank())
			throw new InvalidDataException(MessageFormat.format(GenericConstants.INVALID_FIELD, "Patronus"));
	}

	private void validateHouse(String houseId) {

		var response = houseService.getHouses(apiConfig.getKey());
		List<HouseDTO> houses = response.getHouses().stream().filter(house -> house.getId().equals(houseId))
				.collect(Collectors.toList());

		if (houses.isEmpty())
			throw new ResourceNotFound(MessageFormat.format(GenericConstants.RESOURCE_NOT_FOUND, "House"));
	}

}
