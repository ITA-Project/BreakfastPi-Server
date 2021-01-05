package com.ita.domain.service.impl;

import static org.testng.Assert.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.Test;

public class OrderServiceImplTest {

  @Test
  public void should_return_formatted_date_text() {
    LocalDateTime now = LocalDateTime.of(2019, 10, 1, 15, 1, 0);
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("YYYY年M月d日 HH:mm");
    String formattedDateTime = dateTimeFormatter.format(now);
    assertEquals(formattedDateTime, "2019年10月1日 15:01");
  }
}