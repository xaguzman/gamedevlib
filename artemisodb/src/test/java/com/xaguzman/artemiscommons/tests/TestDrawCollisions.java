package com.xaguzman.artemiscommons.tests;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.xaguzman.artemiscommons.components.Collider;
import com.xaguzman.artemiscommons.components.collidershapes.RectangleCollider;
import com.xaguzman.artemiscommons.managers.CameraManager;

/**
 * Created by Xavier on 16/04/2017.
 */
public class TestDrawCollisions extends IteratingSystem {
	private ShapeRenderer renderer;
	@Wire
    CameraManager cameras;
	@Wire
	ComponentMapper<Collider> colMapper;

	/**
	 * Creates a new EntityProcessingSystem.
	 *
	 */
	public TestDrawCollisions() {
		super(Aspect.all(Collider.class));
	}

	@Override
	protected void initialize() {

		this.renderer = new ShapeRenderer();
		renderer.setColor(Color.SKY);
	}

	@Override
	protected void begin() {
		cameras.get().update();
		renderer.setProjectionMatrix(cameras.get().combined);
		renderer.begin(ShapeRenderer.ShapeType.Filled);
	}

	@Override
	protected void process(int entityId) {
		Collider collider = colMapper.get(entityId);

		if (collider.shape instanceof RectangleCollider){
			renderer.rect(
					collider.shape.getLeft(),
					collider.shape.getBottom(),
					collider.shape.getWidth(),
					collider.shape.getHeight()
			);
		}
	}

	@Override
	protected void end() {
		renderer.end();
	}
}