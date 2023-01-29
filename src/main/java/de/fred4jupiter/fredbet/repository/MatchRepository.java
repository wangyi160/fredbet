package de.fred4jupiter.fredbet.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import de.fred4jupiter.fredbet.domain.Country;
import de.fred4jupiter.fredbet.domain.Group;
import de.fred4jupiter.fredbet.domain.Match;

public interface MatchRepository extends JpaRepository<Match, String> {

	List<Match> findAllByOrderByKickOffDateAsc();

	@Query("select m from Match m where m.kickOffDate > :koKickOffDate order by m.kickOffDate asc")
	List<Match> findUpcomingMatches(@Param("koKickOffDate") LocalDateTime koKickOffDate);

	List<Match> findByGroupOrderByKickOffDateAsc(Group group);

	List<Match> findByTeamOneCountry(Country country);

	List<Match> findByGroup(Group group);

	@Query("select min(a.kickOffDate) from Match a")
	LocalDateTime findStartDateOfFirstMatch();

	@Query("select a.group from Match a ")
	Set<Group> fetchGroupsOfAllMatches();

//	@Query("Select b.match from Bet b where b.joker = TRUE and b.userName = :userName order by b.match.kickOffDate asc")
//	List<Match> findMatchesOfJokerBetsForUser(@Param("userName") String userName);

	List<Match> findByKickOffDateBetweenOrderByKickOffDateAsc(LocalDateTime startDate, LocalDateTime endDate);

	@Query("select m from Match m where m.kickOffDate < :date and m.teamOne.goals is null and m.teamTwo.goals is null order by m.kickOffDate asc")
	List<Match> findFinishedMatchesWithMissingResult(@Param("date") LocalDateTime date);

	@Query("select m from Match m where m.teamOne.goals is not null and m.teamTwo.goals is not null order by m.kickOffDate asc")
	List<Match> findFinishedMatches();

	// 分页需要
	Page<Match> findAll(Pageable pageable);

	@Query("select m from Match m where m.kickOffDate > :koKickOffDate order by m.kickOffDate asc")
	Page<Match> findUpcomingMatches(Pageable pageable, @Param("koKickOffDate") LocalDateTime koKickOffDate);

	Page<Match> findByKickOffDateBetweenOrderByKickOffDateAsc(Pageable pageable, LocalDateTime startDate, LocalDateTime endDate);

	Page<Match> findByGroupOrderByKickOffDateAsc(Pageable pageable, Group group);

}
