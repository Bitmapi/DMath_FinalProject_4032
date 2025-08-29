package math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public final class BigMath {
    private BigMath() {}

    public static BigInteger modPow(BigInteger base, BigInteger exp, BigInteger mod) {
        BigInteger result = BigInteger.ONE;
        BigInteger a = base.mod(mod);
        BigInteger e = exp;
        while (e.signum() > 0) {
            if (e.testBit(0)) result = result.multiply(a).mod(mod);
            a = a.multiply(a).mod(mod);
            e = e.shiftRight(1);
        }
        return result;
    }

    public static BigInteger[] extendedGCD(BigInteger a, BigInteger b) {
        List<BigInteger> remainders = new ArrayList<>();
        List<BigInteger> quotients = new ArrayList<>();

        BigInteger r_old = a.abs();
        BigInteger r_new = b.abs();

        while (!r_new.equals(BigInteger.ZERO)) {
            BigInteger q = r_old.divide(r_new);
            BigInteger r_next = r_old.mod(r_new);

            remainders.add(r_old);
            quotients.add(q);

            r_old = r_new;
            r_new = r_next;
        }

        BigInteger gcd = r_old;
        if (gcd.equals(BigInteger.ZERO)) {
            return new BigInteger[]{BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO};
        }

        BigInteger x = BigInteger.ONE;
        BigInteger y = BigInteger.ZERO;

        for (int i = remainders.size() - 1; i >= 0; i--) {
            BigInteger q = quotients.get(i);
            BigInteger tmpX = y;
            BigInteger tmpY = x.subtract(q.multiply(y));

            x = tmpX;
            y = tmpY;
        }

        if (a.compareTo(BigInteger.ZERO) < 0) x = x.negate();
        if (b.compareTo(BigInteger.ZERO) < 0) y = y.negate();

        return new BigInteger[]{gcd, x, y};
    }

    public static BigInteger modInverse(BigInteger a, BigInteger m) {
        BigInteger[] res = extendedGCD(a, m);
        BigInteger gcd = res[0];
        BigInteger x = res[1];

        if (!gcd.equals(BigInteger.ONE)) {
            throw new ArithmeticException("Inverse does not exist!");
        }
        return x.mod(m);
    }

}
