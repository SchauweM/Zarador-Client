package org.runelive.client.graphics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.runelive.client.Signlink;
import org.runelive.client.cache.Archive;
import org.runelive.client.io.ByteBuffer;
import org.runelive.client.util.FileUtilities;

public final class CacheSpriteLoader {

    private static final List<Integer> cachedSpriteIdList = new ArrayList<>();
    private static final HashMap<Integer, byte[]> cachedSpriteData = new HashMap<>();

    private static boolean DUMP_SPRITES = true;

    static {
        /**
         * These sprites cache the byte[] containing the sprite data when loaded
         * for the HP bar, for example.
         */
        cachedSpriteIdList.add(348);
        cachedSpriteIdList.add(397);
    }

    private static int[] cachedSpritePositions;
    private static int[] cachedSpriteSizes;
    private static Sprite[] cachedSprites;
    private static int dataFileOffset;
    private static RandomAccessFile dataFile;

    private static int[] cachedSpritePositions2;
    private static int[] cachedSpriteSizes2;
    private static Sprite[] cachedSprites2;
    private static int dataFile2Offset;
    private static RandomAccessFile dataFile2;

    private static int[] cachedSpritePositions3;
    private static int[] cachedSpriteSizes3;
    private static Sprite[] cachedSprites3;
    private static int dataFile3Offset;
    private static RandomAccessFile dataFile3;

    public static byte[] getCacheSpriteData(int index) {
        if (!cachedSpriteData.containsKey(index) || cachedSpriteData.get(index) == null) {
            getCacheSprite(index);
        }
        return cachedSpriteData.get(index);
    }

    public static Sprite getCacheSprite(int index) {
        if (cachedSprites[index] == null) {
            cachedSprites[index] = decodeSprite(index);
        }
        return cachedSprites[index];
    }

    public static Sprite getCacheSprite2(int index) {
        if (cachedSprites2[index] == null) {
            cachedSprites2[index] = decodeSprite2(index);
        }
        return cachedSprites2[index];
    }

    public static Sprite getCacheSprite3(int index) {
        if (cachedSprites3[index] == null) {
            cachedSprites3[index] = decodeSprite3(index);
        }
        return cachedSprites3[index];
    }

    public static void loadCachedSpriteDefinitions(Archive archive) {
        final String fileName = "mainsprites.data";
        byte[] data = archive.get(fileName);
        StringBuilder sb = new StringBuilder();
        for (int index = 0; index < 10; index++) {
            sb.append(data[index] + ", ");
        }
        System.out.println("length: " + data.length + ", bytes: " + sb.toString());
        try {
            dataFile = new RandomAccessFile(Signlink.getCacheDirectory() + fileName, "rw");

            dataFile.setLength(0);
            dataFile.seek(0);
            dataFile.write(data);
            dataFile.seek(0);

            int spriteCount = dataFile.readShort();

            int idxSize = dataFile.readShort();
            byte[] idx = new byte[idxSize];
            dataFile.read(idx);
            dataFileOffset = 4 + idxSize;

            ByteBuffer index = new ByteBuffer(idx);
            int totalSprites = index.getShort();
            System.out.println("Total sprites in firstsprites: " + totalSprites);

            cachedSprites = new Sprite[totalSprites];
            cachedSpritePositions = new int[totalSprites];
            cachedSpriteSizes = new int[totalSprites];

            for (int file = 0; file < totalSprites; file++) {
                cachedSpritePositions[file] = index.getInt();
                cachedSpriteSizes[file] = index.getInt();
                //createSprite(file);
            }
        } catch (IOException e) {

        }
    }

    /**
     * Creates a sprite out of the spriteData.
     *
     * @param sprite
     */
    public static void createSprite(int id) {
        if (DUMP_SPRITES) {
            File directory = new File(Signlink.getCacheDirectory() + "/image_dump");
            if (!directory.exists()) {
                directory.mkdir();
            }
            getCacheSprite(id);
            FileUtilities.WriteFile(directory.getAbsolutePath() + System.getProperty("file.separator") + id + ".png", cachedSpriteData.get(id));
        }
    }

    public static void loadCachedSpriteDefinitions2(Archive archive) {
        final String fileName = "secondarysprites.data";
        byte[] data = archive.get(fileName);
        try {
            dataFile2 = new RandomAccessFile(Signlink.getCacheDirectory() + fileName, "rw");

            dataFile2.setLength(0);
            dataFile2.seek(0);
            dataFile2.write(data);
            dataFile2.seek(0);

            int spriteCount = dataFile2.readShort();

            int idxSize = dataFile2.readShort();
            dataFile2Offset = 4 + idxSize;
            byte[] idx = new byte[idxSize];
            dataFile2.read(idx);

            ByteBuffer index = new ByteBuffer(idx);
            int totalSprites = index.getShort();

            cachedSprites2 = new Sprite[totalSprites];
            cachedSpritePositions2 = new int[totalSprites];
            cachedSpriteSizes2 = new int[totalSprites];

            for (int file = 0; file < totalSprites; file++) {
                cachedSpritePositions2[file] = index.getInt();
                cachedSpriteSizes2[file] = index.getInt();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadCachedSpriteDefinitions3(Archive archive) {
        final String fileName = "thirdsprites.data";
        byte[] data = archive.get(fileName);
        try {
            dataFile3 = new RandomAccessFile(Signlink.getCacheDirectory() + fileName, "rw");

            dataFile3.setLength(0);
            dataFile3.seek(0);
            dataFile3.write(data);
            dataFile3.seek(0);

            int spriteCount = dataFile3.readShort();

            int idxSize = dataFile3.readShort();
            dataFile3Offset = 4 + idxSize;
            byte[] idx = new byte[idxSize];
            dataFile3.read(idx);

            ByteBuffer index = new ByteBuffer(idx);
            int totalSprites = index.getShort();
            System.out.println("Total sprites in thirdsprites: "+totalSprites);
            cachedSprites3 = new Sprite[totalSprites];
            cachedSpritePositions3 = new int[totalSprites];
            cachedSpriteSizes3 = new int[totalSprites];

            for (int file = 0; file < totalSprites; file++) {
                cachedSpritePositions3[file] = index.getInt();
                cachedSpriteSizes3[file] = index.getInt();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Sprite decodeSprite(int index) {
        int pos = cachedSpritePositions[index];
        int size = cachedSpriteSizes[index];
        try {
            dataFile.seek(dataFileOffset + pos);
            byte[] data = new byte[size];
            dataFile.read(data);
            if (cachedSpriteIdList.contains(index)) {
                cachedSpriteData.put(index, data);
            }
            return new Sprite(data, index, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Sprite decodeSprite2(int index) {
        int pos = cachedSpritePositions2[index];
        int size = cachedSpriteSizes2[index];
        try {
            dataFile2.seek(dataFile2Offset + pos);
            byte[] data = new byte[size];
            dataFile2.read(data);
            return new Sprite(data, index, 2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Sprite decodeSprite3(int index) {
        int pos = cachedSpritePositions3[index];
        int size = cachedSpriteSizes3[index];
        try {
            dataFile3.seek(dataFile3Offset + pos);
            byte[] data = new byte[size];
            dataFile3.read(data);
            return new Sprite(data, index, 3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}