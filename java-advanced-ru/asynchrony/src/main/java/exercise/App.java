package exercise;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;

class App {

    // BEGIN
    public static CompletableFuture<String> unionFiles(String pathSrc1, String pathSrc2, String pathUnion)
            throws ExecutionException,
            InterruptedException {

        CompletableFuture<String> text1 = readFile(pathSrc1);
        CompletableFuture<String> text2 = readFile(pathSrc2);

        text1.get();
        text2.get();

        return text1.thenCombine(text2, (x, y) -> {
            Path path = Paths.get(pathUnion).toAbsolutePath().normalize();
            String combinedText;

            try {
                if (!Files.exists(path)) {
                    Files.createFile(path);
                }
                Files.writeString(path, x, StandardOpenOption.TRUNCATE_EXISTING);
                Files.writeString(path, y, StandardOpenOption.APPEND);
                combinedText = Files.readString(path);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return combinedText;
        });
    }

    public static CompletableFuture<String> readFile(String src) {
        return CompletableFuture.supplyAsync(() -> {
            String result = "";
            try {
                result = Files.readString(Paths.get(src).toAbsolutePath().normalize());
            } catch (IOException e) {
                System.out.println(e);
            }
            return result;
        });
    }


    public static void main(String[] args) throws Exception {
        // BEGIN
        unionFiles("src/main/resources/file1.txt", "src/main/resources/file2.txt", "src/main/resources/result.txt");
        // END
    }
}

