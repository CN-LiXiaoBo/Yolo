package com.sicnu.yolo.utils;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

/**
 * @className: Sensitive
 * @description: TODO
 * @author: 热爱生活の李
 * @since: 2022/7/7 18:39
 */
@Component
public class SensitiveUtils {

    //替换符
    private static  String REPLACE = "***";
    // 根节点
    private SensitiveNode rootNode = new SensitiveNode();

    @PostConstruct
    public void init(){
        try (
                InputStream is = this.getClass().getClassLoader().getResourceAsStream("sensitive-words.txt");
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));)
        {
            String keyword= null;
            while ((keyword = reader.readLine())!= null){
                this.addKeyWord(keyword);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addKeyWord(String keyword){
        SensitiveNode temp = rootNode;
        char[] charArray = keyword.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            SensitiveNode sub = temp.getNode(charArray[i]);
            if(sub == null){
                sub = new SensitiveNode();
                temp.add(charArray[i],sub);
            }
            temp = sub;
            if(i == charArray.length - 1){
                temp.setKeyWordEnd(true);
            }
        }
    }
    // 构建树的节点
    private class SensitiveNode{
        private boolean isKeyWordEnd = false;
        private HashMap<Character,SensitiveNode> subNodes= new HashMap<>();

        public boolean isKeyWordEnd() {
            return isKeyWordEnd;
        }

        public void setKeyWordEnd(boolean keyWordEnd) {
            isKeyWordEnd = keyWordEnd;
        }
        public void add(Character c,SensitiveNode node){
            subNodes.put(c,node);
        }
        public SensitiveNode getNode(Character c){
            return subNodes.get(c);
        }
    }
    public String filter(String text){
        // 判断是否为空
        if(StringUtils.isBlank(text)){
            return null;
        }
        // 过滤后的结果
        StringBuilder sb = new StringBuilder();

        char[] chars = text.toCharArray();
        int len = chars.length;

        // 指针 1 指向树的根节点
        SensitiveNode temp = rootNode;
        // 指针 2 指向文本开头 (敏感词开头)
        int begin = 0;
        // 指针 3 指向文本开头 (敏感池结尾)
        int end = 0;

        while (begin < len){
            // end 没有到最后
            if(end < len){
                //如果这个是特殊字符就要跳过
                if(isSymbol(chars[end])){
                    //如果这个字符是新词的开头
                   if(temp == rootNode){
                       begin++;
                       sb.append(chars[end]);
                   }
                   end++;
                   continue;
                }
                // 下级节点
                temp = temp.getNode(chars[end]);
                //说明没有以begin为起始位置的不是敏感
                if(temp == null){
                    sb.append(chars[begin]);
                    begin++;
                    end = begin;
                    temp = rootNode;
                }else if(temp.isKeyWordEnd()){
                    sb.append(REPLACE);
                    end++;
                    begin = end;
                    temp = rootNode;
                }else{
                    end++;
                }
            }else {
                sb.append(chars[begin]);
                end = ++begin;
                temp = rootNode;
            }
        }
        return sb.toString();
    }
    private boolean isSymbol(Character c){
        return !CharUtils.isAsciiAlphanumeric(c) &&  (c > 0x9FFF || c < 0x2E80);
    }
}
