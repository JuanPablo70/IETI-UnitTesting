package org.adaschool.weather.controller;

import org.adaschool.weather.data.WeatherReport;
import org.adaschool.weather.service.WeatherReportService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class WeatherReportControllerTest {

    final String BASE_URL = "/v1/api/";

    @MockBean
    private WeatherReportService weatherReportService;

    @Autowired
    private WeatherReportController weatherReportController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        mockMvc = standaloneSetup(weatherReportController).build();
    }

    @Test
    public void testGetReportByLatitudeLongitude() throws Exception{
        WeatherReport weatherReport = new WeatherReport();
        weatherReport.setHumidity(75.0);
        weatherReport.setTemperature(0.0);

        when(weatherReportService.getWeatherReport(37.8267, -122.4233)).thenReturn(weatherReport);

        mockMvc.perform(get(BASE_URL + "weather-report/?latitude=37.8267&longitude=-122.4233"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.temperature", is(0.0)))
                .andExpect(jsonPath("$.humidity", is(75.0)));

        verify(weatherReportService, times(1)).getWeatherReport(37.8267, -122.4233);
    }

}
