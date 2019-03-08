package com.xaguzman.artemiscommons.systems.rendering;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.xaguzman.artemiscommons.components.Collider;
import com.xaguzman.artemiscommons.components.collidershapes.CircleCollider;
import com.xaguzman.artemiscommons.components.collidershapes.ColliderShape;
import com.xaguzman.artemiscommons.components.collidershapes.RectangleCollider;
import com.xaguzman.artemiscommons.systems.CameraSystem;

public class CollisionShapeRenderingSystem extends IteratingSystem {

    private ShapeRenderer shapeRenderer;
    @Wire ComponentMapper<Collider> colliderMapper;
    @Wire CameraSystem camSystem;
    private Color shapesColor;

    public CollisionShapeRenderingSystem(){
        this(new Color(0.39215687f, 0.58431375f, 0.92941177f, 0.6f));
    }

    public CollisionShapeRenderingSystem(Color color){
        super(Aspect.all(Collider.class));
        shapesColor = color;
        shapeRenderer = new ShapeRenderer();
    }

    @Override
    protected void begin() {
        OrthographicCamera camera = camSystem.get();

        Gdx.gl.glLineWidth(1);
        Gdx.gl.glEnable(Gdx.gl.GL_BLEND);
        Gdx.gl.glBlendFunc(Gdx.gl.GL_SRC_ALPHA, Gdx.gl.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(0.39215687f, 0.58431375f, 0.92941177f, 0.6f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
    }

    @Override
    protected void process(int entityId) {
        Collider collider = colliderMapper.get(entityId);

        switch(collider.shape.getShapeType()){
            case ColliderShape.TYPE_CIRCLE:
                CircleCollider circle = (CircleCollider)collider.shape;
                shapeRenderer.circle( circle.bounds.x, circle.bounds.y, circle.bounds.radius);
                break;

            case ColliderShape.TYPE_RECTANGLE:
                RectangleCollider rect = (RectangleCollider)collider.shape;
                shapeRenderer.rect(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight());
                break;
        }
    }

    @Override
    protected void end() {
        shapeRenderer.end();
        Gdx.gl.glDisable(Gdx.gl.GL_BLEND);
    }
}
