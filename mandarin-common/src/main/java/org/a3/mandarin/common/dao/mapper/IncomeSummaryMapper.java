package org.a3.mandarin.common.dao.mapper;

import org.a3.mandarin.common.dto.IncomeSummary;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface IncomeSummaryMapper {
    @Select("select date_format(income.date, '%Y-%m') as month, sum(amount) as amount, type " +
            "from income " +
            "where income.type='DEPOSIT' " +
            "group by date_format(income.date, '%Y-%m')")
    @Results({
            @Result(property = "date", column = "month"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "type", column = "type")
    })
    List<IncomeSummary> findDepositByPerMonth();

    @Select("select date_format(income.date, '%Y-%u') as week, sum(amount) as amount, type " +
            "from income " +
            "where income.type='DEPOSIT' " +
            "group by date_format(income.date, '%Y-%u')")
    @Results({
            @Result(property = "date", column = "week"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "type", column = "type")
    })
    List<IncomeSummary> findDepositByPerWeek();

    @Select("select date_format(income.date, '%Y-%m-%d') as day, sum(amount) as amount, type " +
            "from income " +
            "where income.type='DEPOSIT' " +
            "group by date_format(income.date, '%Y-%m-%d')")
    @Results({
            @Result(property = "date", column = "day"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "type", column = "type")
    })
    List<IncomeSummary> findDepositByPerDay();

    @Select("select date_format(income.date, '%Y-%m') as month, sum(amount) as amount, type " +
            "from income " +
            "where income.type='FINE' " +
            "group by date_format(income.date, '%Y-%m')")
    @Results({
            @Result(property = "date", column = "month"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "type", column = "type")
    })
    List<IncomeSummary> findFineByPerMonth();

    @Select("select date_format(income.date, '%Y-%u') as week, sum(amount) as amount, type " +
            "from income " +
            "where income.type='FINE' " +
            "group by date_format(income.date, '%Y-%u')")
    @Results({
            @Result(property = "date", column = "week"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "type", column = "type")
    })
    List<IncomeSummary> findFineByPerWeek();

    @Select("select date_format(income.date, '%Y-%m-%d') as day, sum(amount) as amount, type " +
            "from income " +
            "where income.type='FINE' " +
            "group by date_format(income.date, '%Y-%m-%d')")
    @Results({
            @Result(property = "date", column = "day"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "type", column = "type")
    })
    List<IncomeSummary> findFineByPerDay();

    @Select("select date_format(income.date, '%Y-%m') as month, sum(amount) as amount, type " +
            "from income " +
            "group by date_format(income.date, '%Y-%m'), type")
    @Results({
            @Result(property = "date", column = "month"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "type", column = "type")
    })
    List<IncomeSummary> findTotalByPerMonth();

    @Select("select date_format(income.date, '%Y-%u') as week, sum(amount) as amount, type " +
            "from income " +
            "group by date_format(income.date, '%Y-%u'), type")
    @Results({
            @Result(property = "date", column = "week"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "type", column = "type")
    })
    List<IncomeSummary> findTotalByPerWeek();

    @Select("select date_format(income.date, '%Y-%m-%d') as day, sum(amount) as amount, type " +
            "from income " +
            "group by date_format(income.date, '%Y-%m-%d'), type")
    @Results({
            @Result(property = "date", column = "day"),
            @Result(property = "amount", column = "amount"),
            @Result(property = "type", column = "type")
    })
    List<IncomeSummary> findTotalByPerDay();
}
