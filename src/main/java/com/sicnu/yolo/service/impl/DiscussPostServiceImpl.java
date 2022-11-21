package com.sicnu.yolo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sicnu.yolo.constant.YoloConstant;
import com.sicnu.yolo.entity.DiscussPost;
import com.sicnu.yolo.entity.EsEvent;
import com.sicnu.yolo.entity.User;
import com.sicnu.yolo.entity.dto.QueryPageBean;
import com.sicnu.yolo.entity.exception.MyException;
import com.sicnu.yolo.entity.vo.DiscussPostAndUser;
import com.sicnu.yolo.entity.vo.PageResult;
import com.sicnu.yolo.event.EventProducer;
import com.sicnu.yolo.mapper.DiscussPostMapper;
import com.sicnu.yolo.service.DiscussPostServicee;
import com.sicnu.yolo.service.UserService;
import com.sicnu.yolo.utils.SensitiveUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @className: DiscussPostServiceImpl
 * @description: 帖子具体实现服务
 * @author: 热爱生活の李
 * @since: 2022/7/1 19:26
 */
@Service
@Transactional
public class DiscussPostServiceImpl extends ServiceImpl<DiscussPostMapper, DiscussPost> implements DiscussPostServicee {
    @Autowired
    private DiscussPostMapper mapper;
    @Autowired
    private UserService userService;
    @Autowired
    private SensitiveUtils sensitiveUtils;
    @Autowired
    private EventProducer eventProducer;
    @Override
    public PageResult findPageByUserId(QueryPageBean queryPageBean) {
        Integer currentPage = queryPageBean.getCurrentPage();
        Integer pageSize = queryPageBean.getPageSize();
        String queryString = queryPageBean.getQueryString();
        currentPage = currentPage == null ? 1 :currentPage;
        pageSize = pageSize == null ? 10 : pageSize;
        PageHelper.startPage(currentPage,pageSize);
        Page<DiscussPost> page = mapper.findPageByUserId(queryString);
        List<DiscussPost> discussPosts = page.getResult();
        List<Integer> userIds = new ArrayList<>();
        discussPosts.forEach(discussPost -> userIds.add(discussPost.getUserId()));
        List<Integer> distinctUserIns = userIds.stream().distinct().sorted().collect(Collectors.toList());
        List<DiscussPostAndUser> list = new ArrayList<>();
        List<User> users = userService.listByIds(distinctUserIns);
        discussPosts.forEach(discussPost -> {
            Optional<User> optional = users.stream().filter(user -> user.getId().intValue() == discussPost.getUserId().intValue()).findFirst();
            User user = optional.get();
            list.add(new DiscussPostAndUser(discussPost,user));
        });

        return new PageResult(page.getTotal(),list);
    }

    @Override
    public void addDiscussPost(DiscussPost post) {
        if(post == null) throw new MyException("参数不能为空");
        // 去掉那种html标签
        post.setTitle(HtmlUtils.htmlEscape(post.getTitle()));
        post.setContent(HtmlUtils.htmlEscape(post.getContent()));
        //  过滤敏感词
        post.setTitle(sensitiveUtils.filter(post.getTitle()));
        post.setContent(sensitiveUtils.filter(post.getContent()));
        mapper.insert(post);
        EsEvent esEvent = new EsEvent()
                .setTopic(YoloConstant.TOPIC_ES_DISCUSSPOST)
                        .setDiscussPost(post);
        eventProducer.fireEvent(esEvent);
    }

    @Override
    public DiscussPostAndUser selectDiscussPostById(Integer discussPostId) {
        DiscussPost discussPost = mapper.findDiscussPostById(discussPostId);
        if(discussPost == null) throw new MyException("不存在该帖子或被拉黑");
        User user = userService.getOne(new QueryWrapper<User>().eq("id", discussPost.getUserId()).eq("status", 1));
        if(user == null) throw new MyException("该用户未激活或不存在");
        DiscussPostAndUser discussPostAndUser = new DiscussPostAndUser(discussPost, user);
        return discussPostAndUser;
    }

    @Override
    public void updateCommentCountById(Integer discussPostId, int num) {
        if(num == 0) return;
        mapper.updateCommentCountById(discussPostId,num);
        return;
    }
}
