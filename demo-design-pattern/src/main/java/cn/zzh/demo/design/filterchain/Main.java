package cn.zzh.demo.design.filterchain;

import cn.zzh.demo.design.filterchain.common.FilterChain;
import cn.zzh.demo.design.filterchain.filter.BlacklistFilter;
import cn.zzh.demo.design.filterchain.filter.HostFilter;
import cn.zzh.demo.design.filterchain.filter.VerfiyFilter;
import com.alibaba.fastjson.JSON;

/**
 * 需求：用户登录的时候
 * <p>
 * 1.需要判断黑名单<br>
 * 2.ip请求限制<br>
 * 3.记录uid的session，<br>
 */
public class Main {

    public static void main(String[] args) {

        Session session = new Session();
        session.setSid("1231");
        session.setIp("127.0.0.11");

        FilterChain filterChain = new FilterChain();
        filterChain.addFilter(new VerfiyFilter())
                .addFilter(new HostFilter())
                .addFilter(new BlacklistFilter());

        boolean ret = filterChain.doFilter(session);

        System.out.printf("ret:%s, session:%s", ret, JSON.toJSONString(session));
    }
}
