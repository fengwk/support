package com.fengwk.support.spring.boot.starter.mysql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.fengwk.support.core.gson.GsonUtils;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.weekend.Fn;
import tk.mybatis.mapper.weekend.WeekendSqls;

/**
 * 
 * @author fengwk
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisStarterTestApplication.class)
@FixMethodOrder(MethodSorters.JVM)
public class TestMapperTest {

    @Autowired
    TestMapper testMapper;

    @Test
    public void test() {
        TestPO po = new TestPO();
        po.setName("你好hhh ");
        testMapper.insert(po);
        
        WeekendSqls<TestPO> ws = WeekendSqls.<TestPO>custom().andBetween(TestPO::getId, "1", "2");
        Example example = Example.builder(TestPO.class).where(ws).build();
        System.out.println(GsonUtils.toJson(testMapper.selectByExample(example)));
    
        System.out.println(GsonUtils.toJson(testMapper.listByName("你好2")));
    }

    public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        Fn<JoPO, String> s = JoPO::getName;
        System.out.println(s.getClass());
        Method method = s.getClass().getDeclaredMethod("writeReplace");
        method.setAccessible(true);
        System.out.println(method.invoke(s));
    }

}
