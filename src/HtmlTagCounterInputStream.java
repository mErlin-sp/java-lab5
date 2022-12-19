import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class HtmlTagCounterInputStream extends FilterInputStream {

    private final Map<String, Integer> tagMap = new HashMap<>();

    public Map<String, Integer> getTagMap() {
        return tagMap;
    }

    private boolean inTag = false;
    private String tag = "";

    public HtmlTagCounterInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read(byte[] buf, int from, int len) throws IOException {
        int charCount = super.read(buf, from, len);

        if (charCount == -1) {
            return -1;
        }

        for (byte b : buf) {
            char ch = (char) b;
            if (!this.inTag) {
                if (ch == '<') {
                    this.inTag = true;
                    this.tag = "<";
                }
            } else if (ch == '/') {
                this.inTag = false;
            } else if (ch == '>' || ch == ' ' || ch == '\n') {
                this.inTag = false;
                this.tag += ">";
                tagMap.merge(this.tag, 1, Integer::sum);
            } else {
                this.tag += ch;
            }
        }

        return charCount;
    }

    @Override
    public int read() throws IOException {
        byte[] buf = new byte[1];
        int result = read(buf, 0, 1);
        if (result == -1) {
            return -1;
        } else {
            return buf[0];
        }
    }
}