package br.com.dextra.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.com.dextra.dtos.ApiResponseDTO;
import br.com.dextra.dtos.HouseDTO;
import br.com.dextra.exceptions.InvalidDataException;
import br.com.dextra.exceptions.ResourceNotFound;
import br.com.dextra.models.Character;
import br.com.dextra.repositories.CharacterRepository;

@ActiveProfiles("test")
@DisplayName("unitary tests")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CharacterServiceTest {

	@Autowired
	private CharacterService service;

	@MockBean
	private CharacterRepository repository;

	@MockBean
	private HouseService houseService;

	@Test
	@DisplayName("successfully created character")
	public void createCharacter() {
		Character characterSaved = new Character(1l, "Harry Potter", "student",
				"Hogwarts School of Witchcraft and Wizardry", "1760529f-6d51-4cb1-bcb1-25087fce5bde", "stag");

		Mockito.when(this.repository.save(Mockito.any(Character.class))).thenReturn(characterSaved);
		Mockito.when(houseService.getHouses(Mockito.anyString()))
				.thenReturn(new ApiResponseDTO(Arrays.asList(new HouseDTO("1760529f-6d51-4cb1-bcb1-25087fce5bde"))));

		Character character = this.service.save(new Character("Harry Potter", "student",
				"Hogwarts School of Witchcraft and Wizardry", "1760529f-6d51-4cb1-bcb1-25087fce5bde", "stag"));

		assertThat(character.getId()).isEqualTo(1l);
		assertThat(character.getName()).isEqualTo("Harry Potter");
		assertThat(character.getRole()).isEqualTo("student");
		assertThat(character.getSchool()).isEqualTo("Hogwarts School of Witchcraft and Wizardry");
		assertThat(character.getHouse()).isEqualTo("1760529f-6d51-4cb1-bcb1-25087fce5bde");
		assertThat(character.getPatronus()).isEqualTo("stag");
	}

	@Test
	@DisplayName("invalid house id")
	public void errorCreatingCharacter_01() {

		Mockito.when(houseService.getHouses(Mockito.anyString()))
				.thenReturn(new ApiResponseDTO(Arrays.asList(new HouseDTO("1760529f-6d51-4cb1-bcb1-25087fce5bde"))));

		try {
			this.service.save(new Character("Harry Potter", "student", "Hogwarts School of Witchcraft and Wizardry",
					"1764529f-6d51-4cb1-bcb1-25087fce5dde", "stag"));
		} catch (Exception e) {
			assertThat(ResourceNotFound.class).isEqualTo(e.getClass());
			assertThat("House not found").isEqualTo(e.getMessage());
		}
	}

	@Test
	@DisplayName("name is empty")
	public void errorCreatingCharacter_02() {

		try {
			this.service.save(new Character("", "student", "Hogwarts School of Witchcraft and Wizardry",
					"1764529f-6d51-4cb1-bcb1-25087fce5dde", "stag"));
		} catch (Exception e) {
			assertThat(InvalidDataException.class).isEqualTo(e.getClass());
			assertThat("Invalid field: Name").isEqualTo(e.getMessage());
		}
	}

	@Test
	@DisplayName("role is null")
	public void errorCreatingCharacter_03() {

		try {
			this.service.save(new Character("Harry Potter", null, "Hogwarts School of Witchcraft and Wizardry",
					"1764529f-6d51-4cb1-bcb1-25087fce5dde", "stag"));
		} catch (Exception e) {
			assertThat(InvalidDataException.class).isEqualTo(e.getClass());
			assertThat("Invalid field: Role").isEqualTo(e.getMessage());
		}
	}

	@Test
	@DisplayName("school with blank space")
	public void errorCreatingCharacter_04() {

		try {
			this.service.save(
					new Character("Harry Potter", "student", "  ", "1764529f-6d51-4cb1-bcb1-25087fce5dde", "stag"));
		} catch (Exception e) {
			assertThat(InvalidDataException.class).isEqualTo(e.getClass());
			assertThat("Invalid field: School").isEqualTo(e.getMessage());
		}
	}

	@Test
	@DisplayName("house is null")
	public void errorCreatingCharacter_05() {

		try {
			this.service.save(
					new Character("Harry Potter", "student", "Hogwarts School of Witchcraft and Wizardry", null, "stag"));
		} catch (Exception e) {
			assertThat(InvalidDataException.class).isEqualTo(e.getClass());
			assertThat("Invalid field: House").isEqualTo(e.getMessage());
		}
	}
	
	@Test
	@DisplayName("patronus is empty")
	public void errorCreatingCharacter_06() {

		try {
			this.service.save(new Character("Harry Potter", "student", "Hogwarts School of Witchcraft and Wizardry",
					"1764529f-6d51-4cb1-bcb1-25087fce5dde", ""));
		} catch (Exception e) {
			assertThat(InvalidDataException.class).isEqualTo(e.getClass());
			assertThat("Invalid field: Patronus").isEqualTo(e.getMessage());
		}
	}
	
	@Test
	@DisplayName("successfully querying character")
	public void consultCharacter() {
		Character characterSaved = new Character(10l, "Harry Potter", "student",
				"Hogwarts School of Witchcraft and Wizardry", "1760529f-6d51-4cb1-bcb1-25087fce5bde", "stag");

		Mockito.when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.of(characterSaved));
		
		Character character = this.service.consultById(10l);
		
		assertThat(character.getId()).isEqualTo(10l);
		assertThat(character.getName()).isEqualTo("Harry Potter");
		assertThat(character.getRole()).isEqualTo("student");
		assertThat(character.getSchool()).isEqualTo("Hogwarts School of Witchcraft and Wizardry");
		assertThat(character.getHouse()).isEqualTo("1760529f-6d51-4cb1-bcb1-25087fce5bde");
		assertThat(character.getPatronus()).isEqualTo("stag");
	}
	
	@Test
	@DisplayName("invalid id")
	public void errorQueryingCharacter() {
		Mockito.when(this.repository.findById(Mockito.anyLong())).thenReturn(Optional.of(new Character()));
		
		try {
			this.service.consultById(5l);
		} catch (Exception e) {
			assertThat(ResourceNotFound.class).isEqualTo(e.getClass());
			assertThat("Character not found").isEqualTo(e.getMessage());
		}
	}
	
	@Test
	@DisplayName("consulting all characters")
	public void consultAllCharacteres() {
		Mockito.when(this.repository.findAll()).thenReturn(Arrays.asList(new Character(1l), new Character(2l)));
		
		List<Character> characters = this.service.consultCharacters(null);
		
		assertThat(characters).isNotEmpty();
		assertThat(characters.size()).isEqualTo(2l);
	}
	
	@Test
	@DisplayName("consulting characters by house")
	public void consultCharacteresByHouse() {
		Mockito.when(houseService.getHouses(Mockito.anyString()))
		.thenReturn(new ApiResponseDTO(Arrays.asList(new HouseDTO("1760529f-6d51-4cb1-bcb1-25087fce5bde"))));
		Mockito.when(this.repository.findByHouse(Mockito.anyString())).thenReturn(Arrays.asList(new Character(1l)));
		
		List<Character> characters = this.service.consultCharacters("1760529f-6d51-4cb1-bcb1-25087fce5bde");
		
		assertThat(characters).isNotEmpty();
		assertThat(characters.size()).isEqualTo(1l);
	}
	
}
