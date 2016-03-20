#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;

import java.io.StringReader;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.Security;
import java.security.Signature;

public class LicenseSigner {

    public static String sign(String respXml) {
        String returnXml = "";
        try {
            String privKeyPEM = "-----BEGIN RSA PRIVATE KEY-----\n" +
                    "MIGaAgEAAkEAt5yrcHAAjhglnCEn6yecMWPeUXcMyo0+itXrLlkpcKIIyqPw546b\n" +
                    "GThhlb1ppX1ySX/OUA4jSakHekNP5eWPawIBAAJAW6/aVD05qbsZHMvZuS2Aa5Fp\n" +
                    "NNj0BDlf38hOtkhDzz/hkYb+EBYLLvldhgsD0OvRNy8yhz7EjaUqLCB0juIN4QIB\n" +
                    "AAIBAAIBAAIBAAIBAA==\n" +
                    "-----END RSA PRIVATE KEY-----";
            System.out.println(privKeyPEM);
            Security.addProvider(new BouncyCastleProvider());

            PEMParser pemParser = new PEMParser(new StringReader(privKeyPEM));
            PEMKeyPair pemKeyPair = (PEMKeyPair) pemParser.readObject();
            JcaPEMKeyConverter jcaPEMKeyConverter = new JcaPEMKeyConverter();
            KeyPair keyPair = jcaPEMKeyConverter.getKeyPair(pemKeyPair);
            PrivateKey privateKey = keyPair.getPrivate();

            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(privateKey);
            signature.update(respXml.getBytes());
            byte[] sign = signature.sign();
            returnXml = "<!-- " + bytesToHexString(sign) + " -->\n";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnXml;
    }

    private static String bytesToHexString(byte[] bArray) {
        StringBuilder sb = new StringBuilder(bArray.length);
        String sTemp;
        for (byte aBArray : bArray) {
            sTemp = Integer.toHexString(0xFF & aBArray);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }
}
