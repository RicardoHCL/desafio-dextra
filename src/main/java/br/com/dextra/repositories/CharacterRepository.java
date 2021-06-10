package br.com.dextra.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.dextra.models.Character;

public interface CharacterRepository extends JpaRepository<Character, Long> {
	
	List<Character> findByHouse(String house);

}
