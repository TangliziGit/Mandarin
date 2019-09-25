package org.a3.mandarin.back.controller;

import org.a3.mandarin.back.exception.ApiNotFoundException;
import org.a3.mandarin.back.exception.ApiUnauthorizedException;
import org.a3.mandarin.back.model.RESTfulResponse;
import org.a3.mandarin.common.annotation.Permission;
import org.a3.mandarin.common.dao.repository.SettingRepository;
import org.a3.mandarin.common.dao.repository.UserRepository;
import org.a3.mandarin.common.entity.*;
import org.a3.mandarin.common.enums.PermissionType;
import org.a3.mandarin.common.exception.PayException;
import org.a3.mandarin.common.util.PayUtil;
import org.a3.mandarin.common.util.RoleUtil;
import org.a3.mandarin.common.util.ValidateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.Instant;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/api")
public class SettingController {
    private Logger logger= LoggerFactory.getLogger(SettingController.class);

    @Resource
    private SettingRepository settingRepository;

    @Resource
    private UserRepository userRepository;
    
    @GetMapping("/setting/{id}")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.ADMIN, PermissionType.LIBRARIAN, PermissionType.READER})
    public ResponseEntity<RESTfulResponse<Setting>> getSetting(@PathVariable("id") Integer targetSettingId,
                                                      		   HttpSession session){
    	
        validateSettingInformation(targetSettingId);
        
        Setting targetSetting=settingRepository.findById(targetSettingId).orElse(null);

        RESTfulResponse<Setting> response=RESTfulResponse.ok();
        response.setData(targetSetting);
        
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/setting/{id}")
    @ResponseBody
    @Transactional
    @Permission({PermissionType.ADMIN})
    public ResponseEntity<RESTfulResponse> updateSetting(@PathVariable("id") Integer targetSettingId,
                                                         @RequestParam(required = false) Integer number,
                                                         HttpSession session){
    	Integer operatorUserId=(Integer) session.getAttribute("userId");
        User operatorUser=userRepository.findById(operatorUserId).orElse(null);
        Setting targetSetting=settingRepository.findById(targetSettingId).orElse(null);

        validateOperatorPermission(targetSetting, operatorUser);
        validateSettingInformation(number, targetSetting.getSettingId());

        if (null!=number) targetSetting.setNumber(number);

        settingRepository.save(targetSetting);
        return ResponseEntity.status(HttpStatus.CREATED).body(RESTfulResponse.ok());
    }

    private void validateOperatorPermission(Setting targetSetting, User operatorUser){
        // only admin can pass validation
        if (null == targetSetting)
            throw new ApiNotFoundException("no such setting");

        Set<Role> roles=operatorUser.getRoles();

        if (!roles.contains(RoleUtil.adminRole))
            throw new ApiUnauthorizedException("role validation not passed");
    }

    private void validateSettingInformation(Integer number, Integer userId) throws ApiNotFoundException{
        if (null!=number && !ValidateUtil.validateNumber(number))
            throw new ApiNotFoundException("number is not available");
    }
    
    private void validateSettingInformation(Integer settingId) throws ApiNotFoundException{
        Setting tmpSetting;
        tmpSetting = settingRepository.findById(settingId).orElse(null);
        if(null != tmpSetting)
        	throw new ApiNotFoundException("setting do not exist");
    }
}
