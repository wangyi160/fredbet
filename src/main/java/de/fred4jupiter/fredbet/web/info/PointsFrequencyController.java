package de.fred4jupiter.fredbet.web.info;

import de.fred4jupiter.fredbet.repository.PointCountResult;
import de.fred4jupiter.fredbet.service.excel.ReportService;
import de.fred4jupiter.fredbet.web.WebMessageUtil;
import org.apache.commons.collections4.MultiValuedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/info/pointsfrequency")
public class PointsFrequencyController {

    private static final String PAGE_INFO_POINTS_FREQ = "info/points_freq";

    private final ReportService reportService;

    private final WebMessageUtil webMessageUtil;

    public PointsFrequencyController(ReportService reportService, WebMessageUtil webMessageUtil) {
        this.reportService = reportService;
        this.webMessageUtil = webMessageUtil;
    }

    @GetMapping
    public String show(Model model) {
        MultiValuedMap<Double, PointCountResult> map = reportService.reportPointsFrequency();

        if (map.isEmpty()) {
            webMessageUtil.addInfoMsg(model, "pointsfrequency.noData");
            return PAGE_INFO_POINTS_FREQ;
        }

        List<Double> pointsList = new ArrayList<>(map.keySet());
        Collections.reverse(pointsList);

        PointsFrequencyCommand command = new PointsFrequencyCommand(map);

        model.addAttribute("pointsFrequencyCommand", command);
        return PAGE_INFO_POINTS_FREQ;
    }
}
