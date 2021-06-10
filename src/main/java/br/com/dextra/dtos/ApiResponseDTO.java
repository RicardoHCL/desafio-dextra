package br.com.dextra.dtos;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDTO implements Serializable {

	private static final long serialVersionUID = 1930182538609339017L;

	private List<HouseDTO> houses;

	/**
	 * Constructors
	 */
	public ApiResponseDTO() {

	}

	public ApiResponseDTO(List<HouseDTO> houses) {
		this.houses = houses;
	}
}
