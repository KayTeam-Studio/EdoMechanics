package org.kayteam.edomechanics.mechanics;

public abstract class Mechanic {

    private final String name;

    public Mechanic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
