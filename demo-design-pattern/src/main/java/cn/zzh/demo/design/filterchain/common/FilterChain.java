package cn.zzh.demo.design.filterchain.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class FilterChain<T> implements Filter<T> {

    private static final Logger LOG = LoggerFactory.getLogger(FilterChain.class);
    private List<Filter> filters = new ArrayList<Filter>();

    public FilterChain addFilter(Filter filter) {
        filters.add(filter);
        return this;
    }

    public boolean doFilter(T t) {

        for (Filter filter : filters) {
            if (!filter.doFilter(t)) {
                LOG.warn("filter[{}] block", filter.getClass().getSimpleName());
                return false;
            }
        }
        return true;
    }
}
