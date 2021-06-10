package br.com.dextra.controllers;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dextra.constants.GenericConstants;
import br.com.dextra.exceptions.InvalidDataException;
import br.com.dextra.exceptions.ResourceNotFound;
import br.com.dextra.models.Character;
import br.com.dextra.utils.Utils;

@ActiveProfiles("test")
@DisplayName("integrated tests")
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CharacterControllerTest {

	private MockMvc mockMvc;

	@Autowired
	private CharacterController controller;

	private Character character;

	@BeforeAll
	@DisplayName("starting tests with a character saved in the database")
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		Character body = new Character("Harry Potter", "student", "Hogwarts School of Witchcraft and Wizardry",
				"1760529f-6d51-4cb1-bcb1-25087fce5bde", "stag");

		MvcResult response = this.mockMvc
				.perform(MockMvcRequestBuilders.post(GenericConstants.CHARACTERS_URL)
						.contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(body)))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		this.character = convertJsonForCharacter(response.getResponse().getContentAsString());
	}

	@Test
	@Order(1)
	@DisplayName("successfully created character")
	public void createCharacter() throws Exception {
		Character body = new Character("Harry Test", "student", "Hogwarts School of Witchcraft and Wizardry",
				"df01bd60-e3ed-478c-b760-cdbd9afe51fc", "test");

		MvcResult response = this.mockMvc
				.perform(MockMvcRequestBuilders.post(GenericConstants.CHARACTERS_URL)
						.contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(body)))
				.andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

		Character returnedCharacter = convertJsonForCharacter(response.getResponse().getContentAsString());

		assertThat(returnedCharacter.getId()).isNotNull();
		assertThat(returnedCharacter.getName()).isEqualTo("Harry Test");
		assertThat(returnedCharacter.getRole()).isEqualTo("student");
		assertThat(returnedCharacter.getSchool()).isEqualTo("Hogwarts School of Witchcraft and Wizardry");
		assertThat(returnedCharacter.getHouse()).isEqualTo("df01bd60-e3ed-478c-b760-cdbd9afe51fc");
		assertThat(returnedCharacter.getPatronus()).isEqualTo("test");

	}

	@Test
	@Order(4)
	@DisplayName("successfully querying character")
	public void consultCharacter() throws Exception {
		MvcResult response = this.mockMvc
				.perform(MockMvcRequestBuilders.get(GenericConstants.CHARACTERS_URL + "/" + this.character.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Character returnedCharacter = convertJsonForCharacter(response.getResponse().getContentAsString());

		assertThat(returnedCharacter.getId()).isEqualTo(this.character.getId());
		assertThat(returnedCharacter.getName()).isEqualTo("Harry Potter");
		assertThat(returnedCharacter.getRole()).isEqualTo("student");
		assertThat(returnedCharacter.getSchool()).isEqualTo("Hogwarts School of Witchcraft and Wizardry");
		assertThat(returnedCharacter.getHouse()).isEqualTo("1760529f-6d51-4cb1-bcb1-25087fce5bde");
		assertThat(returnedCharacter.getPatronus()).isEqualTo("stag");

	}

	@Test
	@Order(5)
	@DisplayName("invalid id")
	public void errorQueryingCharacter() throws Exception {
		MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get(GenericConstants.CHARACTERS_URL + "/12"))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

		assertThat(response.getResolvedException().getMessage()).isEqualTo("Character not found");
		assertThat(response.getResolvedException().getClass()).isEqualTo(ResourceNotFound.class);
	}

	@Test
	@Order(2)
	@DisplayName("consulting all characters")
	public void consultAllCharacteres() throws Exception {
		MvcResult response = this.mockMvc.perform(MockMvcRequestBuilders.get(GenericConstants.CHARACTERS_URL))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Character[] characters = convertJsonForCharacterArray(response.getResponse().getContentAsString());

		assertThat(characters.length).isEqualTo(2);
	}

	@Test
	@Order(3)
	@DisplayName("consulting characters by house")
	public void consultCharacteresByHouse() throws Exception {
		MvcResult response = this.mockMvc
				.perform(MockMvcRequestBuilders
						.get(GenericConstants.CHARACTERS_URL + "?house=1760529f-6d51-4cb1-bcb1-25087fce5bde"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Character[] characters = convertJsonForCharacterArray(response.getResponse().getContentAsString());

		assertThat(characters.length).isEqualTo(1);
	}

	@Test
	@Order(6)
	@DisplayName("updating character")
	public void updateCharacter() throws Exception {
		Character body = new Character("Harry updated", "student", "Hogwarts School of Witchcraft and Wizardry",
				"542b28e2-9904-4008-b038-034ab312ad7e", "updated");

		MvcResult response = this.mockMvc
				.perform(MockMvcRequestBuilders.put(GenericConstants.CHARACTERS_URL + "/" + this.character.getId())
						.contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(body)))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		Character returnedCharacter = convertJsonForCharacter(response.getResponse().getContentAsString());

		assertThat(returnedCharacter.getId()).isEqualTo(this.character.getId());
		assertThat(returnedCharacter.getName()).isEqualTo("Harry updated");
		assertThat(returnedCharacter.getRole()).isEqualTo("student");
		assertThat(returnedCharacter.getSchool()).isEqualTo("Hogwarts School of Witchcraft and Wizardry");
		assertThat(returnedCharacter.getHouse()).isEqualTo("542b28e2-9904-4008-b038-034ab312ad7e");
		assertThat(returnedCharacter.getPatronus()).isEqualTo("updated");
	}

	@Test
	@Order(7)
	@DisplayName("deleting character")
	public void deleteCharacter() throws Exception {
		this.mockMvc
				.perform(MockMvcRequestBuilders.delete(GenericConstants.CHARACTERS_URL + "/" + this.character.getId()))
				.andExpect(MockMvcResultMatchers.status().isOk());

		MvcResult response = this.mockMvc
				.perform(MockMvcRequestBuilders.get(GenericConstants.CHARACTERS_URL + "/" + this.character.getId()))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

		assertThat(response.getResolvedException().getMessage()).isEqualTo("Character not found");
		assertThat(response.getResolvedException().getClass()).isEqualTo(ResourceNotFound.class);
	}

	@Test
	@Order(8)
	@DisplayName("invalid house id")
	public void errorCreatingCharacter_01() throws Exception {
		Character body = new Character("Harry Potter", "student", "Hogwarts School of Witchcraft and Wizardry",
				"1760529f-6d51-4cb1-btb1-25087fce5sbe", "stag");

		MvcResult response = this.mockMvc
				.perform(MockMvcRequestBuilders.post(GenericConstants.CHARACTERS_URL)
						.contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(body)))
				.andExpect(MockMvcResultMatchers.status().isNotFound()).andReturn();

		assertThat(response.getResolvedException().getMessage()).isEqualTo("House not found");
		assertThat(response.getResolvedException().getClass()).isEqualTo(ResourceNotFound.class);
	}

	@Test
	@Order(9)
	@DisplayName("name is empty")
	public void errorCreatingCharacter_02() throws Exception {
		Character body = new Character("", "student", "Hogwarts School of Witchcraft and Wizardry",
				"1760529f-6d51-4cb1-bcb1-25087fce5bde", "stag");

		MvcResult response = this.mockMvc
				.perform(MockMvcRequestBuilders.post(GenericConstants.CHARACTERS_URL)
						.contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(body)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

		assertThat(response.getResolvedException().getMessage()).isEqualTo("Invalid field: Name");
		assertThat(response.getResolvedException().getClass()).isEqualTo(InvalidDataException.class);
	}

	@Test
	@Order(10)
	@DisplayName("role is null")
	public void errorCreatingCharacter_03() throws Exception {
		Character body = new Character("Harry Potter", null, "Hogwarts School of Witchcraft and Wizardry",
				"1760529f-6d51-4cb1-bcb1-25087fce5bde", "stag");

		MvcResult response = this.mockMvc
				.perform(MockMvcRequestBuilders.post(GenericConstants.CHARACTERS_URL)
						.contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(body)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

		assertThat(response.getResolvedException().getMessage()).isEqualTo("Invalid field: Role");
		assertThat(response.getResolvedException().getClass()).isEqualTo(InvalidDataException.class);
	}

	@Test
	@Order(11)
	@DisplayName("school with blank space")
	public void errorCreatingCharacter_04() throws Exception {
		Character body = new Character("Harry Potter", "student", "   ", "1760529f-6d51-4cb1-bcb1-25087fce5bde",
				"stag");

		MvcResult response = this.mockMvc
				.perform(MockMvcRequestBuilders.post(GenericConstants.CHARACTERS_URL)
						.contentType(MediaType.APPLICATION_JSON).content(Utils.asJsonString(body)))
				.andExpect(MockMvcResultMatchers.status().isBadRequest()).andReturn();

		assertThat(response.getResolvedException().getMessage()).isEqualTo("Invalid field: School");
		assertThat(response.getResolvedException().getClass()).isEqualTo(InvalidDataException.class);
	}

	private Character convertJsonForCharacter(String json) {
		Character character = new Character();
		ObjectMapper mapper = new ObjectMapper();

		try {
			character = mapper.readValue(json, Character.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return character;
	}

	private Character[] convertJsonForCharacterArray(String json) {
		Character[] characters = null;
		ObjectMapper mapper = new ObjectMapper();

		try {
			characters = mapper.readValue(json, Character[].class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return characters;
	}
}
