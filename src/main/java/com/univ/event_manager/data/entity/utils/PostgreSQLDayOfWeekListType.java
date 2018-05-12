package com.univ.event_manager.data.entity.utils;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.*;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PostgreSQLDayOfWeekListType implements UserType {
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.ARRAY};
    }

    @Override
    public Class returnedClass() {
        return List.class;
    }

    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return x == null ? y == null : x.equals(y);
    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o == null ? 0 : o.hashCode();
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object owner) throws HibernateException, SQLException {
        Array array = resultSet.getArray(names[0]);
        DayOfWeek[] javaArray = (DayOfWeek[]) array.getArray();
        return new ArrayList<>(Arrays.asList(javaArray));
    }

    @Override
    public void nullSafeSet(PreparedStatement statement, Object o, int i, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        Connection connection = statement.getConnection();

        List<DayOfWeek> castObject = (List<DayOfWeek>) o;
        Object[] daysOfWeek = castObject.toArray();
        Array array = connection.createArrayOf("week_days", daysOfWeek);

        statement.setArray(i, array);
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException {
        return o;
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable cached, Object o) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object o, Object o1, Object o2) throws HibernateException {
        return o;
    }
}
