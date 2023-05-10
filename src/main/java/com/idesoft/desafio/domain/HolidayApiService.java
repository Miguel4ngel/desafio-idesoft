package com.idesoft.desafio.domain;

import com.idesoft.desafio.data.holiday.dto.Holiday;
import com.idesoft.desafio.data.holiday.dto.HolidayList;
import com.idesoft.desafio.data.holiday.client.HolidayApiClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@Log4j2
@Service
public class HolidayApiService {

    private HolidayList holidayList;

    private final HolidayApiClient holidayApiClient;

    static final DateTimeFormatter FMT = new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd")
            .parseDefaulting(ChronoField.NANO_OF_DAY, 0)
            .toFormatter()
            .withZone(ZoneId.of("America/Argentina/Buenos_Aires"));

    public HolidayApiService(HolidayApiClient holidayApiClient) {
        this.holidayApiClient = holidayApiClient;
        getHolidayData();
    }

    private void getHolidayData() {
        this.holidayList = holidayApiClient.invoke();
        log.info("[getHolidayData] Data [{}]", this.holidayList);
    }

    public List<Holiday> getHolidayFilteredList(String type, String startDate, String endDate){
        List<Holiday> filteredHolidayList = new ArrayList<>();

        log.info("[getHolidayFilteredList] Searching on list for the given parameters Type [{}], Start Date [{}] and End Date [{}]", type, startDate, endDate);

        if(!type.isEmpty()) {
            filteredHolidayList = getHolidaysByType(type);
            if(!startDate.isEmpty() && !endDate.isEmpty()) {
                getHolidaysByDate(startDate, endDate, filteredHolidayList);
            }
            return filteredHolidayList;
        } else {
            if(!startDate.isEmpty() && !endDate.isEmpty()) {
                return getHolidaysByDate(startDate, endDate, this.holidayList.getData());
            }
            return this.holidayList.getData();
        }
    }

    private List<Holiday> getHolidaysByType(String type) {
        log.info("[getHolidaysByType] Searching holidays by Type [{}]", type);
        return this.holidayList.getData()
                .stream().filter(holiday -> holiday.getType().equalsIgnoreCase(type))
                .collect(Collectors.toList());
    }

    private List<Holiday> getHolidaysByDate(String startDate, String endDate, List<Holiday> holidayList) {
        log.info("[getHolidaysByDate] Searching holidays by Date Range [{}] - [{}]", startDate, endDate);
        List<Holiday> nonMatchingHolidays = new ArrayList<>();
        for(Holiday holiday : holidayList) {
            if(!checkDateIsBetween(startDate, endDate, holiday)){
                nonMatchingHolidays.add(holiday);
            }
        }
        holidayList.removeAll(nonMatchingHolidays);
        return holidayList;
    }

    private boolean checkDateIsBetween(String startDate, String endDate, Holiday holiday) {

        Instant instantStartDate = FMT.parse(startDate, Instant::from);
        Instant instantEndDate = FMT.parse(endDate, Instant::from);
        Instant instantReceivedDate = FMT.parse(holiday.getDate(), Instant::from);

        return instantReceivedDate.isAfter(instantStartDate) && instantReceivedDate.isBefore(instantEndDate);
    }

}
