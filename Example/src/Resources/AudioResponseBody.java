package Resources;

import Framework.HttpResponseBody;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;


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
