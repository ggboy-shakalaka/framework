package demo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ggboy.framework.utils.database.DatabaseClient;

import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class A {
    public static void main(String[] args) throws IOException, SQLException {
        DatabaseClient databaseClient = DatabaseClient.Builder.buildMysqlClient(
                "123.13.225.42",
                12306,
                "wms",
                "sqwms",
                "PE-WMS%Rdwms"
        );

        List<Map<String, Object>> query = databaseClient.query("SELECT * from base_suite_source_goods where suite_code like '600%';");

        List<String> codeList = new LinkedList<>();
        for (Map<String, Object> stringObjectMap : query) {
            String goods_source_code = stringObjectMap.get("goods_source_code").toString();
            JSONArray jsonArray = JSON.parseArray(goods_source_code);
            for (Object o : jsonArray) {
                String code = ((JSONObject) o).getString("code");
                codeList.add(code);
            }
        }

        codeList.forEach(System.out::println);
    }
}
