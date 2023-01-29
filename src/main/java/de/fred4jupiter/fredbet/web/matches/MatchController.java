package de.fred4jupiter.fredbet.web.matches;

import de.fred4jupiter.fredbet.domain.Group;
import de.fred4jupiter.fredbet.domain.Match;
import de.fred4jupiter.fredbet.repository.GroupRepository;
import de.fred4jupiter.fredbet.security.SecurityService;
import de.fred4jupiter.fredbet.service.MatchService;
import de.fred4jupiter.fredbet.web.WebMessageUtil;
import de.fred4jupiter.fredbet.web.bet.RedirectViewName;
import jakarta.annotation.Resource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/matches")
public class MatchController {

    @Resource
    public MatchService matchService;


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
    public String listAllMatches(Model model, @RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum, @RequestParam(defaultValue = "3", value = "pageSize") int pageSize) {
        List<MatchCommand> matches = matchCommandMapper.findAllMatchesPageable(securityBean.getCurrentUserName(),pageNum, pageSize);
        model.addAttribute("allMatches", matches);
        model.addAttribute("heading", messageUtil.getMessageFor("all.matches"));
        model.addAttribute("redirectViewName", RedirectViewName.MATCHES);
        model.addAttribute("redirectUrl", "/matches");

        // 分页
        Page<Match> res = matchService.findAllMatchesPageable(pageNum, pageSize);
        model.addAttribute("res", res);

        return VIEW_LIST_MATCHES;
    }


    @GetMapping("upcoming")
    public String upcomingMatches(Model model, @RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum, @RequestParam(defaultValue = "3", value = "pageSize") int pageSize) {
        List<MatchCommand> matches = matchCommandMapper.findAllUpcomingMatchesPageable(securityBean.getCurrentUserName(), pageNum, pageSize);
        model.addAttribute("allMatches", matches);
        model.addAttribute("heading", messageUtil.getMessageFor("upcoming.matches"));
        model.addAttribute("redirectViewName", RedirectViewName.MATCHES_UPCOMING);
        model.addAttribute("redirectUrl", "/matches/upcoming");

        // 分页
        Page<Match> res = matchService.findUpcomingMatchesPagable(pageNum, pageSize);
        model.addAttribute("res", res);
        return VIEW_LIST_MATCHES;
    }

    @GetMapping("/group/{groupUrl}")
    public String listByGroup(@PathVariable("groupUrl") String groupUrl, Model model, @RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum, @RequestParam(defaultValue = "3", value = "pageSize") int pageSize) {
//        final Group group = Group.valueOf(groupName);

        // 需要将groupName变换成原始的样子
        String[] parts = groupUrl.split("_");
        String groupName = String.join(" ", parts);

        Optional<Group> groupOpt = groupRepository.findById(groupName);
        List<MatchCommand> matches;
        if(!groupOpt.isEmpty()) {
            Group group = groupOpt.get();
            matches = matchCommandMapper.findMatchesByGroupPageable(securityBean.getCurrentUserName(), group, pageNum, pageSize);
            model.addAttribute("allMatches", matches);
            model.addAttribute("heading", group.getName());
            model.addAttribute("redirectViewName", RedirectViewName.createRedirectForGroup(group));
            model.addAttribute("redirectUrl", "/matches/group/"+group.getName());

            // 分页
            Page<Match> res = matchService.findMatchesByGroupPageable(group, pageNum, pageSize);
            model.addAttribute("res", res);

        } else {
            matches = new ArrayList<>();
            model.addAttribute("allMatches", matches);
            model.addAttribute("heading", "No such group");
            model.addAttribute("redirectViewName", "");
            model.addAttribute("redirectUrl", "/matches");

            // 分页
            Page<Match> res = Page.empty();
            model.addAttribute("res", res);
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
    public String matchesOfToday(Model model, @RequestParam(required = false, defaultValue = "0", value = "pageNum") int pageNum, @RequestParam(defaultValue = "3", value = "pageSize") int pageSize) {
        List<MatchCommand> matches = matchCommandMapper.findMatchesOfTodayPageable(securityBean.getCurrentUserName(), pageNum, pageSize);
        model.addAttribute("allMatches", matches);
        model.addAttribute("heading", messageUtil.getMessageFor("today.matches"));
        model.addAttribute("redirectViewName", RedirectViewName.MATCHES_TODAY);

        // 分页
        Page<Match> res = matchService.findMatchesOfTodayPageable(pageNum, pageSize);
        model.addAttribute("res", res);


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
