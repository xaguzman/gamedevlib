package com.xaguzman.artemiscommons.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;
import com.badlogic.gdx.utils.IntMap;
import com.xaguzman.artemiscommons.components.Collider;
import com.xaguzman.artemiscommons.components.Velocity;
import com.xaguzman.artemiscommons.components.transform.Origin;
import com.xaguzman.artemiscommons.components.transform.Position;

/**
 * Created by gdlxguzm on 3/28/2017.
 */
@SuppressWarnings("ALL")
public class SpatialHashgridSystem extends IteratingSystem {

    @Wire ComponentMapper<Collider> colMapper;
    @Wire ComponentMapper<Velocity> velMapper;
    @Wire ComponentMapper<Position> posMapper;
    @Wire ComponentMapper<Origin> originMapper;
    private final Vector2 movementDelta = new Vector2();

    private int cellSize, rows, cols;

    private IntArray[][] dynamicEntities;//Each cell in the grid is an array which contains the ids of the dynamic entities in that cell
    private IntArray[][] staticEntities;//Each cell in the grid is an array which contains the ids of the static entities in that cell
    private IntArray potentialCollidersIds;
    private IntMap<Collider> potentialColliders;
    private final SpatialHashgridSystem.Area boundsArea;

    public SpatialHashgridSystem(int worldWidth, int worldHeight, int cellSize){
        super(Aspect.all(Collider.class, Position.class));
        this.cellSize = cellSize;
        this.rows = (worldHeight + cellSize - 1) / cellSize;
        this.cols = (worldWidth + cellSize - 1) / cellSize;
        dynamicEntities = new IntArray[cols][rows];
        staticEntities = new IntArray[cols][rows];
        potentialCollidersIds = new IntArray(false, 64);
        potentialColliders = new IntMap<Collider>(64);
        boundsArea = new SpatialHashgridSystem.Area();

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

    private void calcCollisionArea(Collider bounds){
        float left = bounds.getLeft();
        float bottom = bounds.getBottom();
        float top = bounds.getTop();
        float right = bounds.getRight();

        boundsArea.set(
                Math.max(0, MathUtils.floor(left / cellSize)),
                Math.max(0, MathUtils.floor(bottom / cellSize)),
                Math.min(cols - 1, MathUtils.floor(right / cellSize)),
                Math.min(rows - 1, MathUtils.floor(top / cellSize))
        );
    }

    public void remove(int entityId){
        Collider collision = colMapper.get(entityId);
        calcCollisionArea(collision);

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
        Collider collision = colMapper.get(entityId);
        calcCollisionArea(collision);

        for (int x = boundsArea.x1; x <= boundsArea.x2; x++)
        {
            for (int y = boundsArea.y1; y <= boundsArea.y2; y++)
            {
                staticEntities[x][y].add(entityId);
            }
        }
    }

    public void addDynamicEntity(int entityId){
        Collider collision = colMapper.get(entityId);
        addDynamicEntity(entityId, collision);
    }

    public void addDynamicEntity(int entityId, Collider collision){
        calcCollisionArea(collision);

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
        potentialCollidersIds.clear();
        // Calculate the positions again

        Collider collision = colMapper.get(entityId);
        calcCollisionArea(collision);

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
                        if (!potentialCollidersIds.contains(retrieved) && retrieved != entityId)
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

    public IntMap<Collider> getPotentialColliders(int entityId) {
        return getPotentialColliders(entityId, true, true);
    }

    public IntMap<Collider> getPotentialColliders(int entityId, boolean includeDynamic, boolean includeStatic) {
        // Retrieve the list of collide-able entities
        potentialColliders.clear();
        // Calculate the positions again

        Collider collision = colMapper.get(entityId);
        calcCollisionArea(collision);

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

        Collider col = colMapper.get(entityId);
        Position pos = posMapper.get(entityId);
        movementDelta.set(pos.xy).sub(pos.prev);
        col.shape.move(movementDelta);
        addDynamicEntity(entityId, col);
    }

    class Area{
        int x1, x2, y1, y2;

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