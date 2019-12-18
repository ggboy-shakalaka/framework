package com.ggboy.framework.utils.database;

import java.sql.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DatabaseClient {
    private Connection conn;

    private DatabaseClient(Connection conn) {
        this.conn = conn;
    }

    public Connection getConn() {
        return this.conn;
    }

    public List<Map<String, Object>> query(String sql, Map<String, Object> map) throws SQLException {
        List<Object> objects = action3(action2(sql, map), map);
        return query(objects.remove(0).toString(), objects.toArray());
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

    public int update(String sql, Map<String, Object> map) throws SQLException {
        List<Object> objs = action3(action2(sql, map), map);
        return update(objs.remove(0).toString(), objs.toArray());
    }

    public int update(String sql, Object... objs) throws SQLException {
        normalUpdate: {
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

    // todo rename
    static String action2(String sql, Map<String, Object> map) throws SQLException {
        for (String content : new HashSet<>(SqlParser.find0(sql))) {
            String key = SqlParser.getKey(content);
            if (map == null || !map.containsKey(key))
                throw new SQLException(String.format("unknown param: '%s'", content));
            sql = sql.replaceAll(content, map.getOrDefault(key, "").toString());
        }
        return sql;
    }

    // todo rename
    private static List<Object> action3(String sql, Map<String, Object> map) throws SQLException {
        LinkedList<Object> list = new LinkedList<>();
        for (String content : SqlParser.find1(sql)) {
            String key = SqlParser.getKey(content);
            if (map == null || !map.containsKey(key))
                throw new SQLException(String.format("unknown param: '%s'", content));
            list.add(map.get(key));
        }
        list.addFirst(sql.replaceAll("[#]\\{[^}]*}", "?"));
        return list;
    }

    private static Map<String, Object> getLine(ResultSet rs) throws SQLException {
        var md = rs.getMetaData();

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
        var md = rs.getMetaData();

        var keys = new LinkedList<String>();
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

    public final static DatabaseClient buildMysqlClient(String url, int port, String dbName, String name, String pwd) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String jdbcUrl = "jdbc:mysql://" + url + ":" + port + "/" + dbName + "?useUnicode=true&characterEncoding=UTF8&useSSL=false";
            return buildClient(jdbcUrl, name, pwd);
        } catch (Exception e) {
            throw new RuntimeException("client to mysql error", e);
        }
    }

    public final static DatabaseClient buildSqlServerClient(String url, int port, String dbName, String name, String pwd) {
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

class SqlParser {
    private final static String regex0 = "[$]\\{[^}]*}";
    private final static String regex1 = "[#]\\{[^}]*}";

    public final static String getKey(String str) {
        if (!str.matches("^[#|$]\\{[^}]*}$"))
            return str;
        return str.substring(2, str.length() - 1);
    }

    /**
     * ${***}
     */
    public final static List<String> find0(String str) {
        return doFind(regex0, str);
    }

    /**
     * #{***}
     */
    public final static List<String> find1(String str) {
        return doFind(regex1, str);
    }

    public final static List<String> doFind(String regex, String str) {
        Matcher matcher = Pattern.compile(regex).matcher(str);
        List<String> list = new LinkedList<>();
        while (matcher.find())
            list.add(matcher.group());
        return list;
    }
}