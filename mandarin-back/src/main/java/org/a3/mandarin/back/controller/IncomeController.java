package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dto.IncomeSummary;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.mapper.IncomeSummaryMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Controller
@RequestMapping("/api")
public class IncomeController {
    @Resource
    private IncomeSummaryMapper incomeSummaryMapper;

    @GetMapping("/income/week")
    @Transactional
    @ResponseBody
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<IncomeSummary>>> getIncomePerWeek(){
        List<IncomeSummary> incomeSummaries = incomeSummaryMapper.findByPerWeek();

        RESTfulResponse<List<IncomeSummary>> response = RESTfulResponse.ok();
        response.setData(incomeSummaries);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/income/month")
    @Transactional
    @ResponseBody
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<IncomeSummary>>> getIncomePerMonth(){
        List<IncomeSummary> incomeSummaries = incomeSummaryMapper.findByPerMonth();

        RESTfulResponse<List<IncomeSummary>> response = RESTfulResponse.ok();
        response.setData(incomeSummaries);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/income/day")
    @Transactional
    @ResponseBody
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<IncomeSummary>>> getIncomePerDay(){
        List<IncomeSummary> incomeSummaries = incomeSummaryMapper.findByPerDay();

        RESTfulResponse<List<IncomeSummary>> response = RESTfulResponse.ok();
        response.setData(incomeSummaries);
        return ResponseEntity.ok(response);
    }
}
