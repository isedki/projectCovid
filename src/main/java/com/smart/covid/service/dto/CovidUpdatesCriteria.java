package com.smart.covid.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.smart.covid.domain.CovidUpdates} entity. This class is used
 * in {@link com.smart.covid.web.rest.CovidUpdatesResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /covid-updates?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CovidUpdatesCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private StringFilter content;

    private StringFilter source;

    private StringFilter domain;

    private StringFilter image;

    private StringFilter publishedAt;

    public CovidUpdatesCriteria() {
    }

    public CovidUpdatesCriteria(CovidUpdatesCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.content = other.content == null ? null : other.content.copy();
        this.source = other.source == null ? null : other.source.copy();
        this.domain = other.domain == null ? null : other.domain.copy();
        this.image = other.image == null ? null : other.image.copy();
        this.publishedAt = other.publishedAt == null ? null : other.publishedAt.copy();
    }

    @Override
    public CovidUpdatesCriteria copy() {
        return new CovidUpdatesCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTitle() {
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getContent() {
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
    }

    public StringFilter getSource() {
        return source;
    }

    public void setSource(StringFilter source) {
        this.source = source;
    }

    public StringFilter getDomain() {
        return domain;
    }

    public void setDomain(StringFilter domain) {
        this.domain = domain;
    }

    public StringFilter getImage() {
        return image;
    }

    public void setImage(StringFilter image) {
        this.image = image;
    }

    public StringFilter getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(StringFilter publishedAt) {
        this.publishedAt = publishedAt;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CovidUpdatesCriteria that = (CovidUpdatesCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(content, that.content) &&
            Objects.equals(source, that.source) &&
            Objects.equals(domain, that.domain) &&
            Objects.equals(image, that.image) &&
            Objects.equals(publishedAt, that.publishedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        content,
        source,
        domain,
        image,
        publishedAt
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CovidUpdatesCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (content != null ? "content=" + content + ", " : "") +
                (source != null ? "source=" + source + ", " : "") +
                (domain != null ? "domain=" + domain + ", " : "") +
                (image != null ? "image=" + image + ", " : "") +
                (publishedAt != null ? "publishedAt=" + publishedAt + ", " : "") +
            "}";
    }

}
