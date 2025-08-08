package com.softeer.entity;

import com.softeer.domain.Grid;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "grid")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class GridEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int x;
    private int y;

    public static Grid toDomainEntity(GridEntity entity) {
        return new Grid(entity.getId(), entity.getX(), entity.getY());
    }
    
    public static GridEntity from(Grid grid) {
        return new GridEntity(grid.id(), grid.x(), grid.y());
    }
}
