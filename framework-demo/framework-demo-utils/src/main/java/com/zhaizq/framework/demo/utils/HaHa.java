package com.zhaizq.framework.demo.utils;

import com.zhaizq.framework.utils.httpclient.StringSimpleHttp;

public class HaHa {
    public static void main(String[] args) throws Exception {
//        ScriptEngineManager manager = new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByName("JavaScript");
//        StringBuffer script = new StringBuffer();
//        script.append("var obj = new Object();");
//        script.append("obj.hello = function(name){print('hello, '+name);}");
//        //执行这段script脚本
//        engine.eval(script.toString());
//        // javax.script.Invocable 是一个可选的接口
//        // 检查你的script engine 接口是否已实现!
//        // 注意：JavaScript engine实现了Invocable接口
//        Invocable inv = (Invocable) engine;
//        // 获取我们想调用那个方法所属的js对象
//        Object obj = engine.get("obj");
//        // 执行obj对象的名为hello的方法
//        inv.invokeMethod(obj, "hello", "Script Method !!" );

//        String s = StringSimpleHttp.startDefaultRequest("http://wms.dongdamen.net").doPost(StringSimpleHttp.buildJsonEntity("{}"));
        String s = StringSimpleHttp.startDefaultRequest("http://wms.dongdamen.net").doGet();
        System.out.println(s);
    }
}