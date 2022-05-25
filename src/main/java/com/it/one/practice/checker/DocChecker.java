package com.it.one.practice.checker;

import com.it.one.practice.config.DocConfig;
import com.it.one.practice.docs.Doc;

import java.io.IOException;

/**
 * Класс отвечающий за проверку всего документа.
 */

public class DocChecker {

    private final Doc document;
    private final DocConfig config;

    public DocChecker(Doc doc, DocConfig config){
        this.document = doc;
        this.config = config;
    }

    public DocPageChecker openPage(int pageIndex) throws IOException {
        return new DocPageChecker(this.document, this.config, pageIndex);
    }

}
