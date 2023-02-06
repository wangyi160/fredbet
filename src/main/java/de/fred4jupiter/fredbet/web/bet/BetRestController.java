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
import org.springframework.data.domain.Page;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public BetHistoryCommand getBetList(@RequestParam(value = "draw") int draw, @RequestParam(value = "start") int start,
                                        @RequestParam(value = "length") int length) {

        int pageNum = start/length;
        int pageSize = length;

        Page<Bet> bets =  bettingService.findAllByUsernamePageable(securityService.getCurrentUserName(), pageNum, pageSize);


        List< BetHistoryCommandData > datas = new ArrayList<>();

        for(Bet bet: bets) {
            BetHistoryCommandData data = toBetHistoryCommandData(bet);
            datas.add(data);
        }

//        if(datas.size()<10) {
//            int left = 10 - datas.size();
//            for(int i=0;i < left;i++) {
//                datas.add(datas.get(0));
//            }
//        }

        BetHistoryCommand betHistoryCommand = new BetHistoryCommand();
        betHistoryCommand.setDraw(draw);
        betHistoryCommand.setRecordsTotal(bets.getTotalElements());
        betHistoryCommand.setRecordsFiltered(bets.getTotalElements());
        betHistoryCommand.setData(datas);

        return betHistoryCommand;
    }

    private BetHistoryCommandData toBetHistoryCommandData(Bet bet) {
        BetHistoryCommandData betCommand = new BetHistoryCommandData();
        betCommand.setCreatedAt(bet.getCreatedAt());
        betCommand.setMatchGroup(bet.getMatch().getGroup().getName());

        betCommand.setTeamNameOne(bet.getMatch().getTeamOne().getName());
        betCommand.setTeamNameTwo(bet.getMatch().getTeamTwo().getName());

        betCommand.setBetType(bet.getBetType());

        if(bet.getBetType().equals("win")) {
            betCommand.setOdds(bet.getMatch().getWinOdds());
        }
        else if(bet.getBetType().equals("draw")) {
            betCommand.setOdds(bet.getMatch().getDrawOdds());
        }
        else if(bet.getBetType().equals("lose")) {
            betCommand.setOdds(bet.getMatch().getLoseOdds());
        }

        betCommand.setPoints(bet.getPoints());
        betCommand.setReward(bet.getReward());


        return betCommand;
    }


}
