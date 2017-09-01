package cn.zzh.demo.design.filterchain.filter;

import cn.zzh.demo.design.filterchain.Session;
import cn.zzh.demo.design.filterchain.common.Filter;

public class HostFilter implements Filter<Session> {


    public boolean doFilter(Session session) {
        String ip = session.getIp();
        if (ip != null && ip.equals("127.0.0.1")) {
            return false;
        }
        return true;
    }
}
