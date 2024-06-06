package com.alexkekiy.server.main.managers;

import com.alexkekiy.server.data.entities.SpaceMarine;
import com.alexkekiy.server.data.repositories.SpaceMarineRepository;

import java.util.PriorityQueue;
import java.util.stream.Stream;

/**
 * менеджер коллекции объектов,инкапсулирующий методы коллекции
 */
public class CollectionManager {
    private static CollectionManager inst = null;
    private final PriorityQueue<SpaceMarine> collection = new PriorityQueue<>();

    public static CollectionManager getCollectionManager() {
        if (inst == null) {
            inst = new CollectionManager();
            inst.collection.addAll(SpaceMarineRepository.getSpaceMarineRepository().getAll());
        }
        return inst;
    }

    public SpaceMarine poll() {
        return collection.poll();
    }

    public SpaceMarine peek() {
        return collection.peek();
    }

    public void add(SpaceMarine spm) {
        collection.add(spm);
    }

    public void remove(SpaceMarine spm) {
        collection.remove(spm);
    }

    public Stream<SpaceMarine> getCollectionStream() {
        return collection.stream();

    }

    public int getCollectionSize() {
        return collection.size();
    }
}
