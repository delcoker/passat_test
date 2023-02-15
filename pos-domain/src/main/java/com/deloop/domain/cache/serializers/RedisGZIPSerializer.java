package com.deloop.domain.cache.serializers;

import com.fasterxml.jackson.databind.JavaType;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class RedisGZIPSerializer<T> extends Jackson2JsonRedisSerializer<T> {
    private static final int OUTPUT_BUFFER_SIZE = 1024;

    public RedisGZIPSerializer(JavaType javaType) {
        super(javaType);
    }

    @Override
    public T deserialize(byte[] bytes) {
        return super.deserialize(decompress(bytes));
    }

    @Override
    public byte[] serialize(Object object) {
        return compress(super.serialize(object));
    }

    private byte[] compress(byte[] content) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(content);
        } catch (IOException e) {
            throw new SerializationException("Unable to compress data", e);
        }

        return byteArrayOutputStream.toByteArray();
    }

    private byte[] decompress(byte[] contentBytes) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(contentBytes))) {
            byte[] buffer = new byte[OUTPUT_BUFFER_SIZE];
            int length;
            while ((length = gzipInputStream.read(buffer, 0, OUTPUT_BUFFER_SIZE)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new SerializationException("Unable to decompress data", e);
        }

        return out.toByteArray();
    }
}
