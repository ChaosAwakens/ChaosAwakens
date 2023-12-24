package io.github.chaosawakens.common.util;

import io.github.chaosawakens.api.animation.LazyLoadedField;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public final class FileUtil {
    public static final LazyLoadedField<File> CA_ASSET_DIR = new LazyLoadedField<>(() -> { // Credits to (prev. known as) MHLib
        final Path gameDir = FMLPaths.GAMEDIR.get();
        final File result = new File(gameDir.toFile(), "chaosawakens");

        return result;
    });
    public static final File CA_CLIENT_ASSET_DIR = new File(CA_ASSET_DIR.get(), "clientassets");
    public static final File CA_SERVER_ASSET_DIR = new File(CA_ASSET_DIR.get(), "serverassets");

    private FileUtil() {
        throw new IllegalAccessError("Attempted to instantiate a Utility Class!");
    }

    /**
     * Credits to the original source (http://www.java2s.com/example/java-book/compressing-byte-arrays.html) and DerToaster for the idea for this implementation.
     *
     * @param input The translated file.
     * @param compressionLevel The compression level. See {@link Deflater}
     * @param GZIPFormat Whether or not the resulting compressed data should be in GZIP format.
     *
     * @return A byte array containing the compressed data.
     */
    public static byte[] compressData(byte[] input, int compressionLevel, boolean GZIPFormat) {
        // Create a Deflater object to compress data
        Deflater compressor = new Deflater(compressionLevel, GZIPFormat);

        // Set the input for the compressor
        compressor.setInput(input);

        // Call the finish() method to indicate that we have
        // no more input for the compressor object
        compressor.finish();

        // Compress the data
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] readBuffer = new byte[1024];
        int readCount = 0;

        while (!compressor.finished()) {
            readCount = compressor.deflate(readBuffer);
            if (readCount > 0) {
                // Write compressed data to the output stream
                bao.write(readBuffer, 0, readCount);
            }
        }

        // End the compressor
        compressor.end();

        // Return the written bytes from output stream
        return bao.toByteArray();
    }

    /**
     * Credits to the original source (http://www.java2s.com/example/java-book/compressing-byte-arrays.html) and DerToaster for the idea for this implementation.
     *
     * @param input The compressed byte array data of the originally translated file.
     * @param GZIPFormat Whether or not the compressed input byte array was originally compressed in GZIP format.
     *
     * @return A byte array containing the decompressed data, which can then be read from/written to a file normally.
     *
     * @throws DataFormatException If the input byte array is not compressed or otherwise in an invalid format.
     */
    public static byte[] decompressData(byte[] input, boolean GZIPFormat) throws DataFormatException {
        // Create an Inflater object to compress the data
        Inflater decompressor = new Inflater(GZIPFormat);

        // Set the input for the decompressor
        decompressor.setInput(input);

        // Decompress data
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        byte[] readBuffer = new byte[1024];
        int readCount = 0;

        while (!decompressor.finished()) {
            readCount = decompressor.inflate(readBuffer);
            if (readCount > 0) {
                // Write the data to the output stream
                bao.write(readBuffer, 0, readCount);
            }
        }

        // End the decompressor
        decompressor.end();

        // Return the written bytes from the output stream
        return bao.toByteArray();
    }

    /**
     *
     *
     * @param targetFile
     * @param fileData
     * @return
     */
    public static boolean writeDataToFile(File targetFile, byte[] fileData) {
        if (!(targetFile.getParentFile().exists() || !targetFile.getParentFile().isDirectory()) && !targetFile.getParentFile().mkdirs()) return false;

        try (FileOutputStream fos = new FileOutputStream(targetFile)) {
            fos.write(fileData);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *
     *
     * @param directory
     * @param shouldHide
     *
     * @throws IOException
     */
    public static void checkAndMakeDirectory(File directory, boolean shouldHide) throws IOException {
        if (!directory.exists() && !directory.mkdirs()) throw new IOException("Unable to make directory [" + directory.getAbsolutePath() + "]!");
        else if (!directory.isDirectory()) {
            if (directory.delete()) checkAndMakeDirectory(directory, shouldHide);
            else throw new IOException("Directory [" + directory.getAbsolutePath() + "] is not a folder and thus could not be deleted!");
        }
    }
}