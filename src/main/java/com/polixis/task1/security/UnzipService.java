package com.polixis.task1.security;

import net.lingala.zip4j.ZipFile;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UnzipService {

    public void unzip(String filename, String destinationDirectory)
        throws IOException {
        (new ZipFile(filename)).extractAll(destinationDirectory);
    }
}
