package de.fred4jupiter.fredbet.domain;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "FOOTBALL_GROUP")
public class Group {

	@Id
	@Column(name = "NAME")
	private String name;

	protected Group() {
		// for hibernate
	}
	
	public Group(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		String[] parts = name.split(" ");
		return String.join("_", parts);
	}
}
