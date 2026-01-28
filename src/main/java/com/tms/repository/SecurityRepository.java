package com.tms.repository;

import com.tms.model.Security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SecurityRepository {
    private final Connection connection;

    @Autowired
    public SecurityRepository(Connection connection) {
        this.connection = connection;
    }

    public List<Security> getAllSecurities() {
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM users");
            boolean result = statement.execute();
            System.out.println(result);
            return new ArrayList<>();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
