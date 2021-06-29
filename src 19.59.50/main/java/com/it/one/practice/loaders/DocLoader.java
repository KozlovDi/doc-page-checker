package com.it.one.practice.loaders;

import com.it.one.practice.docs.Doc;

import java.io.File;

public interface DocLoader {
    Doc load(File file);
}
