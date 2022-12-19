import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Stream;

public class LaboratoryWork5 {

    protected static final int cipherKey = 3;

    public static void main(String[] args) throws IOException {
        Path lines = (Path.of("lines.txt"));
        System.out.println("The longest line from file " + lines + " is " + getLongestLineFromFile(lines));

        Path decrypted = Path.of("decrypted.txt");
        Path encrypted = Path.of("encrypted.txt");
        encryptFile(decrypted, encrypted);
        decryptFile(encrypted, decrypted);

        String website = "https://kpi.ua/";
        System.out.println("Count HTML tags on " + website);
        Map<String, Integer> tags = countTags(website);
        System.out.println("Tags sorted alphabetically");
        tags.keySet().stream().sorted(String::compareTo).forEach(key -> System.out.println(key + "  " + tags.get(key)));
        System.out.println("\nTags sorted by value");
        tags.keySet().stream().sorted(Comparator.comparingInt(tags::get)).forEach(key -> System.out.println(key + "  " + tags.get(key)));
    }

    public static String getLongestLineFromFile(Path ph) throws IOException {
        try (Stream<String> stream = Files.lines(ph)) {
            return stream.reduce("", (longest, line) -> line.length() > longest.length() ? line : longest);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public static void decryptFile(Path in, Path out) throws IOException {
        FileInputStream fis = new FileInputStream(String.valueOf(in));
        CaesarInputStream eis = new CaesarInputStream(fis, cipherKey, false);

        FileOutputStream fos = new FileOutputStream(String.valueOf(out));

        eis.transferTo(fos);
        eis.close();
    }

    public static void encryptFile(Path in, Path out) throws IOException {
        FileInputStream fis = new FileInputStream(String.valueOf(in));
        CaesarInputStream eis = new CaesarInputStream(fis, cipherKey, true);

        FileOutputStream fos = new FileOutputStream(String.valueOf(out));

        eis.transferTo(fos);
        eis.close();
    }

    public static Map<String, Integer> countTags(String url) throws IOException {
        InputStream input = new URL(url).openStream();
        HtmlTagCounterInputStream counter = new HtmlTagCounterInputStream(input);
        while (counter.read() != -1) {
        }
        return counter.getTagMap();
    }
}