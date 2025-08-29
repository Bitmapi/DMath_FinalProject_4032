package rsa;

import java.math.BigInteger;

public class RSAKeyPair {
    public final BigInteger n;
    public final BigInteger e;
    public final BigInteger d;
    public final BigInteger p;
    public final BigInteger q;

    public RSAKeyPair(BigInteger n, BigInteger e, BigInteger d, BigInteger p, BigInteger q) {
        this.n = n; this.e = e; this.d = d; this.p = p; this.q = q;
    }
}
