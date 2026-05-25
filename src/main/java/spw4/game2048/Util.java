package spw4.game2048;

public class Util {

    public static int randomBase2(int max) {
        int exponent = (int) (Math.random() * max);
        return  1 << exponent;
    }

}
