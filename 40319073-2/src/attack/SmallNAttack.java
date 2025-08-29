package attack;

import java.math.BigInteger;

public final class SmallNAttack {
    private SmallNAttack() {}
    public static BigInteger[] factor(BigInteger n) {
        BigInteger two = BigInteger.TWO;
        if (n.mod(two).equals(BigInteger.ZERO)) return new BigInteger[]{ two, n.divide(two) };

        BigInteger i = BigInteger.valueOf(3);
        while (i.multiply(i).compareTo(n) <= 0) {
            if (n.mod(i).equals(BigInteger.ZERO)) {
                return new BigInteger[]{ i, n.divide(i) };
            }
            i = i.add(two);
        }
        return null;
    }
}
