package com.jukusoft.engine2d.core.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class GZipUtils {

    protected static final String LOG_TAG = "GZipUtils";

    /**
    * default constructor
    */
    protected GZipUtils () {
        //
    }

    public static byte[] compress (byte[] uncompressedData) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (GZIPOutputStream os = new GZIPOutputStream(baos)) {
            os.write(uncompressedData);
        } catch (IOException e) {
            throw new IllegalStateException("unable to compress resource: ", e);
        }

        return baos.toByteArray();
    }

    public static byte[] decompress (byte[] compressedData) {
        byte[] buffer = new byte[1024];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try (ByteArrayInputStream bis = new ByteArrayInputStream(compressedData);
             GZIPInputStream gzipInputStream = new GZIPInputStream(bis)) {

            int bytes_read;

            while ((bytes_read = gzipInputStream.read(buffer)) > 0) {
                baos.write(buffer, 0, bytes_read);
            }

            gzipInputStream.close();
            baos.close();

        } catch (IOException e) {
            throw new IllegalStateException("unable to decompress resource: ", e);
        }

        return baos.toByteArray();
    }

}
