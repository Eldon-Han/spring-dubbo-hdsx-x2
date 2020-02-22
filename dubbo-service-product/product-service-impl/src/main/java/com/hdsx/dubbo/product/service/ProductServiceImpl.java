package com.hdsx.dubbo.product.service;

import com.hdsx.dubbo.common.framework.constant.DeletedStatusEnum;
import com.hdsx.dubbo.common.framework.util.CollectionUtil;
import com.hdsx.dubbo.common.framework.util.ServiceExceptionUtil;
import com.hdsx.dubbo.common.framework.util.StringUtil;
import com.hdsx.dubbo.common.framework.vo.Result;
import com.hdsx.dubbo.common.framework.vo.ResultCode;
import com.hdsx.dubbo.common.framework.vo.ResultUtil;
import com.hdsx.dubbo.product.api.ProductService;
import com.hdsx.dubbo.product.api.bean.ProductBean;
import com.hdsx.dubbo.product.api.bean.ProductNumBean;
import com.hdsx.dubbo.product.api.constant.ProductCategoryConstants;
import com.hdsx.dubbo.product.api.constant.ProductErrorCodeEnum;
import com.hdsx.dubbo.product.api.constant.ProductSpuConstants;
import com.hdsx.dubbo.product.dao.ProductMapper;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
// 实际上不用添加。添加的原因是，必须 Spring 报错提示
@org.apache.dubbo.config.annotation.Service(validation = "true", version = "${dubbo.provider.ProductService.version}")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    @Override
    public Result InsertOrUpdateProduct(ProductBean productBean) {
        try {
            if (productBean == null ) {
                return ResultUtil.error(ResultCode.UPDATE_FAIL);
            }
            // 获取基本信息

            // 获取基本信息的ID
            String id = productBean.getId();
            // 判断库里之前有没有,update或者insert
            ProductBean productById = productMapper.getProductById(id);
            if (productById != null) {
                productMapper.editProduct(productBean);
            } else {
                productMapper.addProduct(productBean);
            }

            return ResultUtil.success(ResultCode.UPDATE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultCode.UPDATE_FAIL);
    }

    @Override
    public Result UpdateProductNumBean(ProductNumBean productNumBean) {
        try {
            if (productNumBean == null ) {
                return ResultUtil.error(ResultCode.UPDATE_FAIL);
            }

            // 获取基本信息的ID
            String id = productNumBean.getId();
            // 判断库里之前有没有,update或者insert
            ProductBean productById = productMapper.getProductById(id);
            if (productById != null) {
                // 原来数据库的 数量 - 这次订单的数量
                int num = productById.getNum() - productNumBean.getNum() ;
                productNumBean.setNum(num);
                productMapper.UpdateProductNumBean(productNumBean);
                return ResultUtil.success(ResultCode.UPDATE_SUCCESS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.error(ResultCode.UPDATE_FAIL);
    }

    @Override
    public Result getProductById(String id) {
        try {
            ProductBean product = productMapper.getProductById(id);
            return ResultUtil.success(ResultCode.QUERY_SUCCESS, product);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResultUtil.error(ResultCode.QUERY_FAIL);
    }
}