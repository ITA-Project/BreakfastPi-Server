package com.ita.domain.service.impl;

import com.ita.domain.service.IdGenerateService;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class PurchaserIdGenerateServiceImpl implements IdGenerateService {

  @Override
  public Integer generateId() {
    UUID uuid = UUID.randomUUID();
    Integer id = (int)(uuid.getMostSignificantBits() + uuid.getLeastSignificantBits());
    return id;
  }
}
