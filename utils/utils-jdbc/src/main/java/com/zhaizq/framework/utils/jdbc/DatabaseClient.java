package com.zhaizq.framework.utils.jdbc;

import com.zhaizq.framework.utils.common.StringUtil;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class DatabaseClient {
    private Connection conn;

    private DatabaseClient(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return this.conn;
    }

    public List<Map<String, Object>> query(String sql, Map<String, Object> map) throws SQLException {
        sql = SqlParser.replace(sql, map);
        List<Object> objs = SqlParser.placeholder(sql, map);
        return query(objs.remove(0).toString(), objs.toArray());
    }

    public List<Map<String, Object>> query(String sql, Object... objs) throws SQLException {
        if (objs == null || objs.length == 0)
            try (Statement statement = conn.createStatement()) {
                return getLines(statement.executeQuery(sql));
            }

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (int i = 0; i < objs.length; ++i)
                statement.setObject(i + 1, objs[i]);
            return getLines(statement.executeQuery());
        }
    }

    public void insert(String table, Map<String, Object> map) throws SQLException {
        insert(table, Collections.singletonList(map));
    }

    // insert into 'table' () values ();
    public void insert(String table, List<Map<String, Object>> mapList) throws SQLException {
        conn.setAutoCommit(false);
        try {
            String sql = "INSERT INTO %s (%s) VALUES (%s)";
            for (Map<String, Object> map : mapList) {
                String columns = StringUtil.toString(", ", map.keySet().toArray());
                String values = StringUtil.toString(", ", map.keySet().stream().map(v -> "#{" + v + "}").toArray());
                update(String.format(sql, table, columns, values), map);
            }
            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        }
    }

    // todo insert into 'table' () values ((), ());
    public int insertBatch(String table, List<Map<String, Object>> mapList) throws SQLException {
        Set<String> keySet = new HashSet<>();
        for (Map<String, Object> map : mapList) {
            List<String> key = map.entrySet().stream().filter(e -> e.getValue() != null).map(Map.Entry::getKey).collect(Collectors.toList());
            keySet.addAll(key);
        }
        return update(null);
    }

    public int update(String sql, Map<String, Object> map) throws SQLException {
        sql = SqlParser.replace(sql, map);
        List<Object> objs = SqlParser.placeholder(sql, map);
        return update(objs.remove(0).toString(), objs.toArray());
    }

    public int update(String sql, Object... objs) throws SQLException {
        normalUpdate:
        {
            if (objs != null && objs.length > 0)
                break normalUpdate;
            try (Statement statement = conn.createStatement()) {
                return statement.executeUpdate(sql);
            }
        }

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (int i = 0; i < objs.length; ++i)
                statement.setObject(i + 1, objs[i]);
            return statement.executeUpdate();
        }
    }

    private static Map<String, Object> getLine(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();

        Map<String, Object> line = new HashMap<>();
        for (int i = md.getColumnCount(); i > 0; --i)
            line.put(md.getColumnName(i), rs.getObject(i));
        return line;
    }

    private static List<Map<String, Object>> getLines(ResultSet rs) throws SQLException {
        List<Map<String, Object>> lines = new LinkedList<>();
        while (rs.next())
            lines.add(getLine(rs));
        return lines;
    }

    private static List<String> getColumns(ResultSet rs) throws SQLException {
        ResultSetMetaData md = rs.getMetaData();

        LinkedList<String> keys = new LinkedList<>();
        for (int i = md.getColumnCount(); i > 0; --i)
            keys.addFirst(md.getColumnName(i));
        return keys;
    }

    public void close() {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static class Builder {
        public static DatabaseClient buildMysqlClient(String url, int port, String dbName, String name, String pwd) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String jdbcUrl = "jdbc:mysql://" + url + ":" + port + "/" + dbName + "?useUnicode=true&characterEncoding=UTF8&useSSL=false";
                return buildClient(jdbcUrl, name, pwd);
            } catch (Exception e) {
                throw new RuntimeException("client to mysql error", e);
            }
        }

        public static DatabaseClient buildSqlServerClient(String url, int port, String dbName, String name, String pwd) {
            try {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                String jdbcUrl = "jdbc:sqlserver://" + url + ":" + port + ";DatabaseName=" + dbName;
                return buildClient(jdbcUrl, name, pwd);
            } catch (Exception e) {
                throw new RuntimeException("client to sqlserver error", e);

            }
        }

        private static DatabaseClient buildClient(String jdbcUrl, String name, String pwd) throws SQLException {
            return new DatabaseClient(DriverManager.getConnection(jdbcUrl, name, pwd));
        }
    }

    static class SqlParser {
        public static String getKey(String str) {
            if (!str.matches("^[#|$]\\{[^}]*}$"))
                return str;
            return str.substring(2, str.length() - 1);
        }

        public static List<String> findDollarSign(String str) {
            return doFind("[$]\\{[^}]*}", str);
        }

        public static List<String> findNumberSign(String str) {
            return doFind("[#]\\{[^}]*}", str);
        }

        public static List<String> doFind(String regex, String str) {
            Matcher matcher = Pattern.compile(regex).matcher(str);
            List<String> list = new LinkedList<>();
            while (matcher.find())
                list.add(matcher.group());
            return list;
        }

        public static String replace(String sql, Map<String, Object> map) throws SQLException {
            for (String content : new HashSet<>(SqlParser.findDollarSign(sql))) {
                String key = SqlParser.getKey(content);
                if (map == null || !map.containsKey(key))
                    throw new SQLException(String.format("unknown param: '%s'", content));
                sql = sql.replaceAll(content, map.getOrDefault(key, "").toString());
            }

            return sql;
        }

        public static List<Object> placeholder(String sql, Map<String, Object> map) throws SQLException {
            LinkedList<Object> list = new LinkedList<>();

            for (String content : SqlParser.findNumberSign(sql)) {
                String key = SqlParser.getKey(content);
                Object value = null;
                if (map == null || (value = map.get(key)) == null)
                    throw new SQLException(String.format("unknown param: '%s'", content));
                list.add(value);
            }

            list.addFirst(sql.replaceAll("[#]\\{[^}]*}", "?"));
            return list;
        }
    }
}