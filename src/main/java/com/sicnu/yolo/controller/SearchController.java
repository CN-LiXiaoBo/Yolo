package com.sicnu.yolo.controller;

import com.sicnu.yolo.entity.DiscussPost;
import com.sicnu.yolo.entity.vo.Result;
import com.sicnu.yolo.service.es.EsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
/**
 * @className: SearchController
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/13 17:49
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private EsService esService;
    @GetMapping("/{keyWord}/{current}/{pageSize}")
    public Result serch(@PathVariable String keyWord,@PathVariable Integer current,@PathVariable Integer pageSize){
        List<DiscussPost> discussPosts = esService.searchDiscussPost(keyWord, current, pageSize);
        return Result.SUCCESS().setData(discussPosts);
    }
}
