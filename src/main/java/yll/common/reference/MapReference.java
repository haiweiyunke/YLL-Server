package yll.common.reference;

import com.github.relucent.base.util.collect.Mapx;

public class MapReference {

    private final Mapx value;

    public MapReference(Mapx value) {
        this.value = value;
    }

    public Mapx get() {
        return value;
    }
}
