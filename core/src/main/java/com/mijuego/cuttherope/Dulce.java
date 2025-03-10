/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mijuego.cuttherope;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author river
 */
public class Dulce {

    private Sprite candySprite;
    private Body candyBody;
    private World world;
    private boolean isCollected;

    public Dulce(World world, float x, float y) {
        this.world = world;
        isCollected = false;

        // Load the texture and create the sprite
        Texture candyTexture = new Texture(Gdx.files.internal("dulce1.png"));
        candySprite = new Sprite(candyTexture);
        candySprite.setSize(1f, 1f);
        candySprite.setPosition(x, y);

        // Create the Box2D body for the candy
        createBody(x, y);
    }

    private void createBody(float x, float y) {
        // Define the candy body and its properties
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);

        candyBody = world.createBody(bodyDef);

        // Create a circle shape for the candy (just like a ball)
        CircleShape shape = new CircleShape();
        shape.setRadius(0.5f);  // Adjust the radius to fit your candy sprite size

        // Define the fixture and attach it to the body
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.2f;
        fixtureDef.restitution = 0.3f;  // Bounciness
        candyBody.createFixture(fixtureDef);

        // Dispose of the shape
        shape.dispose();
    }

    public void update(Vector2 lastSegmentPosition) {
        if (isCollected) {
            // Hide the candy or take any other necessary action when collected
            candySprite.setAlpha(0); // Set alpha to 0 to hide the candy
        } else {
            // Update the candy's position based on the last segment's position
            candySprite.setPosition(lastSegmentPosition.x - candySprite.getWidth() / 2,
                    lastSegmentPosition.y - candySprite.getHeight() / 2);
        }
    }

    public void handleCollision(Objeto other) {
        if (other instanceof Personaje) {
            // Handle collection of the candy by the main character
            isCollected = true;
            // Disable the body and sprite to indicate it's collected
            candyBody.setActive(false);
            candySprite.setAlpha(0);
            // Transition to the next level or perform other actions
            // Call your level transition logic here
            System.out.println("Candy collected! Transitioning to next level...");
        } else if (other instanceof Suelo || other instanceof Obstaculo) {
            // Handle collision with the ground or obstacles
            // Restart the level or take necessary actions
            System.out.println("Candy hit the ground or an obstacle. Restarting level...");
            // Restart logic here
        }
    }

    public void render() {
        if (!isCollected) {
            candySprite.draw(GameScreen.batch);  // Draw the candy sprite
        }
    }

    public void dispose() {
        candySprite.getTexture().dispose();  // Dispose of the texture to free up resources
    }

    // Getter for the body, in case it's needed for more complex interactions
    public Body getCandyBody() {
        return candyBody;
    }
}
