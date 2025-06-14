package net.dragonmounts.plus.common.util;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.*;

import java.util.function.Function;
import java.util.stream.Stream;

/// @see com.mojang.serialization.codecs.KeyDispatchCodec
public class DefaultedDispatchCodec<K, V> extends MapCodec<V> {
    private static final String COMPRESSED_VALUE_KEY = "value";
    private final String typeKey;
    private final Codec<K> keyCodec;
    private final Function<? super V, ? extends DataResult<? extends K>> type;
    private final Function<? super K, ? extends DataResult<? extends MapDecoder<? extends V>>> decoder;
    private final Function<? super V, ? extends DataResult<? extends MapEncoder<V>>> encoder;
    private final DataResult<K> fallback;

    protected DefaultedDispatchCodec(
            final Codec<K> keyCodec,
            final String typeKey,
            final DataResult<K> fallback,
            final Function<? super V, ? extends DataResult<? extends K>> type,
            final Function<? super K, ? extends DataResult<? extends MapDecoder<? extends V>>> decoder,
            final Function<? super V, ? extends DataResult<? extends MapEncoder<V>>> encoder
    ) {
        this.keyCodec = keyCodec;
        this.typeKey = typeKey;
        this.fallback = fallback;
        this.type = type;
        this.decoder = decoder;
        this.encoder = encoder;
    }

    public DefaultedDispatchCodec(
            final Codec<K> keyCodec,
            final String typeKey,
            final DataResult<K> fallback,
            final Function<? super V, ? extends DataResult<? extends K>> type,
            final Function<? super K, ? extends DataResult<? extends MapCodec<? extends V>>> codec
    ) {
        this(keyCodec, typeKey, fallback, type, codec, v -> getCodec(type, codec, v));
    }

    public DefaultedDispatchCodec(
            final Codec<K> keyCodec,
            final String typeKey,
            final K fallback,
            final Function<? super V, ? extends K> type,
            final Function<? super K, ? extends MapCodec<? extends V>> codec
    ) {
        this(keyCodec, typeKey, DataResult.success(fallback), type.andThen(DataResult::success), codec.andThen(DataResult::success));
    }

    @Override
    public <T> DataResult<V> decode(final DynamicOps<T> ops, final MapLike<T> input) {
        final T name = input.get(typeKey);
        return (name == null ? this.fallback : keyCodec.decode(ops, name).map(Pair::getFirst)).flatMap(type ->
                decoder.apply(type).flatMap(elementDecoder -> {
                    if (ops.compressMaps()) {
                        final T value = input.get(ops.createString(COMPRESSED_VALUE_KEY));
                        if (value == null) {
                            return DataResult.error(() -> "Input does not have a \"value\" entry: " + input);
                        }
                        return elementDecoder.decoder().parse(ops, value).map(Function.identity());
                    }
                    return elementDecoder.decode(ops, input).map(Function.identity());
                })
        );
    }

    @Override
    public <T> RecordBuilder<T> encode(final V input, final DynamicOps<T> ops, final RecordBuilder<T> prefix) {
        var result = encoder.apply(input);
        var builder = prefix.withErrorsFrom(result);
        if (result.isError()) return builder;
        return ops.compressMaps()
                ? prefix.add(typeKey, type.apply(input).flatMap(t -> keyCodec.encodeStart(ops, t)))
                .add(COMPRESSED_VALUE_KEY, result.result().get().encoder().encodeStart(ops, input))
                : result.result().get().encode(input, ops, prefix)
                .add(typeKey, type.apply(input).flatMap(t -> keyCodec.encodeStart(ops, t)));
    }

    @Override
    public <T> Stream<T> keys(final DynamicOps<T> ops) {
        return Stream.of(typeKey, COMPRESSED_VALUE_KEY).map(ops::createString);
    }

    @SuppressWarnings("unchecked")
    private static <K, V> DataResult<? extends MapEncoder<V>> getCodec(final Function<? super V, ? extends DataResult<? extends K>> type, final Function<? super K, ? extends DataResult<? extends MapEncoder<? extends V>>> encoder, final V input) {
        return type.apply(input)
                .<MapEncoder<? extends V>>flatMap(key -> encoder.apply(key).map(Function.identity()))
                .map(c -> ((MapEncoder<V>) c));
    }

    @Override
    public String toString() {
        return "DefaultedDispatchCodec[" + keyCodec.toString() + " " + type + " " + decoder + "]";
    }
}
