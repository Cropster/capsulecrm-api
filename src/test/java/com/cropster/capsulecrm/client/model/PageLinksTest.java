package com.cropster.capsulecrm.client.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

import okhttp3.Headers;

/**
 * @author Casey Link
 * created on 2017-01-18
 */
public class PageLinksTest
{

    private String firstPageLink = "<https://api.capsulecrm.com/api/v2/parties?page=2&perPage=500&embed=tags,fields&type=organisations>; rel=\"next\"";
    private String middlePageLink = "<https://api.capsulecrm.com/api/v2/parties?page=39&perPage=500&embed=tags,fields&type=organisations>; rel=\"next\", <https://api.capsulecrm.com/api/v2/parties?page=37&perPage=500&embed=tags,fields&type=organisations>; rel=\"prev\"";
    private String lastPageLink = "<https://api.capsulecrm.com/api/v2/parties?page=38&perPage=500&embed=tags,fields>; rel=\"prev\"";

    @Test
    public void testFirstPageLink()
    {

        Headers headers = Headers.of(
                PageLinks.HEADER_PAGE_HAS_MORE, "true",
                PageLinks.HEADER_PAGE_LINK, firstPageLink);

        PageLinks pageLinks = new PageLinks(headers);
        assertTrue(pageLinks.isHasMore());
        assertThat(pageLinks.getNext(),
                equalTo("https://api.capsulecrm.com/api/v2/parties?page=2&perPage=500&embed=tags,fields&type=organisations"));
        assertThat(pageLinks.getPrev(), is(nullValue()));
    }

    @Test
    public void testMiddlePageLink()
    {
        Headers headers = Headers.of(
                PageLinks.HEADER_PAGE_HAS_MORE, "true",
                PageLinks.HEADER_PAGE_LINK, middlePageLink);

        PageLinks pageLinks = new PageLinks(headers);

        assertTrue(pageLinks.isHasMore());
        assertThat(pageLinks.getNext(),
                equalTo("https://api.capsulecrm.com/api/v2/parties?page=39&perPage=500&embed=tags,fields&type=organisations"));
        assertThat(pageLinks.getPrev(),
                equalTo("https://api.capsulecrm.com/api/v2/parties?page=37&perPage=500&embed=tags,fields&type=organisations"));
    }

    @Test
    public void testLastPageLink()
    {
        Headers headers = Headers.of(
                PageLinks.HEADER_PAGE_HAS_MORE, "false",
                PageLinks.HEADER_PAGE_LINK, lastPageLink);

        PageLinks pageLinks = new PageLinks(headers);

        assertFalse(pageLinks.isHasMore());
        assertThat(pageLinks.getNext(), is(nullValue()));
        assertThat(pageLinks.getPrev(),
                equalTo("https://api.capsulecrm.com/api/v2/parties?page=38&perPage=500&embed=tags,fields"));
    }
}
