/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mijuego.cuttherope;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *
 * @author river
 */
public class Player extends Actor {

    private TextureRegion texture;
    private Rectangle bounds;

    public Player(TextureRegion texture, float x, float y) {
        this.texture = texture;
        setPosition(x, y);
        setSize(texture.getRegionWidth(), texture.getRegionHeight());
        bounds = new Rectangle(x, y, getWidth(), getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(texture, getX(), getY());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        bounds.setPosition(getX(), getY());
    }

    public Rectangle getBounds() {
        return bounds;
    }
}
