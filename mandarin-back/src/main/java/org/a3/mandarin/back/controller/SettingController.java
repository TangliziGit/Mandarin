package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.exception.ApiUnauthorizedException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.SettingRepository;
import org.a3.mandarin.common.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.util.RoleUtil;
import org.a3.mandarin.common.util.SettingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/api")
public class SettingController {
    private Logger logger= LoggerFactory.getLogger(SettingController.class);

    @Resource
    private SettingRepository settingRepository;

    @Resource
    private UserRepository userRepository;

    @GetMapping("/setting/{name}")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.ADMIN, PermissionType.LIBRARIAN, PermissionType.READER})
    public ResponseEntity<RESTfulResponse<Setting>> getSetting(@PathVariable("name") String settingName){
        settingName=SettingUtil.convertToSettingName(settingName);

        if (null == settingName)
            throw new ApiNotFoundException("no such setting");

        Setting setting=settingRepository.findByName(settingName);

        RESTfulResponse<Setting> response=RESTfulResponse.ok();
        response.setData(setting);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/setting/{name}")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.ADMIN})
    public ResponseEntity<RESTfulResponse> updateSetting(@PathVariable("name") String settingName,
                                                         @RequestParam Double value,
                                                         HttpSession session){

    	Integer operatorUserId=(Integer) session.getAttribute("userId");
        User operatorUser=userRepository.findById(operatorUserId).orElse(null);

        settingName=SettingUtil.convertToSettingName(settingName);

        if (null == operatorUser || !operatorUser.getRoles().contains(RoleUtil.adminRole))
            throw new ApiUnauthorizedException("role validation not passed");

        if (null == settingName)
            throw new ApiNotFoundException("no such setting");

        Setting targetSetting=settingRepository.findByName(settingName);
        targetSetting.setValue(value);
        settingRepository.save(targetSetting);
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    @PutMapping("/setting")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.ADMIN})
    public ResponseEntity<RESTfulResponse> updateSetting(@RequestParam Double fine,
                                                         @RequestParam Double deposit,
                                                         @RequestParam Double period,
                                                         HttpSession session){

        Integer operatorUserId=(Integer) session.getAttribute("userId");
        User operatorUser=userRepository.findById(operatorUserId).orElse(null);

        if (null == operatorUser || !operatorUser.getRoles().contains(RoleUtil.adminRole))
            throw new ApiUnauthorizedException("role validation not passed");

        checkAndUpdateSetting(SettingUtil.FINE, fine);
        checkAndUpdateSetting(SettingUtil.PERIOD, period);
        checkAndUpdateSetting(SettingUtil.DEPOSIT, deposit);

        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    private void checkAndUpdateSetting(String settingName, Double value){
        if (null != value) {
            Setting setting = settingRepository.findByName(settingName);
            setting.setValue(value);
            settingRepository.save(setting);
        }
    }
}
