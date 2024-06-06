package com.alexkekiy.server.data.entities.util;

import com.alexkekiy.server.data.entities.Chapter;
import com.alexkekiy.server.data.entities.Coordinates;
import com.alexkekiy.server.data.entities.SpaceMarine;
import com.alexkekiy.server.data.entities.Weapon;

public class SpaceMarineBuilder {
    private final SpaceMarine spaceMarine;

    public SpaceMarineBuilder() {
        this.spaceMarine = new SpaceMarine();
    }

    public SpaceMarineBuilder(SpaceMarine spaceMarine) {
        this.spaceMarine = spaceMarine;
    }

    public SpaceMarineBuilder addChapter(Chapter chapter) {
        this.spaceMarine.setChapter(chapter);
        return this;
    }

    public SpaceMarineBuilder addName(String name) {
        this.spaceMarine.setName(name);
        return this;
    }

    public SpaceMarineBuilder addHeight(float height) {
        this.spaceMarine.setHeight(height);
        return this;
    }

    public SpaceMarineBuilder addHealth(long health) {
        this.spaceMarine.setHealth(health);
        return this;
    }

    public SpaceMarineBuilder addCoordinates(Coordinates coordinates) {
        this.spaceMarine.setCoordinates(coordinates);
        return this;
    }

    public SpaceMarineBuilder addLoyal(boolean loyal) {
        this.spaceMarine.setLoyal(loyal);
        return this;
    }

    public SpaceMarineBuilder addWeapoonType(Weapon weapoonType) {
        this.spaceMarine.setWeaponType(weapoonType);
        return this;
    }

    public SpaceMarine build() {
        return this.spaceMarine;
    }
}
