package com.jukusoft.engine2d.core.utils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.spi.FileSystemProvider;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utils for file operations
 * <p>
 * Created by Justin on 24.08.2016.
 */
public class FileUtils {

    //constant strings for exceptions
    private static final String PATH_CANNOT_NULL = "path cannot be null.";
    private static final String PATH_CANNOT_EMPTY = "path cannot be empty.";

    /**
     * private constructor, so other classes cannot create an instance of FileUtils
     */
    protected FileUtils() {
        //
    }

    /**
     * read content from file
     *
     * @param path     path to file
     * @param encoding file encoding
     * @return content of file as string
     * @throws IOException if there are problems with file I/O
     */
    public static String readFile(String path, Charset encoding) throws IOException {
        if (path == null) {
            throw new NullPointerException(PATH_CANNOT_NULL);
        }

        if (path.isEmpty()) {
            throw new IllegalArgumentException(PATH_CANNOT_EMPTY);
        }

        if (!new File(path).exists()) {
            throw new IOException("File doesnt exists: " + path);
        }

        // read bytes from file
        byte[] encoded = Files.readAllBytes(Paths.get(path.replace("/./", "/").replace("\\.\\", "\\")));

        // convert bytes to string with specific encoding and return string
        return new String(encoded, encoding);
    }

    /**
     * read lines from file
     *
     * @param path    path to file
     * @param charset encoding of file
     * @return list of lines from file
     * @throws IOException if there are problems with file I/O
     */
    public static List<String> readLines(String path, Charset charset) throws IOException {
        if (path == null) {
            throw new NullPointerException(PATH_CANNOT_NULL);
        }

        if (path.isEmpty()) {
            throw new IllegalArgumentException(PATH_CANNOT_EMPTY);
        }

        if (!(new File(path)).exists()) {
            throw new FileNotFoundException("Couldnt find file: " + path);
        }

        return Files.readAllLines(Paths.get(path), charset);
    }

    /**
     * write text to file
     *
     * @param path     path to file
     * @param content  content of file
     * @param encoding file encoding
     * @throws IOException if file couldn't written
     */
    public static void writeFile(String path, String content, Charset encoding) throws IOException {
        if (path == null) {
            throw new NullPointerException(PATH_CANNOT_NULL);
        }

        if (path.isEmpty()) {
            throw new IllegalArgumentException(PATH_CANNOT_EMPTY);
        }

        if (content == null) {
            throw new NullPointerException("content cannot be null.");
        }

        if (content.isEmpty()) {
            throw new IllegalArgumentException("content cannot be empty.");
        }

        if (encoding == null) {
            throw new NullPointerException("encoding cannot be null.");
        }

        Files.write(Paths.get(path), content.getBytes(encoding), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void recursiveDeleteDirectory(File f) throws IOException {
        FileUtils.recursiveDeleteDirectory(f, true);
    }

    public static void recursiveDeleteDirectory(File f, boolean deleteDir) throws IOException {
        Objects.requireNonNull(f, "file cannot be null.");

        if (!f.exists()) {
            //we dont have to delete anything
            //Logger.getAnonymousLogger().log(Level.INFO, "Dont need to delete directory, because it doesnt exists: " + f.getAbsolutePath());

            return;
        }

        //check, if it is an directory
        if (f.isDirectory()) {
            for (File c : f.listFiles()) {
                recursiveDeleteDirectory(c);
            }
        }

        //Log.d("FileUtils", "delete directory / file: " + f.getAbsolutePath());

        //delete directory / file
        if (deleteDir) {
            Files.delete(f.toPath());
        }
    }

    public static List<String> listFiles(File dir) throws IOException {
        List<String> relPaths = new ArrayList<>();

        FileUtils.listFiles(dir, (file, relFilePath) -> {
            relPaths.add(relFilePath);
        });

        return relPaths;
    }

    public static void listFiles(File dir, FileListIterator iterator) throws IOException {
        listFiles(dir, dir, iterator);
    }

    protected static void listFiles(File dir, File baseDir, FileListIterator iterator) throws IOException {
        Objects.requireNonNull(dir, "file cannot be null.");

        if (!dir.exists()) {
            throw new FileNotFoundException("directory doesn't exists: " + dir.getAbsolutePath());
        }

        if (!dir.isDirectory()) {
            throw new IllegalArgumentException("dir isn't a directory: " + dir.getAbsolutePath());
        }

        for (File c : dir.listFiles()) {
            if (c.isDirectory()) {
                listFiles(c, baseDir, iterator);
            } else {
                iterator.iterate(c, getRelativeFile(c, baseDir).getPath().replace("\\", "/"));
            }
        }
    }

    /**
     * get path to user.home directory
     *
     * @return path to user.home directory
     */
    public static String getUserHomeDir() {
        return System.getProperty("user.home");
    }

    public static String getAppHomeDir(String appName) {
        return getUserHomeDir() + "/." + appName + "/";
    }

    /**
     * removes ../ from path
     *
     * @param path path where ../ should be replaced
     * @return string without ../ (resolved)
     */
    public static String removeDoubleDotInDir(String path) {
        if (path == null) {
            throw new NullPointerException(PATH_CANNOT_NULL);
        }

        if (path.isEmpty()) {
            throw new IllegalArgumentException(PATH_CANNOT_EMPTY);
        }

        if (path.startsWith("../")) {
            throw new IllegalArgumentException("Cannot relativize paths starting with ../");
        }

        boolean endsWithSlash = path.endsWith("/");

        path = path.replace("\\", "/");

        if (!path.contains("/")) {
            return path;
        }

        String[] array = path.split("/");

        StringBuilder sb = new StringBuilder();

        for (int i = 1; i < array.length; i++) {
            if (array[i].equals("..")) {
                array[i] = null;
                array[i - 1] = null;
            }
        }

        for (String entry : array) {
            if (entry != null) {
                sb.append(entry + "/");
            }
        }

        String res = sb.toString();

        //remove last slash
        if (res.endsWith("/") && !endsWithSlash) {
            return res.substring(0, res.length() - 1);
        }

        return res;
    }

    /**
     * Returns the path of one File relative to another.
     *
     * @param target the target directory
     * @param base   the base directory
     * @return target's path relative to the base directory
     * @throws IOException if an error occurs while resolving the files' canonical names
     * @see <a href="https://stackoverflow.com/questions/204784/how-to-construct-a-relative-path-in-java-from-two-absolute-paths-or-urls">Stackoverflow</a>
     */
    public static File getRelativeFile(File target, File base) throws IOException {
        if (target == null) {
            throw new NullPointerException("target file cannot be null.");
        }

        if (base == null) {
            throw new NullPointerException("base file cannot be null.");
        }

        String[] baseComponents = base.getCanonicalPath().split(Pattern.quote(File.separator));
        String[] targetComponents = target.getCanonicalPath().split(Pattern.quote(File.separator));

        // skip common components
        int index = 0;

        for (; index < targetComponents.length && index < baseComponents.length; ++index) {
            if (!targetComponents[index].equals(baseComponents[index])) {
                break;
            }
        }

        StringBuilder result = new StringBuilder();

        if (index != baseComponents.length) {
            // backtrack to base directory
            for (int i = index; i < baseComponents.length; ++i) {
                result.append(".." + File.separator);
            }
        }

        for (; index < targetComponents.length; ++index) {
            result.append(targetComponents[index] + File.separator);
        }

        String targetPath = target.getPath().replace("\\", "/");

        if (!targetPath.endsWith("/")) {
            // remove final path separator
            result.delete(result.length() - File.separator.length(), result.length());
        }

        return new File(result.toString() + File.separator);
    }

    public static String getDirectoryOfFilePath(String filePath) {
        filePath = filePath.replace("\\", "/");

        if (filePath.endsWith("/")) {
            //file path is an directory
            throw new IllegalArgumentException("file path cannot be an directory path.");
        }

        String[] array = filePath.split("/");

        //directory path
        String dirPath = "";

        for (int i = 0; i < array.length - 1; i++) {
            dirPath += array[i] + "/";
        }

        return dirPath;
    }

    public static void createWritableDirIfAbsent(String dirPath) {
        File f = new File(dirPath);

        if (!f.exists()) {
            f.mkdirs();
        }

        //check, if directory is writable or try to set directory writable
        if (!f.canWrite() && !f.setWritable(true)) {
            throw new IllegalStateException("directory '" + dirPath + "' is not writable and user dont have permissions to change file permissions.");
        }
    }

    public static void createFileIfAbsent(String filePath, String defaultContent) throws IOException {
        File file = new File(filePath);

        if (!file.exists()) {
            if (!file.createNewFile()) throw new IOException("Cannot create file: " + filePath);

            FileUtils.writeFile(filePath, defaultContent, StandardCharsets.UTF_8);
        }
    }

    /**
     * extracts the resource file into temp directory and returns the file instance
     * <p>
     * See also: https://stackoverflow.com/questions/676097/java-resource-as-file
     *
     * @param resourcePath resource path
     * @return file instance to resource
     */
    public static File getResourceAsFile(String resourcePath) throws IOException {
        URL url = ClassLoader.getSystemResource(resourcePath);

        if (url == null) {
            throw new FileNotFoundException("resource config file does not exists: " + resourcePath);
        }

        InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream(resourcePath);
        if (in == null) {
            return null;
        }

        File tempFile = File.createTempFile(String.valueOf(in.hashCode()), ".tmp");
        tempFile.deleteOnExit();

        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            //copy stream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        return tempFile;
    }

    public static Path getResourcePath(String resourcePath) throws IOException, URISyntaxException {
        URL url = ClassLoader.getSystemResource(resourcePath);

        if (url == null) {
            throw new FileNotFoundException("resource config file does not exists: " + resourcePath);
        }

        URI uri = url.toURI();

        /*final Map<String, String> env = new HashMap<>();
        final String[] array = uri.toString().split("!");
        final FileSystem fs = FileSystems.newFileSystem(URI.create(array[0]), env);
        final Path path = fs.getPath(array[1]);*/

        if ("jar".equals(uri.getScheme())) {
            for (FileSystemProvider provider : FileSystemProvider.installedProviders()) {
                if (provider.getScheme().equalsIgnoreCase("jar")) {
                    try {
                        provider.getFileSystem(uri);
                    } catch (FileSystemNotFoundException e) {
                        // in this case we need to initialize it first:
                        provider.newFileSystem(uri, Collections.emptyMap());
                    }
                }
            }
        }

        return Paths.get(uri);
    }

    public static String getContentFromInputStream(InputStream is, Charset charset) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, charset))) {
            return br.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

}
