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

    @GetMapping("/income/deposit/week")
    @Transactional
    @ResponseBody
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<IncomeSummary>>> getIncomeDepositPerWeek(){
        List<IncomeSummary> incomeSummaries = incomeSummaryMapper.findDepositByPerWeek();

        RESTfulResponse<List<IncomeSummary>> response = RESTfulResponse.ok();
        response.setData(incomeSummaries);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/income/deposit/month")
    @Transactional
    @ResponseBody
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<IncomeSummary>>> getIncomeDepositPerMonth(){
        List<IncomeSummary> incomeSummaries = incomeSummaryMapper.findDepositByPerMonth();

        RESTfulResponse<List<IncomeSummary>> response = RESTfulResponse.ok();
        response.setData(incomeSummaries);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/income/deposit/day")
    @Transactional
    @ResponseBody
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<IncomeSummary>>> getIncomeDepositPerDay(){
        List<IncomeSummary> incomeSummaries = incomeSummaryMapper.findDepositByPerDay();

        RESTfulResponse<List<IncomeSummary>> response = RESTfulResponse.ok();
        response.setData(incomeSummaries);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/income/fine/week")
    @Transactional
    @ResponseBody
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<IncomeSummary>>> getIncomeFinePerWeek(){
        List<IncomeSummary> incomeSummaries = incomeSummaryMapper.findFineByPerWeek();

        RESTfulResponse<List<IncomeSummary>> response = RESTfulResponse.ok();
        response.setData(incomeSummaries);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/income/fine/month")
    @Transactional
    @ResponseBody
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<IncomeSummary>>> getIncomeFinePerMonth(){
        List<IncomeSummary> incomeSummaries = incomeSummaryMapper.findFineByPerMonth();

        RESTfulResponse<List<IncomeSummary>> response = RESTfulResponse.ok();
        response.setData(incomeSummaries);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/income/fine/day")
    @Transactional
    @ResponseBody
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<IncomeSummary>>> getIncomeFinePerDay(){
        List<IncomeSummary> incomeSummaries = incomeSummaryMapper.findFineByPerDay();

        RESTfulResponse<List<IncomeSummary>> response = RESTfulResponse.ok();
        response.setData(incomeSummaries);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/income/total/week")
    @Transactional
    @ResponseBody
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<IncomeSummary>>> getIncomeTotalPerWeek(){
        List<IncomeSummary> incomeSummaries = incomeSummaryMapper.findTotalByPerWeek();

        RESTfulResponse<List<IncomeSummary>> response = RESTfulResponse.ok();
        response.setData(incomeSummaries);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/income/total/month")
    @Transactional
    @ResponseBody
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<IncomeSummary>>> getIncomeTotalPerMonth(){
        List<IncomeSummary> incomeSummaries = incomeSummaryMapper.findTotalByPerMonth();

        RESTfulResponse<List<IncomeSummary>> response = RESTfulResponse.ok();
        response.setData(incomeSummaries);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/income/total/day")
    @Transactional
    @ResponseBody
    @Permission(PermissionType.LIBRARIAN)
    public ResponseEntity<RESTfulResponse<List<IncomeSummary>>> getIncomeTotalPerDay(){
        List<IncomeSummary> incomeSummaries = incomeSummaryMapper.findTotalByPerDay();

        RESTfulResponse<List<IncomeSummary>> response = RESTfulResponse.ok();
        response.setData(incomeSummaries);
        return ResponseEntity.ok(response);
    }
}
