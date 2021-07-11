package com.kaikeba.service;

import com.kaikeba.bean.Express;
import org.junit.Test;

import static org.junit.Assert.*;

public class ExpressServiceTest {

    @Test
    public void insert() {
        Express e = new Express("12321","帅哥m","13778671755","韵达快递","13777861723","666666");
        boolean flag = ExpressService.insert(e);
        System.out.println(flag);
    }
}