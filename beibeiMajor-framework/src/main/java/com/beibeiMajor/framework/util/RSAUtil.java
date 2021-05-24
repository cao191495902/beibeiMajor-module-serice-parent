package com.beibeiMajor.framework.util;

import javax.crypto.Cipher;
import java.io.*;
import java.math.BigInteger;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法
 *
 * @author Jonathan Lai(xingbing.lai@beibei-Major.com)
 * @version 1.0.0 -- Datetime: 16-3-1 下午5:55
 */
public class RSAUtil {
    /**
     * String to hold name of the encryption algorithm.
     */
    public static final String ALGORITHM = "RSA";

    /**
     * String to hold name of the encryption padding.
     */
    public static final String PADDING = "RSA/NONE/NoPadding";

    /**
     * String to hold name of the security provider.
     */
    public static final String PROVIDER = "BC";

    private static final String ROOT_PATH = "/home/beibei-Major";
    private static final String FILE_NAME_RSA_KeyPair = "RSA_keyPair";
    private static final String FILE_RSA_KeyPair = "/home/beibei-Major/"+FILE_NAME_RSA_KeyPair;
    private static final String LOCAL_FILE_PATH = "file/";
//    private static final String FILE_RSA_KeyPair = "/";

//    static {
//        try {
//            FileUtils.forceMkdir(new File(ROOT_PATH));
//            FileUtils.copyInputStreamToFile(Thread.currentThread().getContextClassLoader().getResourceAsStream(LOCAL_FILE_PATH + File.separator + FILE_NAME_RSA_KeyPair), new File(FILE_RSA_KeyPair));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 生成密钥对
     *
     * @return KeyPair
     * @throws Exception
     */
    public static KeyPair generateKeyPair() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM, PROVIDER);
            final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低
            keyPairGen.initialize(KEY_SIZE, new SecureRandom());
            KeyPair keyPair = keyPairGen.generateKeyPair();
            saveKeyPair(keyPair);
            return keyPair;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public static KeyPair getKeyPair() throws Exception {
        FileInputStream fis = new FileInputStream(FILE_RSA_KeyPair);
        ObjectInputStream oos = new ObjectInputStream(fis);
        KeyPair kp = (KeyPair) oos.readObject();
        oos.close();
        fis.close();
        return kp;
    }

    public static void saveKeyPair(KeyPair kp) throws Exception {
        FileOutputStream fos = new FileOutputStream(FILE_RSA_KeyPair);
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        // 生成密钥
        oos.writeObject(kp);
        oos.close();
        fos.close();
    }

    /**
     * 生成公钥
     *
     * @param modulus        系数
     * @param publicExponent 公共指数
     * @return RSAPublicKey 公钥
     * @throws Exception
     */
    public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
        KeyFactory keyFac = null;
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            keyFac = KeyFactory.getInstance(ALGORITHM, PROVIDER);
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception(ex.getMessage());
        }

        RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
        try {
            return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * 生成私钥
     *
     * @param modulus         系数
     * @param privateExponent 私密指数
     * @return RSAPrivateKey 私钥
     * @throws Exception
     */
    public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
        KeyFactory keyFac = null;
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            keyFac = KeyFactory.getInstance(ALGORITHM, PROVIDER);
        } catch (NoSuchAlgorithmException ex) {
            throw new Exception(ex.getMessage());
        }

        RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
        try {
            return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
        } catch (InvalidKeySpecException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * 加密
     *
     * @param pk   加密的公钥
     * @param data 待加密的明文数据
     * @return 加密后的数据
     * @throws Exception
     */
    public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        try {
            final Cipher cipher = Cipher.getInstance(ALGORITHM, PROVIDER);
            cipher.init(Cipher.ENCRYPT_MODE, pk);
            int blockSize = cipher.getBlockSize();// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
            // 加密块大小为127byte,加密后为128byte;因此共有2个加密块，第一个127byte第二个为1个byte
            int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
            int leavedSize = data.length % blockSize;
            int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
            byte[] raw = new byte[outputSize * blocksSize];
            int i = 0;
            while (data.length - i * blockSize > 0) {
                if (data.length - i * blockSize > blockSize) {
                    cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
                } else {
                    cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
                }
                i++;
            }
            return raw;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 解密
     *
     * @param pk  解密的密钥
     * @param raw 已经加密的数据
     * @return 解密后的明文
     * @throws Exception
     */
    public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            final Cipher cipher = Cipher.getInstance(ALGORITHM, PROVIDER);
            cipher.init(Cipher.DECRYPT_MODE, pk);
            int blockSize = cipher.getBlockSize();
            ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
            int j = 0;

            while (raw.length - j * blockSize > 0) {
                bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
                j++;
            }
            return bout.toByteArray();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    /**
     * 解密前端javascript加密过的密文。
     * 如果为空，表示解密失败。
     *
     * @param arg 加密数据
     * @return 解密后的明文
     */
    public static String decryptRequestParam(String arg) {
        try {
            byte[] en_result = hexStr2ByteArr(arg);
            byte[] de_result = decrypt(RSAUtil.getKeyPair().getPrivate(), en_result);
            return new StringBuilder(new String(de_result)).reverse().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] hexStr2ByteArr(String strIn) {
        if (strIn == null || strIn.equals("")) {
            return null;
        }
//        strIn = strIn.toUpperCase();
        byte[] arrB = strIn.getBytes();
        int iLen = arrB.length;
        byte[] arrOut = new byte[iLen / 2];
        for (int i = 0; i < iLen; i = i + 2) {
            String strTmp = new String(arrB, i, 2);
            arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
        }
        return arrOut;
    }
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * * *
     *
     * @param args *
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        RSAPublicKey rsap = (RSAPublicKey) getKeyPair().getPublic();
//        RSAPrivateKey rsaa = (RSAPrivateKey) getKeyPair().getPrivate();
        System.out.println(rsap.getModulus().toString(16));
        System.out.println(rsap.getPublicExponent().toString(16));
//        System.out.println(rsaa.getModulus());
//        System.out.println(rsaa.getPrivateExponent());
        String test = "1456sf232xdfsd";
        byte[] en_test = encrypt(getKeyPair().getPublic(), test.getBytes());
        String de_test = decryptRequestParam(bytesToHexString(en_test));
        System.out.println(bytesToHexString(en_test));
        System.out.println(de_test);

//        RSAPublicKey pubk = RSAUtil.generateRSAPublicKey("be44aec4d73408f6b60e6fe9e3dc55d0e1dc53a1e171e071b547e2e8e0b7da01c56e8c9bcf0521568eb111adccef4e40124b76e33e7ad75607c227af8f8e0b759c30ef283be8ab17a84b19a051df5f94c07e6e7be5f77866376322aac944f45f3ab532bb6efc70c1efa524d821d16cafb580c5a901f0defddea3692a4e68e6cd".getBytes(), "10001".getBytes());
//////        RSAPrivateKey prik = RSAUtil.generateRSAPrivateKey("108368048788890560576572359774440094528241439236977435842254047613639902146619552188260903793435066711695213909830538008019609061114995118579433741950108897670960853465900185040198867356782389765444400474968011359678815704914512251924472572119499063342297571073597918012349131762728165202050464377633235643721".getBytes(), "247dfc60927b0343ddce035402291f3fa2eb155188a99da7b92a2a7f3e92d9ac2c98e9949adb376ed4e1d2594f874bc28c34b965bde555a9fc08cda3a01730f5b1e7c6aed9022ecd3d8bdda36dbb0b8a50a1d06660eafdd497883d95c683f44be484fdd1782c6c0094ed3a7584e260e663311ed90d4aa0a16b6a4360fcd7cee1".getBytes());
//        byte[] en_test = RSAUtil.encrypt(pubk, "123456a".getBytes());
//        byte[] de_test = RSAUtil.decrypt(prik, en_test);
//        System.out.println(new String(en_test));

//        String en_test = "82c4c22a0b997b52337eeb33787827a0133c3bab6dba004ab267bf866ba9c4dabf2e82716106f2194cb5ac992531f008fecbf90283cb9beeae896a4141082064392ef9aee8ce462bd02f503caf3ad80f3bc2ed5c31f3240f4234c4c126450f6983ae1a4cda4829e9403fae4ea44523fa24d5157d94286c270579f0d90af5306a";
//        PrivateKey prik = RSAUtil.getKeyPair().getPrivate();
//        byte[] de_test = RSAUtil.decrypt(prik, en_test.getBytes());
//        System.out.println(new String(de_test));

//        RSAPublicKey rsap = (RSAPublicKey) RSAUtil.generateKeyPair().getPublic();
//        RSAPublicKey rsap = (RSAPublicKey) RSAUtil.getKeyPair().getPublic();
//        System.out.println(rsap.toString());
//        System.out.println(rsap.getModulus().toString(16));
//        System.out.println(rsap.getPublicExponent().toString(16));
//
//        System.out.println("################################");
//        RSAPrivateKey rsapr = (RSAPrivateKey) RSAUtil.getKeyPair().getPrivate();
//        System.out.println(rsapr.toString());
//        System.out.println(rsapr.getModulus().toString(16));
//        System.out.println(rsapr.getPrivateExponent().toString(16));
//
//        System.out.println("################################");
//        byte[] en_test = RSAUtil.encrypt(rsap, "222222".getBytes());
//        byte[] de_test = RSAUtil.decrypt(rsapr, en_test);
//        System.out.println(new String(de_test));
//
//        System.out.println("################################");
//        String s = "32ffb0e8b7d916d09ee7862f73e26a648b29e9d53f3bbd9b2c8621cbdf28adff35d9c4ba3ec1ad70e532608781c89c354c861a382c049fdd43e4277a70502493f26b5595c9c4f46cf19fd0c850224b37bcc9a5fe59a78d3d2141b63c5e9b4e60e7bd9db248aa4951e4fb75034a96868ebacccb2cb88a8efac75a9d383e460d41";
//        System.out.println(decryptRequestParam(s));
//        generateKeyPair();

    }
}
