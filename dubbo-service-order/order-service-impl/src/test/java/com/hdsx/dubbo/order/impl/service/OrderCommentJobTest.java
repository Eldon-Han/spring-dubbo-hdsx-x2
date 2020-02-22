package com.hdsx.dubbo.order.impl.service;

import com.hdsx.dubbo.order.api.bo.OrderCommentTimeOutBO;
import com.hdsx.dubbo.order.api.dto.OrderCommentTimeOutPageDTO;
import com.hdsx.dubbo.order.impl.OrderApplicationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 订单评论自动好评任务测试
 *
 * @author wtz
 * @time 2019-06-17 19:09
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplicationTest.class)
@ActiveProfiles("dev")
public class OrderCommentJobTest {

    @Autowired
    private OrderCommentService orderCommentService;

    @Test
    public void createOrderCommentJob(){
        OrderCommentTimeOutPageDTO orderCommentTimeOutPageDTO=new OrderCommentTimeOutPageDTO();
        orderCommentTimeOutPageDTO.setCommentState(0);
        orderCommentTimeOutPageDTO.setPageNo(0);
        orderCommentTimeOutPageDTO.setPageSize(10);
        orderCommentTimeOutPageDTO.setOverDay(7);
        List<OrderCommentTimeOutBO> orderCommentTimeOutBOList = orderCommentService.getOrderCommentTimeOutPage(orderCommentTimeOutPageDTO);


        orderCommentService.updateBatchOrderCommentState(orderCommentTimeOutBOList);

    }

}
