package io.bayberry.aloha.support;

import io.bayberry.aloha.Channel;
import io.bayberry.aloha.Stream;
import io.bayberry.aloha.StreamRegistry;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultStreamRegistry implements StreamRegistry {

    private Map<Channel, Set<Stream>> streams = new ConcurrentHashMap<>();

    @Override
    public void register(Stream stream) {
        Set<Stream> streamSet = this.streams.getOrDefault(stream.getChannel(), new HashSet<>());
        streamSet.add(stream);
        streams.putIfAbsent(stream.getChannel(), streamSet);
    }

    @Override
    public void unregister(Stream stream) {
        Set<Stream> streamList = this.streams.get(stream.getChannel());
        if (streamList != null) {
            streamList.remove(stream);
            if (streamList.isEmpty()) {
                this.streams.remove(stream.getChannel());
            }
        }
    }

    @Override
    public Set<Stream> getStreams(Channel channel) {
        return streams.getOrDefault(channel, Collections.EMPTY_SET);
    }

    @Override
    public Set<Stream> getStreams() {
        Set<Stream> allStreams = new HashSet<>();
        this.streams.values().forEach(allStreams::addAll);
        return allStreams;
    }
}

