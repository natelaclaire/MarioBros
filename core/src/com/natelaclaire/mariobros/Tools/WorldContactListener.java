package com.natelaclaire.mariobros.Tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.natelaclaire.mariobros.Items.Item;
import com.natelaclaire.mariobros.MarioBros;
import com.natelaclaire.mariobros.Sprites.Enemy;
import com.natelaclaire.mariobros.Sprites.InteractiveTileObject;
import com.natelaclaire.mariobros.Sprites.Mario;

public class WorldContactListener implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        Fixture fixA = contact.getFixtureA();
        Fixture fixB = contact.getFixtureB();

        int cDef = fixA.getFilterData().categoryBits | fixB.getFilterData().categoryBits;

        switch (cDef) {
            case MarioBros.MARIO_HEAD_BIT | MarioBros.BRICK_BIT:
            case MarioBros.MARIO_HEAD_BIT | MarioBros.COIN_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.MARIO_HEAD_BIT) {
                    ((InteractiveTileObject) fixB.getUserData()).onHeadHit((Mario) fixA.getUserData());
                }
                else {
                    ((InteractiveTileObject) fixA.getUserData()).onHeadHit((Mario) fixB.getUserData());
                }
                break;
            case MarioBros.ENEMY_HEAD_BIT | MarioBros.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.ENEMY_HEAD_BIT) {
                    ((Enemy)fixA.getUserData()).hitOnHead((Mario) fixB.getUserData());
                }
                else {
                    ((Enemy)fixB.getUserData()).hitOnHead((Mario) fixA.getUserData());
                }
                break;
            case MarioBros.ENEMY_BIT | MarioBros.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.ENEMY_BIT) {
                    ((Enemy)fixA.getUserData()).reverseVelocity(true, false);
                }
                else {
                    ((Enemy)fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case MarioBros.MARIO_BIT | MarioBros.ENEMY_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.MARIO_BIT) {
                    ((Mario) fixA.getUserData()).hit((Enemy)fixB.getUserData());
                }
                else {
                    ((Mario) fixB.getUserData()).hit((Enemy)fixA.getUserData());
                }
                break;
            case MarioBros.ENEMY_BIT:
                ((Enemy)fixA.getUserData()).onEnemyHit((Enemy)fixB.getUserData());
                ((Enemy)fixB.getUserData()).onEnemyHit((Enemy)fixA.getUserData());
                break;
            case MarioBros.ITEM_BIT | MarioBros.OBJECT_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT) {
                    ((Item)fixA.getUserData()).reverseVelocity(true, false);
                }
                else {
                    ((Item)fixB.getUserData()).reverseVelocity(true, false);
                }
                break;
            case MarioBros.ITEM_BIT | MarioBros.MARIO_BIT:
                if (fixA.getFilterData().categoryBits == MarioBros.ITEM_BIT) {
                    ((Item)fixA.getUserData()).use((Mario) fixB.getUserData());
                }
                else {
                    ((Item)fixB.getUserData()).use((Mario) fixA.getUserData());
                }
                break;
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
