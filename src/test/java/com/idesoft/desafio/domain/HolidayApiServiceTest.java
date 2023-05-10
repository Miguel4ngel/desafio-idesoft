package com.idesoft.desafio.domain;

import com.idesoft.desafio.data.holiday.client.HolidayApiClient;
import com.idesoft.desafio.data.holiday.dto.Holiday;
import com.idesoft.desafio.data.holiday.dto.HolidayList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

public class HolidayApiServiceTest {

    @Mock
    private HolidayApiClient holidayApiClient;

    @InjectMocks
    private HolidayApiService holidayApiService;

    List<Holiday> holidayList;
    HolidayList holidayListResponse;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);

        holidayList = new ArrayList<>();

        Holiday holiday1 = Holiday.builder().date("2023-01-01").type("Civil").title("Año Nuevo").inalienable(true).extra("Civil e Irrenunciable").build();
        Holiday holiday2 = Holiday.builder().date("2023-01-02").type("Civil").title("Feriado Adicional").inalienable(false).extra("Civil e Irrenunciable").build();
        Holiday holiday3 = Holiday.builder().date("2023-04-07").type("Religioso").title("Viernes Santo").inalienable(false).extra("Religioso").build();
        Holiday holiday4 = Holiday.builder().date("2023-04-08").type("Religioso").title("Sábado Santo").inalienable(true).extra("Religioso").build();
        Holiday holiday5 = Holiday.builder().date("2023-05-01").type("Civil").title("Día Nacional del Trabajo").inalienable(true).extra("Civil e Irrenunciable").build();

        holidayList.add(holiday1);
        holidayList.add(holiday2);
        holidayList.add(holiday3);
        holidayList.add(holiday4);
        holidayList.add(holiday5);

        HolidayList.builder().data(holidayList).status("success").build();
    }

    @Test
    @DisplayName("Should return only Civil holidays.")
    public void returnCivilHolidays() {
        //Arrange
        String holidayType = "Civil";
        Mockito.when(holidayApiClient.invoke()).thenReturn(holidayListResponse);
        //Act
        List<Holiday> filteredByTypeHolidayList =  holidayApiService.getHolidayFilteredList("Civil", "", "");
        //Assert
        Assertions.assertNotNull(filteredByTypeHolidayList);
        Assertions.assertEquals(3, filteredByTypeHolidayList.size());

    }

    @DisplayName("Should return Civil holidays between the given dates.")
    public void returnCivilHolidaysAndBetweenGivenDates() {
        //Arrange
        String holidayType = "Civil";
        Mockito.when(holidayApiClient.invoke()).thenReturn(holidayListResponse);
        //Act
        List<Holiday> filteredByTypeHolidayList =  holidayApiService.getHolidayFilteredList("Civil", "2023-01-01", "2023-05-01");
        //Assert
        Assertions.assertNotNull(filteredByTypeHolidayList);
        Assertions.assertEquals(1, filteredByTypeHolidayList.size());
    }

    @DisplayName("Should return and empty list when non valid holyday type is received")
    public void returnEmptyHolidayList() {
        //Arrange
        String holidayType = "TypeInvalid";
        Mockito.when(holidayApiClient.invoke()).thenReturn(holidayListResponse);
        //Act
        List<Holiday> filteredByTypeHolidayList =  holidayApiService.getHolidayFilteredList("Civil", "", "");
        //Assert
        Assertions.assertNotNull(filteredByTypeHolidayList);
        Assertions.assertEquals(0, filteredByTypeHolidayList.size());
    }
}
