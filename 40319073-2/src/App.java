import rsa.*;
import attack.SmallNAttack;

import java.math.BigInteger;

public class App {
    private static final int PRIME_BITS = 1024;
    private static final int MR_ROUNDS = 30;

    public static void main(String[] args) {
        System.out.println("RSA key generation with Miller-Rabin");
        long t0 = System.currentTimeMillis();
        RSAKeyPair keypair = RSA.generate(PRIME_BITS, MR_ROUNDS);
        long t1 = System.currentTimeMillis();
        System.out.println("Key generation took " + (t1 - t0) + " ms");
        System.out.println("n bitlength = " + keypair.n.bitLength());
        System.out.println("Public exponent e = " + keypair.e);

        BigInteger m = BigInteger.valueOf(239183048);
        System.out.println("\nNumeric test:");
        System.out.println("m = " + m);
        BigInteger c = RSA.encrypt(m, keypair);
        System.out.println("cipher (hex) = " + c.toString(16));
        BigInteger recovered = RSA.decrypt(c, keypair);
        System.out.println("recovered = " + recovered);

        System.out.println("\nSmall-n attack demo :");
        RSAKeyPair small = RSA.generate(300, 10);
        System.out.println("small n = " + small.n + " (bits=" + small.n.bitLength() + ")");
        var fac = SmallNAttack.factor(small.n);
        if (fac != null) {
            System.out.println("Factored small n => p=" + fac[0] + ", q=" + fac[1]);
        } else {
            System.out.println("Could not factor with naive method (n may be larger).");
        }

        System.out.println("\nDone.");
    }
}
