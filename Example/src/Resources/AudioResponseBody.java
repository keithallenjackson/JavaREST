package Resources;

import Framework.HttpResponseBody;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * Author: Keith Jackson
 * Class: CSC583
 * Semester: Fall 2015
 * Project:
 * <p>
 * Date: 4/17/2016
 * File:
 * Description:
 */
public class AudioResponseBody extends HttpResponseBody {

    FileInputStream stream;

    public AudioResponseBody(String path) throws IOException {
            String pathFix = Paths.get("").toAbsolutePath().toString() + path;
            stream = new FileInputStream(pathFix);

    }

    @Override
    public InputStream getBodyInputStream() {
        return stream;
    }
}
