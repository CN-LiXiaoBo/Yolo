package com.sicnu.yolo.dao.es;

import com.sicnu.yolo.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @interfaceName: DiscussPostRepository
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/13 14:07
 */
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost,Integer> {
}
