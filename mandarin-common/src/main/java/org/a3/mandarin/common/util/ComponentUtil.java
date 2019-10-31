package org.a3.mandarin.common.util;

import org.a3.mandarin.common.dao.repository.BorrowingFineHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ComponentUtil {
    public static BorrowingFineHistoryRepository borrowingFineHistoryRepository;

    @Autowired
    public void setBorrowingFineHistory(BorrowingFineHistoryRepository borrowingFineHistoryRepository){
        ComponentUtil.borrowingFineHistoryRepository = borrowingFineHistoryRepository;
    }
}
