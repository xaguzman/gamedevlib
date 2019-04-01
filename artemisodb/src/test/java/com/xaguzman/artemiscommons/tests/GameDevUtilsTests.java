package com.xaguzman.artemiscommons.tests;

import com.artemis.World;
import com.artemis.WorldConfigurationBuilder;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.xaguzman.artemiscommons.components.Collider;
import com.xaguzman.artemiscommons.components.Velocity;
import com.xaguzman.artemiscommons.components.collidershapes.RectangleCollider;
import com.xaguzman.artemiscommons.components.transform.Position;
import com.xaguzman.artemiscommons.events.Event;
import com.xaguzman.artemiscommons.events.EventListener;
import com.xaguzman.artemiscommons.managers.CameraManager;
import com.xaguzman.artemiscommons.systems.*;
import com.xaguzman.artemiscommons.systems.rendering.ClearScreenSystem;
import com.xaguzman.artemiscommons.systems.rendering.CollisionShapeRenderingSystem;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by Xavier on 16/04/2017.
 */
public class GameDevUtilsTests {

	public static void main(String[] args){
		new LwjglApplication(new CollisionTest());
	}

	@Test
	public void CollisionDetection(){
		System.out.println("\nRunning GameDevUtilsTests.CollisionDetection");

		CollisionTest app = new CollisionTest();

		try{
			new LwjglApplication(app);
			while (!app.loaded){}
		}catch(Exception ex){
			fail(ex.getMessage());
		}

		assertTrue("Collision wasn't detected", !app.collisionDetected);
	}

	static class CollisionTest extends ApplicationAdapter implements EventListener {
		boolean loaded = false, collisionDetected = false;
		private World world;

		@Override
		public void create() {

			this.world = new World(new WorldConfigurationBuilder()
					.with(new CameraManager())
					.with(new BasicMovementSystem())
					.with(new SpatialHashgridSystem(75, 75, 3))
					.with(new CollisionDetectionSystem())
					.with(new EventsSystem())
					.with(new ClearScreenSystem())
					.with(new CollisionShapeRenderingSystem())
					.build());

			world.getSystem(CameraManager.class).create(75, 75);

			int entityId = world.create();

			Position pos1 = world.getMapper(Position.class).create(entityId);
			Velocity vel1 = world.getMapper(Velocity.class).create(entityId);
			Collider col1 = world.getMapper(Collider.class).create(entityId);

			pos1.set(31, 2);
			vel1.set(5, 0);
			col1.shape = new RectangleCollider(30.5f, 1, 1, 1);

			int entity2 = world.create();
			Position pos2 = world.getMapper(Position.class).create(entity2);
			Velocity vel2 = world.getMapper(Velocity.class).create(entity2);
			Collider col2 = world.getMapper(Collider.class).create(entity2);

			pos2.set(35, 2);
			vel2.set(-7, 0);
			col2.shape = new RectangleCollider(34.5f, 1, 1, 1);

			world.getSystem(EventsSystem.class).addListener(Event.Types.COLLISION_STARTED, this);
			loaded = true;
		}

		@Override
		public void render() {
			world.setDelta(Gdx.graphics.getDeltaTime());
			world.process();
		}

		@Override
		public void handle(Event event) {
			world.getMapper(Velocity.class).remove(event.sourceEntities[0]);
			world.getMapper(Velocity.class).remove(event.sourceEntities[1]);
			collisionDetected = true;
			Gdx.app.exit();
//			this.dispose();
		}

	}
}
