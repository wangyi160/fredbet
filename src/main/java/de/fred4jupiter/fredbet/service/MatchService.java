package de.fred4jupiter.fredbet.service;

import de.fred4jupiter.fredbet.domain.Group;
import de.fred4jupiter.fredbet.domain.Match;
import de.fred4jupiter.fredbet.props.CacheNames;
import de.fred4jupiter.fredbet.repository.MatchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;

@Service
@Transactional
public class MatchService {

    private static final Logger LOG = LoggerFactory.getLogger(MatchService.class);

    /**
     * show current K.O. matches that has been finished since 3 hours after
     * kick-off
     */
    private static final int MINUTES_SHOW_UPCOMING_OTHER_MATCHES = 10;

    /**
     * show current group matches that has been finished since 2 hours after
     * kick-off
     */
    private static final int HOURS_SHOW_UPCOMING_GROUP_MATCHES = 2;

    private final MatchRepository matchRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public MatchService(MatchRepository matchRepository, ApplicationEventPublisher applicationEventPublisher) {
        this.matchRepository = matchRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public List<Match> findAll() {
        return matchRepository.findAll();
    }

    public Optional<Match> findByMatchId(String matchId) {
        Assert.notNull(matchId, "matchId must be given");
        return matchRepository.findById(matchId);
    }

    @CacheEvict(cacheNames = CacheNames.AVAIL_GROUPS, allEntries = true)
    public Match save(Match match) {
        return matchRepository.save(match);
    }

    public void enterMatchResult(String matchId, Consumer<Match> consumer) {
        Match match = findMatchById(matchId);
        consumer.accept(match);

        save(match);
        publishMatchGoalsChangedEvent(match);
    }

    public void enterMatchResultsForAllMatches(Consumer<Match> consumer) {
        List<Match> allMatches = matchRepository.findAll();
        allMatches.forEach(consumer);
        matchRepository.saveAll(allMatches);
        allMatches.forEach(this::publishMatchGoalsChangedEvent);
    }

    private void publishMatchGoalsChangedEvent(Match match) {
        LOG.debug("fire MatchGoalsChangedEvent...");
        applicationEventPublisher.publishEvent(new MatchGoalsChangedEvent(this, match));
    }

    public List<Match> findMatchesByGroup(Group group) {
        return matchRepository.findByGroupOrderByKickOffDateAsc(group);
    }

    public List<Match> findAllMatches() {
        return matchRepository.findAllByOrderByKickOffDateAsc();
    }

    public List<Match> findUpcomingMatches() {
//        LocalDateTime groupKickOffBeginSelectionDate = LocalDateTime.now().minusHours(HOURS_SHOW_UPCOMING_GROUP_MATCHES);
        LocalDateTime koKickOffBeginSelectionDate = LocalDateTime.now().minusMinutes(MINUTES_SHOW_UPCOMING_OTHER_MATCHES);
        return matchRepository.findUpcomingMatches(koKickOffBeginSelectionDate);
    }

    @CacheEvict(cacheNames = CacheNames.AVAIL_GROUPS, allEntries = true)
    public void deleteAllMatches() {
        matchRepository.deleteAll();
    }

    @CacheEvict(cacheNames = CacheNames.AVAIL_GROUPS, allEntries = true)
    public void deleteMatch(String matchId) {
        matchRepository.deleteById(matchId);
    }

    public Match findMatchById(String matchId) {
        Optional<Match> matchOpt = matchRepository.findById(matchId);
        return matchOpt.orElse(null);
    }

    @Cacheable(CacheNames.AVAIL_GROUPS)
    public Set<Group> availableGroups() {
        LOG.info("Loading groups from DB...");
        return this.matchRepository.fetchGroupsOfAllMatches();
    }

//    public List<Match> findJokerMatches(String userName) {
//        return matchRepository.findMatchesOfJokerBetsForUser(userName);
//    }

    public List<Match> findMatchesOfToday() {
        LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime endDateTime = LocalDate.now().atTime(23, 59, 59);
        return matchRepository.findByKickOffDateBetweenOrderByKickOffDateAsc(startDateTime, endDateTime);
    }

    public List<Match> findFinishedMatchesWithoutResult() {
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(105);
        return matchRepository.findFinishedMatchesWithMissingResult(localDateTime);
    }

    public boolean isGameForThirdAvailable() {
//        List<Match> matches = matchRepository.findByGroup(Group.GAME_FOR_THIRD);
//        return !matches.isEmpty();
    	return false;
    }

    public Page<Match> findAllMatchesPageable(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return matchRepository.findAllByOrderByKickOffDateDesc(pageable);
    }

    public Page<Match> findUpcomingMatchesPagable(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
//        LocalDateTime groupKickOffBeginSelectionDate = LocalDateTime.now().minusHours(HOURS_SHOW_UPCOMING_GROUP_MATCHES);
        LocalDateTime koKickOffBeginSelectionDate = LocalDateTime.now().minusMinutes(MINUTES_SHOW_UPCOMING_OTHER_MATCHES);
        return matchRepository.findUpcomingMatches(pageable,  koKickOffBeginSelectionDate);
    }

    public Page<Match> findMatchesOfTodayPageable(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        LocalDateTime startDateTime = LocalDate.now().atStartOfDay();
        LocalDateTime endDateTime = LocalDate.now().atTime(23, 59, 59);
        return matchRepository.findByKickOffDateBetweenOrderByKickOffDateDesc(pageable, startDateTime, endDateTime);
    }

    public Page<Match> findMatchesByGroupPageable(Group group, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return matchRepository.findByGroupOrderByKickOffDateDesc(pageable, group);
    }

    public Page<Match> findFinishedMatchesWithoutResultPageable(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(105);
        return matchRepository.findFinishedMatchesWithMissingResult(pageable, localDateTime);
    }

}
