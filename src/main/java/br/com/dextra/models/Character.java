package br.com.dextra.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "characters")
public class Character implements Serializable {

	private static final long serialVersionUID = -4800352923967739829L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_character")
	@SequenceGenerator(name = "seq_character", sequenceName = "seq_character", allocationSize = 1)
	@Column(name = "id")
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "role", nullable = false)
	private String role;

	@Column(name = "school", nullable = false)
	private String school;

	@Column(name = "house", nullable = false)
	private String house;

	@Column(name = "patronus", nullable = false)
	private String patronus;

	/**
	 * Constructors
	 */
	public Character() {

	}

	public Character(Long id) {
		this.id = id;
	}

	public Character(String name, String role, String school, String house, String patronus) {
		this.name = name;
		this.role = role;
		this.school = school;
		this.house = house;
		this.patronus = patronus;
	}

	public Character(Long id, String name, String role, String school, String house, String patronus) {
		this.id = id;
		this.name = name;
		this.role = role;
		this.school = school;
		this.house = house;
		this.patronus = patronus;
	}

}
