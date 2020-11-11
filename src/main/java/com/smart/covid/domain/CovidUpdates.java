package com.smart.covid.domain;


import javax.persistence.*;

import java.io.Serializable;

/**
 * A CovidUpdates.
 */
@Entity
@Table(name = "covid_updates")
public class CovidUpdates implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "source")
    private String source;

    @Column(name = "domain")
    private String domain;

    @Column(name = "image")
    private String image;

    @Column(name = "published_at")
    private String publishedAt;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public CovidUpdates title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public CovidUpdates content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public CovidUpdates source(String source) {
        this.source = source;
        return this;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDomain() {
        return domain;
    }

    public CovidUpdates domain(String domain) {
        this.domain = domain;
        return this;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getImage() {
        return image;
    }

    public CovidUpdates image(String image) {
        this.image = image;
        return this;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public CovidUpdates publishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
        return this;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CovidUpdates)) {
            return false;
        }
        return id != null && id.equals(((CovidUpdates) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CovidUpdates{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", source='" + getSource() + "'" +
            ", domain='" + getDomain() + "'" +
            ", image='" + getImage() + "'" +
            ", publishedAt='" + getPublishedAt() + "'" +
            "}";
    }
}
