package br.com.dextra.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HouseDTO implements Serializable {

	private static final long serialVersionUID = 8469167124086352470L;

	private String id;
	private String name;
	private String headOfHouse;
	private List<String> values;
	private List<String> colors;
	private String school;
	private String mascot;
	private String houseGhost;
	private String founder;

	/**
	 * Constructors
	 */
	public HouseDTO() {

	}

	public HouseDTO(String id) {
		this.id = id;
	}
}
