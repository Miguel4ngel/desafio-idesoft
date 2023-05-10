package com.idesoft.desafio.presentation;

import com.idesoft.desafio.data.holiday.dto.Holiday;
import com.idesoft.desafio.domain.HolidayApiService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@Log4j2
@RestController
@RequestMapping("/holiday")
public class HolidayApiController {

    private final HolidayApiService holidayApiService;

    public HolidayApiController(HolidayApiService holidayApiService) {
        this.holidayApiService = holidayApiService;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Holiday>> get(@RequestParam(name = "type") String type,
            @RequestParam (name = "startDate") String startDate, @RequestParam(name = "endDate") String endDate) {
        List<Holiday> filteredHolidayList = holidayApiService.getHolidayFilteredList(type, startDate, endDate);
        return ResponseEntity.ok(filteredHolidayList);

    }

}
