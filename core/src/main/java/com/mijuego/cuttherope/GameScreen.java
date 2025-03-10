/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mijuego.cuttherope;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

/**
 *
 * @author river
 */
public class GameScreen implements Screen {

    private World mundo;
    private OrthographicCamera camara;
    private Box2DDebugRenderer debugRenderer;
    private Cuerda cuerda;
    public static SpriteBatch batch;
    private Texture texturaCuerda;

    public GameScreen(World mundo) {
        this.mundo = mundo;
        this.camara = new OrthographicCamera(Gdx.graphics.getWidth() / 100f, Gdx.graphics.getHeight() / 100f);
        this.debugRenderer = new Box2DDebugRenderer();
        this.batch = new SpriteBatch();

        // Cargar la textura de la cuerda
        texturaCuerda = new Texture("cuerda.png");

        // Crear la cuerda
        cuerda = new Cuerda(mundo, 0, 5, texturaCuerda);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mundo.step(1 / 60f, 6, 2);
        camara.update();

        // Dibujar la cuerda
        batch.setProjectionMatrix(camara.combined);
        batch.begin();
        cuerda.render(batch);
        batch.end();

        // Dibujar el mundo con el debugRenderer (opcional)
        debugRenderer.render(mundo, camara.combined);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        debugRenderer.dispose();
        batch.dispose();
        texturaCuerda.dispose();
    }
}
