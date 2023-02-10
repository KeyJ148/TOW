package cc.abro.orchengine.util;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Vector2<T> {

    public T x, y;

    public Vector2(Vector2<T> vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public Vector2<T> copy() {
        return new Vector2<>(x, y);
    }
}
