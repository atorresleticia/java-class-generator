package com.ws.calendar.calendartype;


import com.ws.commons.server.pagination.PaginationSearch;

/**
 * @author Leticia Torres {@literal <leticia.bertoldo@wssim.com.br>}
 * @since 6.1.0 2018-03-27
 */

public class CalendarTypeSearch extends PaginationSearch {

    private static final long serialVersionUID = 1L;

    private String generalSearch;
    private Boolean[] active;
    private String color;
    private Boolean standard;

    /**
     *
     * @return generalSearch as {@link String}
     */
    public String getGeneralSearch() {
        return generalSearch;
    }

    /**
     *
     * @param generalSearch as {@link String}
     */
    public void setGeneralSearch(String generalSearch) {
        this.generalSearch = generalSearch;
    }

    /**
     *
     * @return active as {@link Boolean[]}
     */
    public Boolean[] getActive() {
        return active;
    }

    /**
     *
     * @param active as {@link Boolean[]}
     */
    public void setActive(Boolean[] active) {
        this.active = active;
    }

    /**
     *
     * @return color as {@link String}
     */
    public String getColor() {
        return color;
    }

    /**
     *
     * @param color as {@link String}
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     *
     * @return standard as {@link Boolean}
     */
    public Boolean getStandard() {
        return standard;
    }

    /**
     *
     * @param standard as {@link Boolean}
     */
    public void setStandard(Boolean standard) {
        this.standard = standard;
    }
}