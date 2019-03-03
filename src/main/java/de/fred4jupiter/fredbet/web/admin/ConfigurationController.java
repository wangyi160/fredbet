package de.fred4jupiter.fredbet.web.admin;

import de.fred4jupiter.fredbet.security.FredBetPermission;
import de.fred4jupiter.fredbet.service.admin.CacheAdministrationService;
import de.fred4jupiter.fredbet.util.LogLevel;
import de.fred4jupiter.fredbet.util.LogLevelChangable;
import de.fred4jupiter.fredbet.web.WebMessageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/config")
@PreAuthorize("hasAuthority('" + FredBetPermission.PERM_ADMINISTRATION + "')")
public class ConfigurationController {

    private static final String PAGE_CONFIGURATION = "admin/configuration";

    @Autowired
    private CacheAdministrationService cacheAdministrationService;

    @Autowired
    private WebMessageUtil webMessageUtil;

    @Autowired
    private LogLevelChangable logLevelChangable;

    @ModelAttribute("configurationCommand")
    public ConfigurationCommand initConfigurationCommand() {
        return new ConfigurationCommand();
    }

    @GetMapping("/show")
    public String showCachePage(ConfigurationCommand configurationCommand, Model model) {
        configurationCommand.setLevel(logLevelChangable.getCurrentLogLevel());
        model.addAttribute("configurationCommand", configurationCommand);
        return PAGE_CONFIGURATION;
    }

    @GetMapping("/clearCache")
    public String clearCache(ConfigurationCommand configurationCommand, Model model) {
        this.cacheAdministrationService.clearCaches();

        configurationCommand.setLevel(logLevelChangable.getCurrentLogLevel());
        webMessageUtil.addInfoMsg(model, "administration.msg.info.cacheCleared");
        return PAGE_CONFIGURATION;
    }

    @PostMapping("/changeLogLevel")
    public String changeLogLevel(@Valid ConfigurationCommand configurationCommand, RedirectAttributes redirect) {
        LogLevel level = configurationCommand.getLevel();

        logLevelChangable.setLogLevelTo(level);

        webMessageUtil.addInfoMsg(redirect, "administration.msg.info.logLevelChanged", level);

        return "redirect:/config/show";
    }
}
