package com.cropster.capsulecrm.client;

import java.util.ArrayList;
import java.util.Collection;

import com.cropster.capsulecrm.client.model.PageLinks;

/**
 * @author Casey Link
 * created on 2017-09-20
 */
public class CArray<T> extends ArrayList<T>
{
    private final PageLinks links;

    public CArray(final Collection<? extends T> c, PageLinks links)
    {
        super(c);
        this.links = links;
    }

    public boolean hasNextPage()
    {
        return links.isHasMore();
    }

    public String getLink()
    {
        return links.getNext();
    }
}
