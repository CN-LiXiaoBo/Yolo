package com.sicnu.yolo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * @className: DiscussPost
 * @description: 帖子实体
 * @author: 热爱生活の李
 * @since: 2022/7/1 19:05
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "discusspost",shards = 6,replicas = 3)
public class DiscussPost {
    @Id
    @TableId(type = IdType.AUTO)
    private Integer id;
    @Field(type= FieldType.Integer)
    private Integer userId;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String title;
    @Field(type = FieldType.Text,analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String content;
    @Field(type= FieldType.Integer)
    private Integer type;
    @Field(type= FieldType.Integer)
    private Integer status;
    @TableField(fill = FieldFill.INSERT)
    @Field(type= FieldType.Date,format = DateFormat.basic_date)
    private Date createTime;
    @Field(type= FieldType.Integer)
    private Integer commentCount;
    @Field(type= FieldType.Double)
    private double score;
}
