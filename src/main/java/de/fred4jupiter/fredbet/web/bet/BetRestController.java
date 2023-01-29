package de.fred4jupiter.fredbet.web.bet;

import de.fred4jupiter.fredbet.domain.Bet;
import de.fred4jupiter.fredbet.domain.Match;
import de.fred4jupiter.fredbet.domain.Team;
import de.fred4jupiter.fredbet.security.SecurityService;
import de.fred4jupiter.fredbet.service.BettingService;
import de.fred4jupiter.fredbet.service.MatchService;
import de.fred4jupiter.fredbet.util.MessageSourceUtil;
import de.fred4jupiter.fredbet.web.WebMessageUtil;
import de.fred4jupiter.fredbet.web.matches.MatchCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/betlist")
public class BetRestController {
    @Autowired
    private BettingService bettingService;

    @Autowired
    private MatchService matchService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private MessageSourceUtil messageSourceUtil;

    @Autowired
    private  WebMessageUtil webMessageUtil;

    @GetMapping
    public List< BetCommand > getBetList() {
        List<Bet> bets =  bettingService.findAllByUsername(securityService.getCurrentUserName());

        List< BetCommand > betCommands = new ArrayList<>();

        for(Bet bet: bets) {
            BetCommand betCommand = toBetCommand(bet);
            betCommands.add(betCommand);
        }

        return betCommands;
    }

    private BetCommand toBetCommand(Bet bet) {
        BetCommand betCommand = new BetCommand();
        betCommand.setBetId(bet.getId());
        betCommand.setMatchId(bet.getMatch().getId());

        MatchCommand matchCommand = toMatchCommand(bet.getMatch());
        betCommand.setMatch(matchCommand);

//        betCommand.setGoalsTeamOne(bet.getGoalsTeamOne());
//        betCommand.setGoalsTeamTwo(bet.getGoalsTeamTwo());
//        betCommand.setPenaltyWinnerOne(bet.isPenaltyWinnerOne());

        final Locale locale = LocaleContextHolder.getLocale();
        final Team teamOne = bet.getMatch().getTeamOne();
        final Team teamTwo = bet.getMatch().getTeamTwo();
        betCommand.setTeamNameOne(teamOne.getNameTranslated(messageSourceUtil, locale));
        betCommand.setIconPathTeamOne(teamOne.getIconPathBig());
        betCommand.setTeamNameTwo(teamTwo.getNameTranslated(messageSourceUtil, locale));
        betCommand.setIconPathTeamTwo(teamTwo.getIconPathBig());

        betCommand.setGroupMatch(bet.getMatch().isGroupMatch());

//        Joker joker = jokerService.getJokerForUser(securityService.getCurrentUserName());
//        betCommand.setNumberOfJokersUsed(joker.getNumberOfJokersUsed());
//        betCommand.setMaxJokers(joker.getMax());
//        betCommand.setUseJoker(bet.isJoker());
//        betCommand.setJokerEditable(jokerService.isSettingJokerAllowed(securityService.getCurrentUserName(), bet.getMatch().getId()));

        betCommand.setPoints(bet.getPoints());
        betCommand.setBetType(bet.getBetType());
        betCommand.setOdds(bet.getOdds());

        betCommand.setReward(bet.getReward());
        betCommand.setCreatedAt(bet.getCreatedAt());


        return betCommand;
    }

    public MatchCommand toMatchCommand(Match match) {
        Assert.notNull(match, "Match must be given");
        MatchCommand matchCommand = new MatchCommand();
        matchCommand.setMatchId(match.getId());
        matchCommand.setCountryTeamOne(match.getTeamOne().getCountry());
        matchCommand.setCountryTeamTwo(match.getTeamTwo().getCountry());
        matchCommand.setTeamNameOne(webMessageUtil.getTeamNameOne(match));
        matchCommand.setTeamNameTwo(webMessageUtil.getTeamNameTwo(match));
        matchCommand.setTeamResultOne(match.getGoalsTeamOne());
        matchCommand.setTeamResultTwo(match.getGoalsTeamTwo());
        matchCommand.setKickOffDate(match.getKickOffDate());
        matchCommand.setStadium(match.getStadium());
        matchCommand.setGroup(match.getGroup());

        matchCommand.setWinOdds(match.getWinOdds());
        matchCommand.setDrawOdds(match.getDrawOdds());
        matchCommand.setLoseOdds(match.getLoseOdds());

//        matchCommand.setPenaltyWinnerOneMatch(match.isPenaltyWinnerOne());
        return matchCommand;
    }
}
