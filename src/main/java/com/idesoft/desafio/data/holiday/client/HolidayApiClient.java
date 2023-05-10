package com.idesoft.desafio.data.holiday.client;

import com.idesoft.desafio.data.holiday.dto.HolidayList;
import com.idesoft.desafio.data.holiday.exception.HolidayApiClientException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@Log4j2
@Component
public class HolidayApiClient {

    @Value("${api.holiday.url}")
    private String holidayApiUrl;

    public final RestTemplate restTemplate;

    public HolidayApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public HolidayList invoke() {
        try {
            log.info("[invoke] Calling Holiday API. URL [{}]", holidayApiUrl);
            ResponseEntity<HolidayList> responseEntity = restTemplate.getForEntity(holidayApiUrl, HolidayList.class);
            log.info("[invoke] Successful call to Holiday API. Response [{}]", responseEntity.getBody());
            return responseEntity.getBody();
        } catch (HttpServerErrorException | HttpClientErrorException ex) {
            log.error("[invoke] Error calling holiday API. Error [{}]", ex.getLocalizedMessage());
            throw new HolidayApiClientException("Error calling holiday API.");
        }
    }

}
