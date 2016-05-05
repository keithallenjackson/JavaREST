package Resources;

import JavaREST.Framework.HttpResponseBody;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

/**
 * Author: Keith Jackson
 * Date: 4/18/2016
 * License: MIT
 *
 */


public class AudioResponseBody extends HttpResponseBody {

    private FileInputStream stream;

    public AudioResponseBody(String path) throws IOException {
            String pathFix = Paths.get("").toAbsolutePath().toString() + path;
            stream = new FileInputStream(pathFix);

    }

    @Override
    public InputStream getBodyInputStream() {
        return stream;
    }
}
