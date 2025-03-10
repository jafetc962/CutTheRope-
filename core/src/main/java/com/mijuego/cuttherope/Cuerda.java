/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mijuego.cuttherope;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author river
 */
public class Cuerda {

    private static final int SEGMENTOS = 10;  // Número de segmentos de la cuerda
    private static final float ANCHO_SEGMENTO = 0.03f;  // Ancho de cada segmento
    private static final float LARGO_SEGMENTO = 0.3f;   // Largo de cada segmento

    private World mundo;
    private Body ultimoCuerpo;
    private List<Body> segmentos;
    private List<Sprite> sprites;
    private Dulce dulce;  // Objeto que representa el dulce al final de la cuerda

    public Cuerda(World mundo, float inicioX, float inicioY, Texture texturaSegmento) {
        this.mundo = mundo;
        this.segmentos = new ArrayList<>();
        this.sprites = new ArrayList<>();

        // Coloca el ancla en la parte superior central de la pantalla
        ultimoCuerpo = crearAnclaje(inicioX, inicioY);

        // Crear los segmentos de la cuerda que caen desde el ancla
        for (int i = 0; i < SEGMENTOS; i++) {
            // Cada segmento se coloca justo debajo del segmento anterior
            float posY = inicioY - (i + 1) * LARGO_SEGMENTO;
            Body segmento = crearSegmento(inicioX, posY);
            conectarSegmentos(ultimoCuerpo, segmento);  // Conectar con el segmento anterior
            ultimoCuerpo = segmento;
            segmentos.add(segmento);

            // Crear sprite para cada segmento
            Sprite sprite = new Sprite(texturaSegmento);
            sprite.setSize(ANCHO_SEGMENTO * 2, LARGO_SEGMENTO);  // Ajustar el tamaño del sprite
            sprites.add(sprite);
        }

        // Coloca el dulce al final de la cuerda (último segmento)
        Vector2 ultimaPosicion = segmentos.get(segmentos.size() - 1).getPosition();
        dulce = new Dulce(mundo, ultimaPosicion.x, ultimaPosicion.y);
    }

    private Body crearAnclaje(float x, float y) {
        BodyDef cuerpoDef = new BodyDef();
        cuerpoDef.type = BodyDef.BodyType.StaticBody;  // El ancla es un cuerpo estático
        cuerpoDef.position.set(x, y);

        Body cuerpo = mundo.createBody(cuerpoDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(ANCHO_SEGMENTO);

        cuerpo.createFixture(shape, 0);
        shape.dispose();

        return cuerpo;
    }

    private Body crearSegmento(float x, float y) {
        BodyDef cuerpoDef = new BodyDef();
        cuerpoDef.type = BodyDef.BodyType.DynamicBody;  // Los segmentos son cuerpos dinámicos
        cuerpoDef.position.set(x, y);

        Body cuerpo = mundo.createBody(cuerpoDef);

        // Crear una caja para el segmento
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(ANCHO_SEGMENTO, LARGO_SEGMENTO / 2);  // La forma debe ajustarse al tamaño de los segmentos

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f;
        fixtureDef.friction = 0.5f;
        fixtureDef.restitution = 0.3f;  // Bounciness para simular elasticidad
        cuerpo.createFixture(fixtureDef);

        // Limpiar el shape
        shape.dispose();

        return cuerpo;
    }

    private void conectarSegmentos(Body cuerpoA, Body cuerpoB) {
        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.bodyA = cuerpoA;
        jointDef.bodyB = cuerpoB;

        // Asegura que la distancia entre los segmentos sea igual al largo de cada segmento
        jointDef.length = LARGO_SEGMENTO;
        jointDef.frequencyHz = 4.0f;  // Frecuencia del resorte (esto controla la elasticidad)
        jointDef.dampingRatio = 0.5f;  // Amortiguación para evitar rebotes excesivos

        // Establecer que no colisionen entre sí
        jointDef.collideConnected = false;

        // Crear el joint en el mundo
        mundo.createJoint(jointDef);
    }

    public void actualizarPosicionDulce() {
        // Obtener la posición del último segmento y actualizar la posición del dulce
        Vector2 ultimaPosicion = segmentos.get(segmentos.size() - 1).getPosition();
        dulce.update(ultimaPosicion);
    }

    public void render(SpriteBatch batch) {
        // Dibuja cada segmento de la cuerda
        for (int i = 0; i < segmentos.size(); i++) {
            Body segmento = segmentos.get(i);
            Sprite sprite = sprites.get(i);

            Vector2 posicion = segmento.getPosition();
            float angulo = (float) Math.toDegrees(segmento.getAngle());

            sprite.setPosition(posicion.x - sprite.getWidth() / 2, posicion.y - sprite.getHeight() / 2);
            sprite.setRotation(angulo);
            sprite.draw(batch);
        }

        // Actualiza y dibuja el dulce
        actualizarPosicionDulce();
        dulce.render();
    }
}
