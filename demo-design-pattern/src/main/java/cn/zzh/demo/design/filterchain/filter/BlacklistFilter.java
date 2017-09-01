package cn.zzh.demo.design.filterchain.filter;

import cn.zzh.demo.design.filterchain.Session;
import cn.zzh.demo.design.filterchain.common.Filter;

/**
 * 黑名单
 */
public class BlacklistFilter implements Filter<Session> {

    public boolean doFilter(Session session) {
        Long uid = session.getUid();
        if(uid!=null && uid == 123){
            return false;
        }
        return true;
    }
}
