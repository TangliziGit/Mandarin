package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.DeletingHistoryRepository;
import org.a3.mandarin.common.entity.DeletingHistory;
import org.a3.mandarin.common.enums.PermissionType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/api")
public class HistoryController {

    @Resource
    private DeletingHistoryRepository deletingHistoryRepository;

    @GetMapping("/history/deleting")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.LIBRARIAN})
    public ResponseEntity<RESTfulResponse<List<DeletingHistory>>> getDeletingHistories(){
        List<DeletingHistory> deletingHistories=deletingHistoryRepository.findAll();

        RESTfulResponse<List<DeletingHistory>> response=RESTfulResponse.ok();
        response.setData(deletingHistories);
        return ResponseEntity.ok(response);
    }
}
