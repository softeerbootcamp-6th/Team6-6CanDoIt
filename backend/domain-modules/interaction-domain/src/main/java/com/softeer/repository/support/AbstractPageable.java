package com.softeer.repository.support;

public abstract class AbstractPageable implements Where, OrderBy{

    protected static final int DEFAULT_PAGE_SIZE = 10;
    protected static final long DEFAULT_LAST_ID = Long.MAX_VALUE;

    protected final Integer pageSize;
    protected final Long lastId;

    public AbstractPageable(int pageSize, Long lastId) {
        this.pageSize = pageSize;
        this.lastId = lastId;
    }

    public int pageSize() {
        return pageSize;
    }
}
