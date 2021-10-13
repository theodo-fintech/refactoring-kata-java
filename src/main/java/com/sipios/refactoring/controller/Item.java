package com.sipios.refactoring.controller;

import com.sipios.refactoring.article.Article;

public


// TODO: bad name
class Item {
    private Article article;
    private int nb;

    public Item() {}

    public Item(Article a, int quantity) {
        this.article = a;
        this.nb = quantity;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article a) {
        this.article = a;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }
}

