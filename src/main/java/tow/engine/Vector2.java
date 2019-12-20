package tow.engine;

public class Vector2<T> {

    public T x, y;

    public Vector2(){}

    public Vector2(T x, T y){
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2<T> vector){
        this.x = vector.x;
        this.y = vector.y;
    }

    public Vector2<T> copy(){
        return new Vector2<>(x, y);
    }

    @Override
    public String toString(){
        return "{" + x +"," + y + "}";
    }
}
