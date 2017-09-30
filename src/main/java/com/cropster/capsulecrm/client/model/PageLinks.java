package com.cropster.capsulecrm.client.model;

import org.apache.commons.lang3.StringUtils;

import lombok.Getter;
import okhttp3.Headers;

/**
 * Page link class to be used to determine the links to other pages of request
 * responses encoded in the current response. These will be present if the
 * result set size exceeds the per page limit.
 *
 * @author Casey Link
 * created on 2017-01-18
 */
@Getter
public class PageLinks
{

    static final String HEADER_PAGE_HAS_MORE = "X-Pagination-Has-More";
    static final String HEADER_PAGE_LINK = "Link";

    private static final String DELIM_LINKS = ", ";

    private static final String DELIM_LINK_PARAM = ";";
    private static final String META_REL = "rel";
    private static final String META_NEXT = "next";
    private static final String META_PREV = "prev";

    private String next;
    private String prev;
    private boolean hasMore = false;

    /**
     * Parse links from executed method
     *
     * @param response
     */
    public PageLinks(Headers headers)
    {
        String hasMoreHeader = StringUtils.trim(headers.get(HEADER_PAGE_HAS_MORE));
        if ("true".equals(hasMoreHeader))
            hasMore = true;
        String linkHeader = headers.get(HEADER_PAGE_LINK);
        if (linkHeader != null)
        {
            String[] links = linkHeader.split(DELIM_LINKS);
            for (String link : links)
            {
                String[] segments = link.split(DELIM_LINK_PARAM);
                if (segments.length < 2)
                    continue;

                String linkPart = segments[0].trim();
                if (!linkPart.startsWith("<") || !linkPart
                        .endsWith(">"))
                    continue;
                linkPart = linkPart.substring(1, linkPart.length() - 1);

                for (int i = 1; i < segments.length; i++)
                {
                    String[] rel = segments[i].trim().split("=");
                    if (rel.length < 2 || !META_REL.equals(rel[0]))
                        continue;

                    String relValue = rel[1];
                    if (relValue.startsWith("\"") && relValue
                            .endsWith("\""))
                        relValue = relValue.substring(1, relValue.length() - 1);

                    if (META_NEXT.equals(relValue))
                        next = linkPart;
                    else if (META_PREV.equals(relValue))
                        prev = linkPart;
                }
            }
        } else
        {
            next = null;
            prev = null;
        }
    }
}
