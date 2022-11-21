package com.sicnu.yolo.controller;

import com.sicnu.yolo.annotation.LoginRequired;
import com.sicnu.yolo.entity.DiscussPost;
import com.sicnu.yolo.entity.User;
import com.sicnu.yolo.entity.dto.QueryPageBean;
import com.sicnu.yolo.entity.vo.DiscussPostAndUser;
import com.sicnu.yolo.entity.vo.PageResult;
import com.sicnu.yolo.entity.vo.Result;
import com.sicnu.yolo.service.DiscussPostServicee;
import com.sicnu.yolo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @className: HomeController
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/1 20:30
 */
@RestController
@CrossOrigin
public class HomeController {
    @Autowired
    private DiscussPostServicee discussPostServicee;

    @GetMapping("/index")
    public Result index(Integer currentPage, Integer pageSize, String q){
        System.out.println(currentPage);
        System.out.println(pageSize);
        System.out.println(q);
        QueryPageBean queryPageBean = new QueryPageBean(currentPage, pageSize, q);
        PageResult result= discussPostServicee.findPageByUserId(queryPageBean);
        return Result.SUCCESS().setData(result);
    }
}
