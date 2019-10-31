package org.a3.mandarin.front.model;

import org.a3.mandarin.common.entity.BorrowingHistory;
import org.a3.mandarin.common.util.ComponentUtil;

public class BorrowingHistoryModel {
    private Double fine = 0.0;
    private Boolean isFine = false;
    private BorrowingHistory borrowingHistory;

    public BorrowingHistoryModel(BorrowingHistory borrowingHistory){
        this.borrowingHistory = borrowingHistory;
        if (borrowingHistory.getBorrowingFineHistory() != null){
            isFine = true;
            fine = ComponentUtil.borrowingFineHistoryRepository.findFineValuetByBorrowingFineHistoryId(
                    borrowingHistory.getBorrowingFineHistory().getFineId()
            );
        }
    }

    public Double getFine() {
        return fine;
    }

    public void setFine(Double fine) {
        this.fine = fine;
    }

    public BorrowingHistory getBorrowingHistory() {
        return borrowingHistory;
    }

    public void setBorrowingHistory(BorrowingHistory borrowingHistory) {
        this.borrowingHistory = borrowingHistory;
    }

    public void setFine(Boolean fine) {
        isFine = fine;
    }
}
