package math;

import java.math.BigInteger;
import java.security.SecureRandom;

public final class MillerRabin {
    private static final SecureRandom rnd = new SecureRandom();

    public static boolean isProbablePrime(BigInteger n, int rounds) {
        BigInteger TWO = BigInteger.TWO;
        if (n.compareTo(TWO) < 0) return false;
        if (n.equals(TWO) || n.equals(BigInteger.valueOf(3))) return true;
        if (n.mod(TWO).equals(BigInteger.ZERO)) return false;

        BigInteger nMinusOne = n.subtract(BigInteger.ONE);
        int s = nMinusOne.getLowestSetBit();
        BigInteger d = nMinusOne.shiftRight(s);

        for (int i = 0; i < rounds; i++) {
            BigInteger a;
            do {
                a = new BigInteger(n.bitLength(), rnd);
            } while (a.compareTo(BigInteger.ONE) <= 0 || a.compareTo(nMinusOne) >= 0);

            BigInteger x = BigMath.modPow(a, d, n);
            if (x.equals(BigInteger.ONE) || x.equals(nMinusOne)) {
                continue;
            }

            boolean composite = true;
            for (int r = 1; r < s; r++) {
                x = x.multiply(x).mod(n);
                if (x.equals(nMinusOne)) {
                    composite = false;
                    break;
                }
            }
            if (composite) return false;
        }
        return true;
    }

    public static BigInteger randomPrime(int bitLength, int rounds) {
        while (true) {
            BigInteger candidate = new BigInteger(bitLength, rnd).setBit(bitLength - 1).setBit(0);
            if (isProbablePrime(candidate, rounds)) return candidate;
        }
    }
}
