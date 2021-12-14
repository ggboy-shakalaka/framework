package demo;

import com.zhaizq.framework.utils.common.FileUtil;
import com.zhaizq.framework.utils.jdbc.DatabaseClient;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        List<String> strings = FileUtil.readLines("C:\\Users\\switc\\Desktop\\1234.log");
        DatabaseClient databaseClient = DatabaseClient.Builder.buildMysqlClient("192.168.1.52", 3306, "zhaizq", "root", "Hnqst@123");

        List<String> lines = new LinkedList<>();

        for (String line : strings) {
            if (line.contains("<warehouseCode>CK00001</warehouseCode>")) {
                lines.add(line);
            }
        }

        strings = null;

        System.out.println("订单分析完毕");

        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\switc\\Desktop\\321.txt"));

        bufferedWriter.write("");

        for (String line : lines) {
            String time = line.substring(0, 19);
            String message = line.substring(24);

            bufferedWriter.append(String.format("%s,,,%s", time, message));
            bufferedWriter.append("\n");
        }

        bufferedWriter.flush();
        bufferedWriter.close();
    }
}