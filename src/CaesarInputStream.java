import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CaesarInputStream extends FilterInputStream {
    protected final int cipherKey;
    private final boolean encrypt;

    protected CaesarInputStream(InputStream in, int cipherKey, boolean encrypt) {
        super(in);
        this.cipherKey = cipherKey;
        this.encrypt = encrypt;
    }

    @Override
    public int read() throws IOException {
        int ch = in.read();

        if (ch == -1) {
            return -1;
        } else {
            return encrypt ? ch + cipherKey : ch - cipherKey;
        }
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int read = super.read(b, off, len);
        if (read == -1) {
            return -1;
        } else {
            for (int i = 0; i < b.length; i++) {
                b[i] += encrypt ? cipherKey : -cipherKey;
            }
            return read;
        }
    }

}
