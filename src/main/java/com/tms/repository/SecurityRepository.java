package com.tms.repository;

import com.tms.model.Security;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class SecurityRepository {

    public List<Security> getAllSecurities() {
        return new ArrayList<>();
    }
}
