package com.natelaclaire.mariobros.Sprites;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.natelaclaire.mariobros.MarioBros;
import com.natelaclaire.mariobros.Screens.PlayScreen;

public class Mario extends Sprite {
    public World world;
    public Body b2Body;
    private TextureRegion marioStand;

    public Mario(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("little_mario"));

        this.world = world;

        marioStand = new TextureRegion(getTexture(), 0, 0, 16, 16);

        defineMario();

        setBounds(0, 0, 16/MarioBros.PPM, 16/MarioBros.PPM);
        setRegion(marioStand);
    }

    public void update(float dt) {
        setPosition(b2Body.getPosition().x - getWidth() / 2, b2Body.getPosition().y - getHeight() / 2);
    }

    public void defineMario() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(32/MarioBros.PPM, 32/MarioBros.PPM);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2Body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(6/MarioBros.PPM);

        fixtureDef.shape = shape;
        b2Body.createFixture(fixtureDef);
    }
}
