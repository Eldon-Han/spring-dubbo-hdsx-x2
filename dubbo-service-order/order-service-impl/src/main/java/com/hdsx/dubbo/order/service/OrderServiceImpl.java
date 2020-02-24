package com.hdsx.dubbo.order.service;

import com.hdsx.dubbo.common.framework.vo.Result;
import com.hdsx.dubbo.common.framework.vo.ResultCode;
import com.hdsx.dubbo.common.framework.vo.ResultUtil;
import com.hdsx.dubbo.order.api.OrderService;
import com.hdsx.dubbo.order.api.bean.OrderBean;
import com.hdsx.dubbo.order.api.bean.OrderQueryBean;
import com.hdsx.dubbo.order.dao.OrderMapper;
import com.hdsx.dubbo.product.api.ProductService;
import com.hdsx.dubbo.product.api.bean.ProductNumBean;
import io.seata.spring.annotation.GlobalTransactional;
import org.apache.commons.lang.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 梁铖城 - 移动开发部
 * 2020年02月16日18:25:10s
 * 订单 service impl
 */
@Service
@org.apache.dubbo.config.annotation.Service(validation = "true", version = "${dubbo.provider.OrderService.version}")
public class OrderServiceImpl implements OrderService {

    /**
     * 支付过期时间 120 分钟
     */
    public static final int PAY_EXPIRE_TIME = 120;

    @Autowired
    private OrderMapper orderMapper;

    @Reference(validation = "true", version = "${dubbo.consumer.PromotionActivityService.version}")
    private ProductService productService;

    @Override
    public Result getOrderList(OrderQueryBean orderQueryBean) {
        try {
            List<OrderBean> orderList = orderMapper.getOrderList(orderQueryBean);
            return ResultUtil.success(ResultCode.QUERY_SUCCESS, orderList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultUtil.error(ResultCode.QUERY_FAIL);
    }

    @Override
    public Result getOrderById(String id) {
        try {
            OrderBean orderById = orderMapper.getOrderById(id);
            return ResultUtil.success(ResultCode.QUERY_SUCCESS, orderById);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultUtil.error(ResultCode.QUERY_FAIL);
    }

    @GlobalTransactional
    @Override
    public Result addOrder(OrderBean orderBean) {
        try {
            if (orderBean == null ) {
                return ResultUtil.error(ResultCode.UPDATE_FAIL);
            }

            // 获取基本信息的ID
            String id = orderBean.getId();
            // 判断库里之前有没有,update或者insert
            OrderBean orderById = orderMapper.getOrderById(id);
            int flag = 0;
            if (orderById != null) {
                // 修改订单
                flag = orderMapper.editOrder(orderBean);
            } else {
                // 逻辑简单，只做演示，这里只有新添加的订单才减少库存
                ProductNumBean productNumBean = new ProductNumBean();
                productNumBean.setNum(orderBean.getOrdernum());
                productNumBean.setId(orderBean.getProductid());
                productService.UpdateProductNumBean(productNumBean);
                // 生存订单
                flag = orderMapper.addOrder(orderBean);
            }

            if (flag == 1) {
                return ResultUtil.success(ResultCode.UPDATE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultCode.UPDATE_FAIL);
    }

    @GlobalTransactional
    @Override
    public Result editOrder(OrderBean orderBean) {
        try {
            if (orderBean == null ) {
                return ResultUtil.error(ResultCode.UPDATE_FAIL);
            }
            int flag = orderMapper.editOrder(orderBean);
            if (flag == 1) {
                return ResultUtil.success(ResultCode.UPDATE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultCode.UPDATE_FAIL);
    }

    @Override
    public Result editOrderState(List<OrderBean> orderBeanList) {
        try {
            if (orderBeanList == null || orderBeanList.size() <= 0) {
                return ResultUtil.error(ResultCode.UPDATE_FAIL);
            }
            int flag = orderMapper.editOrderState(orderBeanList);
            if (flag == 1) {
                return ResultUtil.success(ResultCode.UPDATE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultCode.UPDATE_FAIL);
    }

    @Override
    public Result cancelOrderById(String id) {
        try {
            if (StringUtils.isEmpty(id)) {
                return ResultUtil.error(ResultCode.UPDATE_FAIL);
            }
            int flag = orderMapper.cancelOrderById(id);
            if (flag == 1) {
                return ResultUtil.success(ResultCode.UPDATE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultCode.UPDATE_FAIL);
    }

    @Override
    public Result getOrderListFor7Days(OrderQueryBean orderQueryBean) {
        try {
            List<OrderBean> orderList = orderMapper.getOrderListFor7Days(orderQueryBean);
            return ResultUtil.success(ResultCode.QUERY_SUCCESS, orderList);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultUtil.error(ResultCode.QUERY_FAIL);
    }
}
