package com.hdsx.dubbo.order.mapper;

import com.hdsx.dubbo.order.dao.OrderMapper;
import com.hdsx.dubbo.order.OrderApplicationTest;
import com.hdsx.dubbo.order.impl.dataobject.OrderDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * mapper test
 *
 * @author Sin
 * @time 2019/5/13 10:45 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplicationTest.class)
@Transactional
public class OrderMapperTest {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void updateByIdTest() {
        int id = 47;
//        int updateStatus = 99;
//        int res = orderMapper.update(new OrderDO().setStatus(id), new UpdateWrapper<OrderDO>().eq("id", updateStatus));
        OrderDO orderDO = orderMapper.selectById(id);
//        Assert.assertTrue(orderDO.getStatus() == updateStatus);
    }
}
