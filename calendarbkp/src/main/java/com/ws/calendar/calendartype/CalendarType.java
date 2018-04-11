package com.ws.calendar.calendartype;

import com.ws.commons.persistence.model.BaseEntity;
import com.ws.commons.server.validation.constraints.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Leticia Torres {@literal <leticia.bertoldo@wssim.com.br>}
 * @since 6.1.0 2018-03-27
 */

@Entity
public class CalendarType extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Column(length = 80)
    @Size(max = 80)
    private String description;

    @Column(length = 30)
    @Size(max = 30)
    private String color;

    @NotNull
    private Boolean active;

    @NotNull
    private Boolean standard;

    /**
     *
     * @return description as {@link String}
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description as {@link String}
     */
    public void setDescription(String description) {
        this.description = description;
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
     * @return active as {@link Boolean}
     */
    public Boolean getActive() {
        return active;
    }

    /**
     *
     * @param active as {@link Boolean}
     */
    public void setActive(Boolean active) {
        this.active = active;
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