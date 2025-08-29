package rsa;

import math.BigMath;
import math.MillerRabin;

import java.math.BigInteger;

public final class RSA {
    private RSA() {}

    public static RSAKeyPair generate(int primeBits, int mrRounds) {
        BigInteger p = MillerRabin.randomPrime(primeBits, mrRounds);
        BigInteger q;
        do {
            q = MillerRabin.randomPrime(primeBits, mrRounds);
        } while (p.equals(q));

        BigInteger n = p.multiply(q);
        BigInteger phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        BigInteger e = BigInteger.valueOf(65537);
        if (e.compareTo(phi) >= 0 || !e.gcd(phi).equals(BigInteger.ONE)) {
        e = BigInteger.valueOf(3);
        while (e.compareTo(phi) < 0 && !e.gcd(phi).equals(BigInteger.ONE)) {
        e = e.add(BigInteger.TWO);
    }
}

        BigInteger d = BigMath.modInverse(e, phi);

        return new RSAKeyPair(n, e, d, p, q);
    }

    public static BigInteger encrypt(BigInteger m, RSAKeyPair key) {
        if (m.compareTo(BigInteger.ZERO) < 0 || m.compareTo(key.n) >= 0)
            throw new IllegalArgumentException("message must be in range [0, n)");
        return BigMath.modPow(m, key.e, key.n);
    }

    public static BigInteger decrypt(BigInteger c, RSAKeyPair key) {
        if (c.compareTo(BigInteger.ZERO) < 0 || c.compareTo(key.n) >= 0)
            throw new IllegalArgumentException("cipher must be in range [0, n)");
        return BigMath.modPow(c, key.d, key.n);
    }

}
