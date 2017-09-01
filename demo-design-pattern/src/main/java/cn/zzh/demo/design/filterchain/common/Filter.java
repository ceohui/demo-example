package cn.zzh.demo.design.filterchain.common;

public interface Filter<T> {

    boolean doFilter(T t);

}
