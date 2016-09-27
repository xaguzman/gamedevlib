package com.xguzm.artemiscommons.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.xguzm.artemiscommons.components.Collision;
import com.xguzm.artemiscommons.components.Position;
import com.xguzm.artemiscommons.components.Velocity;

/**
 * System which partitions the world for faster collision detection
 */
public class SpatialHashSystem extends IteratingSystem {

    @Wire ComponentMapper<Collision> colMapper;
    @Wire ComponentMapper<Velocity> velMapper;
    @Wire ComponentMapper<Position> posMapper;


    private int cellSize, rows, cols;

    private IntArray[][] dynamicEntities;//Each cell in the grid is an array which contains the ids of the dynamic entities in that cell
    private IntArray[][] staticEntities;//Each cell in the grid is an array which contains the ids of the static entities in that cell
    private IntArray potentialCollidersIds;
    private IntMap<Collision> potentialColliders;
    private final Area boundsArea;

    public SpatialHashSystem(int worldWidth, int worldHeight, int cellSize){
        super(Aspect.all(Collision.class, Position.class));
        this.cellSize = cellSize;
        this.rows = (worldHeight + cellSize - 1) / cellSize;
        this.cols = (worldWidth + cellSize - 1) / cellSize;
        dynamicEntities = new IntArray[cols][rows];
        staticEntities = new IntArray[cols][rows];
        potentialCollidersIds = new IntArray(false, 64);
        potentialColliders = new IntMap<Collision>(64);
        boundsArea = new Area();

        for (int x=0; x<cols; x++)
        {
            for (int y=0; y<rows; y++)
            {
                dynamicEntities[x][y] = new IntArray(false, 32);
                staticEntities[x][y] = new IntArray(false, 32);
            }
        }

    }

    public void clearDynamicCells(){
        for (int x=0; x<cols; x++)
        {
            for (int y=0; y<rows; y++)
            {
                dynamicEntities[x][y].clear();
            }
        }
    }

    public void clearStaticCells(){
        for (int x=0; x<cols; x++)
        {
            for (int y=0; y<rows; y++)
            {
                staticEntities[x][y].clear();
            }
        }
    }

    private void getBoundsArea(Rectangle bounds){
        boundsArea.set(
                Math.max(0, MathUtils.floor(bounds.x / cellSize)),
                Math.max(0, MathUtils.floor(bounds.y / cellSize)),
                Math.min(cols - 1, MathUtils.floor((bounds.x + bounds.width) / cellSize)),
                Math.min(rows - 1, MathUtils.floor((bounds.y + bounds.height) / cellSize))
        );
    }

    public void remove(int entityId){
        Collision collision = colMapper.get(entityId);
        Rectangle bounds = collision.bounds;

        getBoundsArea(bounds);

        for (int x = boundsArea.x1; x <= boundsArea.x2; x++)
        {
            for (int y = boundsArea.y1; y <= boundsArea.y2; y++)
            {
                staticEntities[x][y].removeValue(entityId);
                dynamicEntities[x][y].removeValue(entityId);
            }
        }
    }

    public void addStaticEntity(int entityId){
        Collision collision = colMapper.get(entityId);
        Rectangle bounds = collision.bounds;

        getBoundsArea(bounds);

        for (int x = boundsArea.x1; x <= boundsArea.x2; x++)
        {
            for (int y = boundsArea.y1; y <= boundsArea.y2; y++)
            {
                staticEntities[x][y].add(entityId);
            }
        }
    }

    public void addDynamicEntity(int entityId){
        Collision collision = colMapper.get(entityId);
        addDynamicEntity(entityId, collision);
    }

    public void addDynamicEntity(int entityId, Collision collision){
        Rectangle bounds = collision.bounds;

        getBoundsArea(bounds);

        for (int x = boundsArea.x1; x <= boundsArea.x2; x++)
        {
            for (int y = boundsArea.y1; y <= boundsArea.y2; y++)
            {
                dynamicEntities[x][y].add(entityId);
            }
        }
    }

    public IntArray getPotentialCollidersIds(int entityId) {
        return getPotentialCollidersIds(entityId, true, true);
    }

    public IntArray getPotentialCollidersIds(int entityId, boolean includeDynamic, boolean includeStatic) {
        // Retrieve the list of collide-able entities
        potentialColliders.clear();
        // Calculate the positions again

        Collision collision = colMapper.get(entityId);
        Rectangle bounds = collision.bounds;

        getBoundsArea(bounds);

        for (int x = boundsArea.x1; x <= boundsArea.x2; x++)
        {
            for (int y = boundsArea.y1; y <= boundsArea.y2; y++)
            {
                IntArray cell;

                if (includeDynamic) {
                    cell = dynamicEntities[x][y];
                    // Add every entity in the cell to the list
                    for (int i = 0; i < cell.size; i++) {
                        int retrieved = cell.get(i);
                        // Avoid duplicate entries
                        if (!potentialCollidersIds.contains(retrieved))
                            potentialCollidersIds.add(retrieved);
                    }
                }

                if (includeStatic) {
                    cell = staticEntities[x][y];
                    for (int i = 0; i < cell.size; i++) {
                        int retrieved = cell.get(i);
                        // Avoid duplicate entries
                        if (!potentialCollidersIds.contains(retrieved))
                            potentialCollidersIds.add(retrieved);
                    }
                }

            }
        }
        return potentialCollidersIds;
    }

    public IntMap<Collision> getPotentialColliders(int entityId) {
        return getPotentialColliders(entityId, true, true);
    }

    public IntMap<Collision> getPotentialColliders(int entityId, boolean includeDynamic, boolean includeStatic) {
        // Retrieve the list of collide-able entities
        potentialColliders.clear();
        // Calculate the positions again

        Collision collision = colMapper.get(entityId);
        Rectangle bounds = collision.bounds;

        getBoundsArea(bounds);

        for (int x = boundsArea.x1; x <= boundsArea.x2; x++)
        {
            for (int y = boundsArea.y1; y <= boundsArea.y2; y++)
            {

                IntArray cell;
                if (includeDynamic) {
                    cell = dynamicEntities[x][y];
                    // Add every entity in the cell to the list
                    for (int i = 0; i < cell.size; i++) {
                        int retrieved = cell.get(i);
                        // Avoid duplicate entries
                        if (!potentialColliders.containsKey(retrieved)) {
                            potentialColliders.put(retrieved, colMapper.get(retrieved));
                        }
                    }
                }

                if (includeStatic) {
                    cell = staticEntities[x][y];
                    // Add every entity in the cell to the list
                    for (int i = 0; i < cell.size; i++) {
                        int retrieved = cell.get(i);
                        // Avoid duplicate entries
                        if (!potentialColliders.containsKey(retrieved)) {
                            potentialColliders.put(retrieved, colMapper.get(retrieved));
                        }
                    }
                }
            }
        }
        return potentialColliders;
    }


    // ARTEMIS ECS METHODS


    @Override
    protected void inserted(int entityId) {
        if (!velMapper.has(entityId)){
            addStaticEntity(entityId);
            return;
        }

    }

    @Override
    protected void removed(int entityId) {
        remove(entityId);
    }

    @Override
    protected void begin() {
        clearDynamicCells();
    }

    @Override
    protected void process(int entityId) {
        if (!velMapper.has(entityId))
            return;

        Collision col = colMapper.get(entityId);
        Position pos = posMapper.get(entityId);
        col.bounds.setPosition(pos.x() - (col.bounds.width * 0.5f), pos.y());

        addDynamicEntity(entityId, col);
    }

    class Area{
        public int x1, x2, y1, y2;

        public void set(int x1, int y1, int x2, int y2){
            this.x1 = x1;
            this.x2 = x2;
            this.y1 = y1;
            this.y2 = y2;
        }

        @Override
        public String toString() {
            return "[" + x1 + "," + y1 + "," + x2 + "," + y2+ "]";
        }
    }
}
