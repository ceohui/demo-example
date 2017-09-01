package cn.zzh.demo.design.filterchain.filter;

import cn.zzh.demo.design.filterchain.Session;
import cn.zzh.demo.design.filterchain.common.Filter;

public class VerfiyFilter implements Filter<Session> {

    public boolean doFilter(Session session) {
        String sid = session.getSid();
        if (sid != null) {
            session.setUid(Long.valueOf(sid));
            return true;
        }
        return false;
    }
}
