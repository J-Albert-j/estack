package com.kaikeba.controller;

import com.kaikeba.bean.BootStrapTableCourier;
import com.kaikeba.bean.Courier;
import com.kaikeba.bean.ResultData;
import com.kaikeba.service.CourierService;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author JJH
 * @2021/7/11 11:41
 * 说明：
 */
public class CourierControllerTest {

    @Test
    public void console() {
    }

    @Test
    public void list() {
        int offset = 1;
        int pageNumber = 5;
        List<Courier> list = CourierService.findAll(true, offset, pageNumber);
        List<BootStrapTableCourier> list2 = new ArrayList<>();
        System.out.println(list);
        for (Courier c : list){
            BootStrapTableCourier c2 = new BootStrapTableCourier(c.getId(),c.getCourierName(),c.getCourierPhone(),"******",c.getCourierPassword(),null,null,null);
            list2.add(c2);
        }
        System.out.println(list2);
        ResultData<BootStrapTableCourier> data = new ResultData<>();
        data.setRows(list2);
        System.out.println(data);

    }

    @Test
    public void insert() {
    }
}