package com.example.p_kontrol.DataLayer;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestDBTest {

    @Test
    void addUser() {
        TestDB db = new TestDB();
        db.addUser();
        db.getUser();
    }
}