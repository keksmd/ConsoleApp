/**
 * Класс управляющий основной коллекцией
 */
package com.alexkekiy.server.main.managers;

import com.alexkekiy.server.data.entities.SpaceMarine;
import com.alexkekiy.server.data.repositories.SpaceMarineRepository;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * синглтон-менеджер базы данных,связывющий бизнес логику и репозиторный слой
 */
@Setter
public class DataBaseManager {
    private static DataBaseManager inst;
    private static SpaceMarineRepository spaceMarineRepository = SpaceMarineRepository.getSpaceMarineRepository();

    public DataBaseManager getDatabaseManager() {
        return inst == null ? inst = new DataBaseManager() : inst;
    }

    public void updateSpaceMarine(@NotNull SpaceMarine spm, String[] args) {
        spm.update(args);
        spaceMarineRepository.update(spm);

    }

    public void add(SpaceMarine spm) {
        spaceMarineRepository.add(spm);
    }

    public void removeById(int id) {
        spaceMarineRepository.get(id).ifPresent(spm -> spaceMarineRepository.remove(spm));
    }
}
