package de.fred4jupiter.fredbet.web.matches;

import de.fred4jupiter.fredbet.domain.Group;
import de.fred4jupiter.fredbet.repository.GroupRepository;
import de.fred4jupiter.fredbet.security.SecurityService;
import de.fred4jupiter.fredbet.web.WebMessageUtil;
import de.fred4jupiter.fredbet.web.bet.RedirectViewName;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/matches")
public class MatchController {

    private static final String VIEW_LIST_MATCHES = "matches/list";

    private final SecurityService securityBean;

    private final WebMessageUtil messageUtil;

    private final MatchCommandMapper matchCommandMapper;
    
    private GroupRepository groupRepository;

    public MatchController(SecurityService securityBean, WebMessageUtil messageUtil, MatchCommandMapper matchCommandMapper, GroupRepository groupRepository) {
        this.securityBean = securityBean;
        this.messageUtil = messageUtil;
        this.matchCommandMapper = matchCommandMapper;
        this.groupRepository = groupRepository;
    }

    @GetMapping
    public String listAllMatches(Model model) {
        List<MatchCommand> matches = matchCommandMapper.findAllMatches(securityBean.getCurrentUserName());
        model.addAttribute("allMatches", matches);
        model.addAttribute("heading", messageUtil.getMessageFor("all.matches"));
        model.addAttribute("redirectViewName", RedirectViewName.MATCHES);
        return VIEW_LIST_MATCHES;
    }

    @GetMapping("upcoming")
    public String upcomingMatches(Model model) {
        List<MatchCommand> matches = matchCommandMapper.findAllUpcomingMatches(securityBean.getCurrentUserName());
        model.addAttribute("allMatches", matches);
        model.addAttribute("heading", messageUtil.getMessageFor("upcoming.matches"));
        model.addAttribute("redirectViewName", RedirectViewName.MATCHES_UPCOMING);
        return VIEW_LIST_MATCHES;
    }

    @GetMapping("/group/{groupUrl}")
    public String listByGroup(@PathVariable("groupUrl") String groupUrl, Model model) {
//        final Group group = Group.valueOf(groupName);

        // 需要将groupName变换成原始的样子
        String[] parts = groupUrl.split("_");
        String groupName = String.join(" ", parts);

        Optional<Group> groupOpt = groupRepository.findById(groupName);
        List<MatchCommand> matches;
        if(!groupOpt.isEmpty()) {
            Group group = groupOpt.get();
            matches = matchCommandMapper.findMatchesByGroup(securityBean.getCurrentUserName(), group);
            model.addAttribute("allMatches", matches);
            model.addAttribute("heading", group.getName());
            model.addAttribute("redirectViewName", RedirectViewName.createRedirectForGroup(group));
        } else {
            matches = new ArrayList<>();
            model.addAttribute("allMatches", matches);
            model.addAttribute("heading", "No such group");
            model.addAttribute("redirectViewName", "");
        }

        return VIEW_LIST_MATCHES;
    }

//    @GetMapping("/joker")
//    public String jokerMatches(Model model) {
//        List<MatchCommand> matches = matchCommandMapper.findJokerMatches(securityBean.getCurrentUserName());
//        model.addAttribute("allMatches", matches);
//        model.addAttribute("heading", messageUtil.getMessageFor("joker.matches"));
//        model.addAttribute("redirectViewName", RedirectViewName.MATCHES_JOKER);
//        return VIEW_LIST_MATCHES;
//    }

    @GetMapping("/today")
    public String matchesOfToday(Model model) {
        List<MatchCommand> matches = matchCommandMapper.findMatchesOfToday(securityBean.getCurrentUserName());
        model.addAttribute("allMatches", matches);
        model.addAttribute("heading", messageUtil.getMessageFor("today.matches"));
        model.addAttribute("redirectViewName", RedirectViewName.MATCHES_TODAY);
        return VIEW_LIST_MATCHES;
    }

    @GetMapping("/finishednoresult")
    public String finishedMatchesWithoutResult(Model model) {
        List<MatchCommand> matches = matchCommandMapper.findFinishedMatchesNoResult(securityBean.getCurrentUserName());
        model.addAttribute("allMatches", matches);
        model.addAttribute("heading", messageUtil.getMessageFor("finishednoresult.matches"));
        model.addAttribute("redirectViewName", RedirectViewName.MATCHES_TODAY);
        return VIEW_LIST_MATCHES;
    }
}
