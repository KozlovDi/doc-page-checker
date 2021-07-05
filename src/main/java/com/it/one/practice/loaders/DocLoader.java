package com.it.one.practice.loaders;

import com.it.one.practice.docs.Doc;

import java.io.File;
import java.io.IOException;

public interface DocLoader {
    Doc load(File file) throws IOException;
}
