package com.deloop.domain.cache.serializers;

import com.deloop.domain.exceptions.RedisSerializationException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Slf4j
public class GenericRedisGzipSerializer extends GenericJackson2JsonRedisSerializer {
    private static final int OUTPUT_BUFFER_SIZE = 1024;

    public GenericRedisGzipSerializer(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @SneakyThrows
    @Override
    public Object deserialize(byte[] bytes) {
        try {
            return super.deserialize(decompress(bytes));
        } catch (Exception e) {
            // Log the error and the input byte array
            log.error("Error deserializing data: {}", e.getMessage(), e);
            log.debug("Input byte array: {}", Arrays.toString(bytes));
            throw new RedisSerializationException(e.getMessage());
        }
    }

    @SneakyThrows
    @Override
    public byte[] serialize(Object object) {
        try {
            return compress(super.serialize(object));
        } catch (Exception e) {
            // Log the error and the input object
            log.error("Error serializing data: {}", e.getMessage(), e);
            log.debug("Input object: {}", object);
            throw new RedisSerializationException(e.getMessage());
        }
    }

    private byte[] compress(byte[] content) throws RedisSerializationException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(content);
        } catch (IOException e) {
            throw new RedisSerializationException("Unable to compress data");
        }

        return byteArrayOutputStream.toByteArray();
    }

    private byte[] decompress(byte[] contentBytes) throws RedisSerializationException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(contentBytes))) {
            byte[] buffer = new byte[OUTPUT_BUFFER_SIZE];
            int length;
            while ((length = gzipInputStream.read(buffer, 0, OUTPUT_BUFFER_SIZE)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new RedisSerializationException("Unable to decompress data");
        }

        return out.toByteArray();
    }
}
