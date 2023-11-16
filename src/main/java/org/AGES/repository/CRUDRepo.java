package org.AGES.repository;

import java.sql.SQLException;
import java.util.List;

public interface CRUDRepo<T> {
    int save(T object) throws SQLException;
    List<T> findAll();
}
