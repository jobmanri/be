package com.example.bak.global.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractStubRepository<ID, ENTITY> {

    protected final List<ENTITY> store = new ArrayList<>();

    protected abstract ID getId(ENTITY entity);

    protected abstract boolean isSame(ID left, ID right);

    public ENTITY save(ENTITY entity) {
        ID id = getId(entity);

        if (id != null) {
            store.removeIf(it -> isSame(getId(it), id));
        }

        store.add(entity);
        return entity;
    }

    public Optional<ENTITY> findById(ID id) {
        return store.stream()
                .filter(e -> isSame(getId(e), id))
                .findFirst();
    }

    public List<ENTITY> findAll() {
        return List.copyOf(store);
    }
}