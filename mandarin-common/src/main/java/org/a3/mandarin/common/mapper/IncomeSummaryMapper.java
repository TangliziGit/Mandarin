package org.a3.mandarin.common.mapper;

import org.a3.mandarin.common.dto.IncomeSummary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IncomeSummaryMapper {
    @Select("select date_format(income.date, '%Y-%m') as month, sum(amount) as amount " +
            "from income " +
            "group by date_format(income.date, '%Y-%m')")
    @Results({
            @Result(property = "date", column = "month"),
            @Result(property = "amount", column = "amount")
    })
    List<IncomeSummary> findByPerMonth();

    @Select("select date_format(income.date, '%Y-%u') as week, sum(amount) as amount " +
            "from income " +
            "group by date_format(income.date, '%Y-%u')")
    @Results({
            @Result(property = "date", column = "week"),
            @Result(property = "amount", column = "amount")
    })
    List<IncomeSummary> findByPerWeek();

    @Select("select date_format(income.date, '%Y-%m-%d') as day, sum(amount) as amount " +
            "from income " +
            "group by date_format(income.date, '%Y-%m-%d')")
    @Results({
            @Result(property = "date", column = "day"),
            @Result(property = "amount", column = "amount")
    })
    List<IncomeSummary> findByPerDay();
}
