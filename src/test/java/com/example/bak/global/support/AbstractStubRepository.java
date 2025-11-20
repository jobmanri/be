package com.example.bak.global.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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

    public Page<ENTITY> findAll(Pageable pageable) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        int start = page * size;
        int end = Math.min(start + size, store.size());

        if (start > store.size()) {
            return new PageImpl<>(List.of(), pageable, store.size());
        }
        List<ENTITY> content = store.subList(start, end);
        return new PageImpl<>(new ArrayList<>(content), pageable, store.size());
    }

    public void deleteById(ID id) {
        store.removeIf(it -> getId(it).equals(id));
    }
}