package com.example.job.util;

import com.example.job.entity.WaterNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: ymfa
 * @Date: 2020/2/16 13:01
 * @Description:
 */
public class ExportExcel {
    static Map<String, WaterNode> waterMap = new HashMap<>();
    public static void main(String[] args) throws Exception{
        int pageNum =333;
        for (int i =1;i <=pageNum;i++){

            getRowData(i,"xld");
            getRowData(i,"xxy");

        }
        int i =0;
        System.out.println();
        //插入excel中
        List<List<String>> lists = new ArrayList<>();
        for (String key: waterMap.keySet()){
            List<String> list = new ArrayList<>();
            String[] s = key.split(" ");
            list.add(s[0]);
            list.add(s[1]);
            WaterNode node = waterMap.get(key);
            list.add(node.getXld());
            list.add(node.getXxy());
            lists.add(list);
            //if (lists.size() > 5000){
            //    break;
            //}
        }
        ExcelUtil.insertLine(lists);

    }

    private static void getRowData(int page,String name) throws IOException {
        File input = new File("src/main/resources/water/"+name+page+".html");
        Document doc = Jsoup.parse(input, "UTF-8");
        Elements tables = doc.select("table.jrsq tr");
        Map<Integer,String> map = new HashMap<>();
        map.put(1,"时间");
        map.put(2,"库水位");
        map.put(3,"蓄水量");
        map.put(4,"坝下水位");
        map.put(5,"昨日入库流量");
        map.put(6,"昨日出库流量");
        map.put(7,"昨日弃水流量");
        //组装行数据
        int ceshi = tables.size() > 10 ? 10 :tables.size();
        for (int i =1; i<ceshi; i++) {
            Elements tds = tables.get(i).select("td");
            for (Element td: tds){

            }
             if (tds.size() != 8){
               System.out.println(tables.get(i).html());
               throw new RuntimeException(page+name+"失败"+i);
           }
           for (int j=2; j<8; j++){
               String key = tds.get(1).text() +" "+ map.get(j);
               WaterNode node = waterMap.get(key);
               if (node == null){
                   node = new WaterNode();
               }
               if ("xld".equals(name)){

                   node.setXld(tds.get(j).text());
               }else{
                   node.setXxy(tds.get(j).text());
               }
               waterMap.put(key,node);
           }
           //ExcelUtil.insertLine(list);

       }
    }
}
