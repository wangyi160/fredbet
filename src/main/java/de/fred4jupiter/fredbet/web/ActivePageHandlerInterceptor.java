package de.fred4jupiter.fredbet.web;

import de.fred4jupiter.fredbet.domain.Group;
import de.fred4jupiter.fredbet.repository.GroupRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Adds css class to html tags for showing the active tab/link.
 *
 * @author michael
 */
public class ActivePageHandlerInterceptor implements HandlerInterceptor {

    private static final String CSS_ACTIVE = "active";

    private static final String PAGE_STATE_REFIX = "pageState_";

    private List<Group> groups;

    public ActivePageHandlerInterceptor(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        final String requestURI = request.getRequestURI();
        if (requestURI.contains("error")) {
            return;
        }

        if (modelAndView == null) {
            return;
        }

        final String page = StringUtils.substring(requestURI, 1);
        final String replaced = StringUtils.replace(page, "/", "_");
        modelAndView.addObject(PAGE_STATE_REFIX + replaced, CSS_ACTIVE);

        // add top navigation items with groups
        modelAndView.addObject("groups", groups);

        // add top navigation items with submenus

        // Tippen
        if (requestURI.contains("/bet/")) {
            modelAndView.addObject(PAGE_STATE_REFIX + "betting", CSS_ACTIVE);
        }

        // Gruppen
        if (requestURI.contains("group")) {
            if (containsMainGroups(requestURI) || containsFinalGroups(requestURI)) {
                modelAndView.addObject(PAGE_STATE_REFIX + "group", CSS_ACTIVE);
            }
        }

        // Administration
        if (requestURI.contains("user") || requestURI.contains("buildinfo") || requestURI.contains("administration")) {
            modelAndView.addObject(PAGE_STATE_REFIX + "administrationMenu", CSS_ACTIVE);
        }

        // Infos
        if (requestURI.contains("info/")) {
            modelAndView.addObject(PAGE_STATE_REFIX + "info", CSS_ACTIVE);
        }

        // user profile
        if (requestURI.contains("profile")) {
            modelAndView.addObject(PAGE_STATE_REFIX + "profile", CSS_ACTIVE);
        }
    }

    private boolean containsMainGroups(String requestURI) {
//        for (Group group : Group.getMainGroups()) {
//            if (requestURI.contains(group.getName())) {
//                return true;
//            }
//        }
        return false;
    }

    private boolean containsFinalGroups(String requestURI) {
//        for (Group group : Group.getFinalGroups()) {
//            if (requestURI.contains(group.getName())) {
//                return true;
//            }
//        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
