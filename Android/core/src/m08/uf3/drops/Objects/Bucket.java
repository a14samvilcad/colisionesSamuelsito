package m08.uf3.drops.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import m08.uf3.drops.helper.AssetManager;
import m08.uf3.drops.Screens.GameScreen;
import  m08.uf3.drops.Utils.Settings;


public class Bucket extends Actor {

    public static final int WALLET_STANDING = 0;
    public static final int WALLET_RIGHT = 1;
    public static final int WALLET_LEFT = 2;

    private Vector2 position;
    private int width, height;
    private int direction;

    private Rectangle collisionRect;

    private TiledMapTileLayer collisionLayer;

    public Bucket(float x, float y, int width, int height, TiledMapTileLayer collisionLayer){
        this.width = width;
        this.height = height;
        this.collisionLayer = collisionLayer;
        position = new Vector2(x, y);

        direction = WALLET_STANDING;

        collisionRect = new Rectangle();
        collisionRect.x = x;
        collisionRect.y = y;
        collisionRect.width = this.width;
        collisionRect.height = this.height;

        setBounds(position.x, position.y, width, height);
        setTouchable(Touchable.enabled);
    }

    public void act(float delta){
        super.act(delta);

        //Collision con objectos "blocked"
        float oldX = getX();
        float oldY = getY();
        boolean collisionX = false;
        boolean collisionY = false;
        float tileWidth = collisionLayer.getTileWidth();
        float tileHeigth = collisionLayer.getTileHeight();

        //Izquierda derecha left rigth
            //left
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)){
                //top left
                collisionX = collisionLayer.getCell((int) (getX() / tileWidth),(int) ((getY() + getHeight() / tileHeigth)))
                        .getTile().getProperties().containsKey("blocked");

                //middle left
                if (!collisionX) {
                    collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeigth))
                            .getTile().getProperties().containsKey("blocked");
                }

                //bottom left
                if (!collisionX) {
                    collisionX = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeigth))
                            .getTile().getProperties().containsKey("blocked");
                }


                this.position.x -= Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
            }
            //rigth
            else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)){
                //top rigth
                collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight()) / tileHeigth))
                        .getTile().getProperties().containsKey("blocked");

                //middle rigth
                if (!collisionX){
                    collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight() / 2) / tileHeigth))
                            .getTile().getProperties().containsKey("blocked");
                }

                //bottom rigth
                if (!collisionX){
                    collisionX = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) (getY() / tileHeigth))
                            .getTile().getProperties().containsKey("blocked");
                }


                this.position.x += Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
            }

            //reaccion a las colisiones de objetos del eje X
            if (collisionX){
                setX(oldX);
                Settings.PLAYER_VELOCITY = 0;
            }

            //ARRIBA
            if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)){
                //top left
                collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) ((getY() + getHeight()) / tileHeigth))
                        .getTile().getProperties().containsKey("blocked");

                //top middle
                if (!collisionY){
                    collisionY = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) ((getY() + getHeight()) / tileHeigth))
                            .getTile().getProperties().containsKey("blocked");
                }

                //top rigth
                if (!collisionY){
                    collisionY = collisionLayer.getCell((int) ((getX() + getWidth()) / tileWidth), (int) ((getY() + getHeight()) / tileHeigth))
                            .getTile().getProperties().containsKey("blocked");
                }



                this.position.y += Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
            }

            //ABAJO
            else if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)){
                //bottom left
                collisionY = collisionLayer.getCell((int) (getX() / tileWidth), (int) (getY() / tileHeigth))
                        .getTile().getProperties().containsKey("blocked");

                //bottom middle
                if (!collisionY){
                    collisionY = collisionLayer.getCell((int) ((getX() + getWidth() / 2) / tileWidth), (int) (getY() / tileHeigth))
                            .getTile().getProperties().containsKey("blocked");
                }

                //bottom rigth
                if(!collisionY){
                    collisionY = collisionLayer.getCell((int) ((getX() + getWidth())  / tileWidth), (int) (getY() / tileHeigth))
                            .getTile().getProperties().containsKey("blocked");
                }



                this.position.y -= Settings.PLAYER_VELOCITY * Gdx.graphics.getDeltaTime();
            }

            //reaccion a las colisiones del eje Y
            if (collisionY){
                setY(oldY);
                Settings.PLAYER_VELOCITY = 0;
            }


            //Colision personaje con los bordes del mapa
            if (this.position.y <= 5){
                this.position.y = 5;
            }
            if (this.position.x <= 5){
                this.position.x = 5;
            }
            if (this.position.x >= 3840 - this.width - 5){
                this.position.x = 3840 - this.width - 5;
            }
            if (this.position.y >= 2160 - this.height - 2){
                this.position.y = 2160 - this.height- 2;
            }

            collisionRect.x = this.position.x;
            collisionRect.y = this.position.y;
            collisionRect.width = this.width;
            collisionRect.height = this.height;

    }

    // Canviem la wallet de la spacecraft: Puja
    public void goRight() {
        direction = WALLET_RIGHT;
    }

    // Canviem la wallet de la spacecraft: Baixa
    public void goLeft() {
        direction = WALLET_LEFT;
    }

    // Posem la wallet al seu estat original
    public void goStraight() {
        direction = WALLET_STANDING;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.draw(AssetManager.soldierImage, this.position.x, this.position.y, width, height);
    }

    // Getters dels atributs principals
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getCollisionRectBucket() {
        return collisionRect;
    }

}
