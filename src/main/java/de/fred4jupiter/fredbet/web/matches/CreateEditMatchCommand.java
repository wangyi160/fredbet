package de.fred4jupiter.fredbet.web.matches;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

import de.fred4jupiter.fredbet.domain.Country;
import de.fred4jupiter.fredbet.domain.Group;
import de.fred4jupiter.fredbet.web.validation.ValidMatchConstraint;

@ValidMatchConstraint
public class CreateEditMatchCommand {

	private static final String DATE_FORMAT_PATTERN = "dd.MM.yyyy HH:mm";

	private String matchId;

	@NotNull
	private String teamNameOne;

	@NotNull
	private String teamNameTwo;

	//@NotNull
	private Country countryTeamOne;

	//@NotNull
	private Country countryTeamTwo;

	@NotNull
	private LocalDateTime kickOffDate;

	@NotNull
	private Group group;

	//@NotBlank
	private String stadium;

	private boolean deletable;
	
	private double winOdds;
	private double drawOdds;
	private double loseOdds;

	public String getMatchId() {
		return matchId;
	}

	public void setMatchId(String matchId) {
		this.matchId = matchId;
	}

	public String getTeamNameOne() {
		return teamNameOne;
	}

	public void setTeamNameOne(String teamNameOne) {
		this.teamNameOne = teamNameOne;
	}

	public String getTeamNameTwo() {
		return teamNameTwo;
	}

	public void setTeamNameTwo(String teamNameTwo) {
		this.teamNameTwo = teamNameTwo;
	}

	public Country getCountryTeamOne() {
		return countryTeamOne;
	}

	public void setCountryTeamOne(Country countryTeamOne) {
		this.countryTeamOne = countryTeamOne;
	}

	public Country getCountryTeamTwo() {
		return countryTeamTwo;
	}

	public void setCountryTeamTwo(Country countryTeamTwo) {
		this.countryTeamTwo = countryTeamTwo;
	}

	public LocalDateTime getKickOffDate() {
		return kickOffDate;
	}

	public void setKickOffDate(LocalDateTime kickOffDate) {
		this.kickOffDate = kickOffDate;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getStadium() {
		return stadium;
	}

	public void setStadium(String stadium) {
		this.stadium = stadium;
	}

	public String getKickOffDateString() {
		if (kickOffDate == null) {
			return "";
		}
		return kickOffDate.format(DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
	}

	public void setKickOffDateString(String kickOffDateString) {
		if (StringUtils.isBlank(kickOffDateString)) {
			return;
		}
		this.kickOffDate = LocalDateTime.parse(kickOffDateString, DateTimeFormatter.ofPattern(DATE_FORMAT_PATTERN));
	}

	public boolean isDeletable() {
		return deletable;
	}

	public void setDeletable(boolean deletable) {
		this.deletable = deletable;
	}

	public double getWinOdds() {
		return winOdds;
	}

	public void setWinOdds(double winOdds) {
		this.winOdds = winOdds;
	}

	public double getDrawOdds() {
		return drawOdds;
	}

	public void setDrawOdds(double drawOdds) {
		this.drawOdds = drawOdds;
	}

	public double getLoseOdds() {
		return loseOdds;
	}

	public void setLoseOdds(double loseOdds) {
		this.loseOdds = loseOdds;
	}
	
	
	
	
}
