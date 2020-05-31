package com.changgou.test;

import com.changgou.util.IdWorker;
import org.junit.Test;

/**
 * @Program: ChangGou
 * @ClassName: IdTest
 * @Description:
 * @Author: KyleSun
 **/
public class IdTest {


    /**
     * @description: //TODO IDWorker的使用测试
     * @param: []
     * @return: void
     */
    @Test
    public void idWorkerTest() {

        IdWorker idWorker = new IdWorker(0, 0);

        for (int i = 0; i < 20; i++) {
            long id = idWorker.nextId();
            System.out.println(id);
        }

    }

}
