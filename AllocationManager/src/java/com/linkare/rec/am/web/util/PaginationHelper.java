
package com.linkare.rec.am.web.util;

import javax.faces.model.DataModel;

public abstract class PaginationHelper {

    private int pageSize;
    private int page;

    public PaginationHelper(int pageSize) {
        this.pageSize = pageSize;
    }

    public abstract int getItemsCount();

    public abstract DataModel createPageDataModel();

    public final int getPageFirstItem() {
        return page*pageSize;
    }

    public final int getPageLastItem() {
        int i = getPageFirstItem() + pageSize -1;
        int count = getItemsCount() - 1;
        if (i > count) {
            i = count;
        }
        if (i < 0) {
            i = 0;
        }
        return i;
    }

    public final boolean isHasNextPage() {
        return (page+1)*pageSize+1 <= getItemsCount();
    }

    public final void nextPage() {
        if (isHasNextPage()) {
            page++;
        }
    }

    public final boolean isHasPreviousPage() {
        return page > 0;
    }

    public final void previousPage() {
        if (isHasPreviousPage()) {
            page--;
        }
    }

    public final int getPageSize() {
        return pageSize;
    }

}
