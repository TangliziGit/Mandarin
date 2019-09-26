package org.a3.mandarin.common.dao.repository;

import org.a3.mandarin.common.entity.Setting;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SettingRepository extends JpaRepository<Setting, Integer>, JpaSpecificationExecutor<Setting> {
	Setting findByName(String name);
	Setting findBySettingId(Integer settingId);
}