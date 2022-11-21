package com.sicnu.yolo.service.es;

import com.sicnu.yolo.dao.es.DiscussPostRepository;
import com.sicnu.yolo.entity.DiscussPost;
import com.sicnu.yolo.service.DiscussPostServicee;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @className: EsService
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/13 16:22
 */
@Service
public class EsService {
    @Autowired
    private DiscussPostServicee discussPostServicee;
    @Autowired
    private DiscussPostRepository discussPostRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    public void deleteDiscussPost(int id){
        discussPostRepository.deleteById(id);
    }
    public void save(DiscussPost discussPost){
        discussPostRepository.save(discussPost);
    }
    public List<DiscussPost> searchDiscussPost(String keyWord, int current, int pageSize){
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery(keyWord,"title","content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(current-1,pageSize))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<span style='color:red'>").postTags("</span>"),
                        new HighlightBuilder.Field("content").preTags("<span style='color:red'>").postTags("</span>"))
                .build();
        SearchHits<DiscussPost> search = elasticsearchRestTemplate.search(query, DiscussPost.class);
        List<SearchHit<DiscussPost>> searchHits = search.getSearchHits();
        List<DiscussPost> res = new ArrayList<>();
        for (SearchHit<DiscussPost> searchHit : searchHits) {
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();
            searchHit.getContent().setTitle(highlightFields.get("title")==null?searchHit.getContent().getTitle() : highlightFields.get("title").get(0));
            searchHit.getContent().setContent(highlightFields.get("content")==null?searchHit.getContent().getTitle() : highlightFields.get("content").get(0));
            res.add(searchHit.getContent());
        }
        return res;
    }
}
