/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mijuego.cuttherope;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author river
 */
public class Obstaculo extends Objeto {

    public Obstaculo(Body body, World world, float x, float y, float width, float height) {
        // Crear el cuerpo dinámico para el obstáculo
        super(body);
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;  // Estático para que no se mueva
        bodyDef.position.set(x, y);

        body = world.createBody(bodyDef);

        // Crear la forma del obstáculo (rectángulo)
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width, height);

        // Crear la fixture del cuerpo
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = 0.7f;  // Mayor fricción para obstáculos

        body.createFixture(fixtureDef);
        shape.dispose();
    }
}
