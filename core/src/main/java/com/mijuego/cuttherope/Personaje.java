/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mijuego.cuttherope;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author river
 */
public class Personaje extends Objeto {

    private Sprite sprite;

    public Personaje(Body body, World world, float x, float y, Texture texture) {
        // Crear el cuerpo dinámico para el personaje
        super(body);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(x, y);
        
        body = world.createBody(bodyDef);

        // Crear la forma del personaje (por ejemplo, un rectángulo)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(0.5f, 0.5f);  // Tamaño ajustable para el personaje

        // Crear la fixture del cuerpo
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.5f;  // Rebote

        body.createFixture(fixtureDef);
        shape.dispose();

        // Crear el sprite para el personaje
        sprite = new Sprite(texture);
        sprite.setPosition(x, y);
    }

    public void render(SpriteBatch batch) {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, 
                           body.getPosition().y - sprite.getHeight() / 2);
        sprite.setRotation((float) Math.toDegrees(body.getAngle()));
        sprite.draw(batch);
    }
}
