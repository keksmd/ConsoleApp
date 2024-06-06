package com.alexkekiy.server.data.entities;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import static com.alexkekiy.common.utilites.CheckingReader.readValidType;
import static java.time.LocalDateTime.now;
@Setter
@Getter
@Entity
@Table(name = "spacemarines")
/**
 * основной хранимый entity-дата-класс
 */

public class SpaceMarine implements Comparable<SpaceMarine>, Serializable {

    /**
     * id {@link SpaceMarine}Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name="user_id")
    private ServerAccount user_id;

    /**
     * Имя {@link SpaceMarine}.
     * Поле не может быть null и не может быть пустым.
     */
    @Column(name ="name")
    private String name;

    /**
     * Координаты {@link SpaceMarine}.
     * Поле не может быть null.
     */
    @OneToOne
    @JoinColumn(name = "Coordinate_id",referencedColumnName = "id")
    @Cascade(CascadeType.ALL)
    private Coordinates coordinates;

    /**
     * Дата создания {@link SpaceMarine}.
     * Поле не может быть null и его значение должно генерироваться автоматически.
     * Формат даты: "dd-MM-yyyy HH:mm"
     */
    @Column
    private LocalDateTime creationDate;

    /**
     * Уровень здоровья {@link SpaceMarine}.
     * Значение поля должно быть больше 0.
     */
    @Column(name="health")
    private long health;

    /**
     * Флаг лояльности {@link SpaceMarine}.
     * Поле может быть null.
     */
    @Column
    private Boolean loyal;

    /**
     * Рост {@link SpaceMarine}.
     */
    @Column
    private float height;

    /**
     * Вид оружия {@link SpaceMarine}.
     * Поле может быть null.
     */
    @Column
    private Weapon weaponType;

    /**
     * Глава {@link SpaceMarine}.
     * Поле не может быть null.
     */

    @OneToOne
    @JoinColumn(name = "Chapter_id",referencedColumnName = "id")
    @Cascade(CascadeType.ALL)
    @NotNull
    private Chapter chapter;
    public static SpaceMarine newInstance(String[] args){

        return new SpaceMarine(
                (String) readValidType("s", args[0]),
                new Coordinates(
                        (Long) readValidType("l", args[1]),
                        (Float) readValidType("f", args[2])),
                (Long) readValidType("l", args[3]),
                (Boolean) readValidType("b", args[4]),
                (Float) readValidType("f", args[5]),
                Weapon.choose(
                        (String) readValidType("s", args[6])),
                new Chapter(
                        (String) readValidType("s", args[7]),
                        (String) readValidType("s", args[8])));

    }

    public SpaceMarine(String n, Coordinates c, long h, Boolean l, float height, Weapon gun, Chapter ch) {
        super();
        this.name = n;
        this.health = h;
        this.coordinates = c;
        this.loyal = l;
        this.weaponType = gun;
        this.chapter = ch;
        this.height = height;
        this.creationDate = now();


    }

    public SpaceMarine() {
    }

    public SpaceMarine update(String[] args) {


        this.name = (String) readValidType("s", args[0]);
        this.coordinates = new Coordinates(
                (Long) readValidType("l", args[1]),
                (Float) readValidType("f", args[2]));

        this.health = (Long) readValidType("l", args[3]);
        this.loyal = (Boolean) readValidType("b", args[4]);
        this.height = (Float) readValidType("f", args[5]);
        this.weaponType = Weapon.choose(
                (String) readValidType("s", args[6]));
        this.chapter = new Chapter(
                (String) readValidType("s", args[7]),
                (String) readValidType("s", args[8]));

        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SpaceMarine that)) return false;
        return id == that.id && health == that.health && Float.compare(height, that.height) == 0 && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates) && Objects.equals(creationDate, that.creationDate) && Objects.equals(loyal, that.loyal) && weaponType == that.weaponType && Objects.equals(chapter, that.chapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, health, loyal, height, weaponType, chapter);
    }

    @Override
    public String toString() {

        return "***** " + this.getClass() + " Details *****\n" +
                "ID=" + getId() + "\n" +
                "Name=" + getName() + "\n" +
                "health=" + getHealth() + "\n" +
                "Coordinates=" + getCoordinates() + "\n" +
                "loyal=" + getLoyal() + "\n" +
                "chapter=" + getChapter() + "\n" +
                "weapoonType=" + getWeaponType() + "\n" +
                "height=" + getHeight() + "\n" +
                "creationDate=" + getCreationDate() + "\n" +
                "*****************************";
    }

    @Override
    public int compareTo(SpaceMarine other) {
        return this.name.compareTo(other.name);
    }



}
