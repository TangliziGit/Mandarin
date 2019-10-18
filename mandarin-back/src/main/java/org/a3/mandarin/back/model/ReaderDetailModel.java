package org.a3.mandarin.back.model;

import org.a3.mandarin.back.util.ModelUtil;
import org.a3.mandarin.common.entity.BorrowingHistory;
import org.a3.mandarin.common.entity.User;

import java.util.ArrayList;
import java.util.List;

public class ReaderDetailModel {
    private Integer userId;
    private String name;
    private String phoneNumber;
    private String email;
    private List<BorrowingHistoryModel> borrowingHistoryModels;

    public ReaderDetailModel(User user){
        this.userId = user.getUserId();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.email = user.getEmail();
        this.borrowingHistoryModels = new ArrayList<>();

        for (BorrowingHistory borrowingHistory: user.getBorrowingHistories())
            this.borrowingHistoryModels.add(ModelUtil.convertToBorrowingHistoryModel(borrowingHistory));
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<BorrowingHistoryModel> getBorrowingHistoryModels() {
        return borrowingHistoryModels;
    }

    public void setBorrowingHistoryModels(List<BorrowingHistoryModel> borrowingHistoryModels) {
        this.borrowingHistoryModels = borrowingHistoryModels;
    }
}
