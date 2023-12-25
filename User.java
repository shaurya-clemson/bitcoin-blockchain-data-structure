package org.example;

import java.security.*;
import java.security.spec.ECGenParameterSpec;

public class User {
    private final String username;
    private final KeyPair keyPair;
    private double balance;

    public User(String username) {
        this.username = username;
        this.keyPair = generateKeyPair();
        this.balance = 0;
    }

    public PublicKey getPublicKey() {
        return keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return keyPair.getPrivate();
    }

    public double getBalance() {
        return balance;
    }

    public void addToBalance(double amount) {
        balance += amount;
    }

    private KeyPair generateKeyPair() {
        try {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("EC");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256k1");
            keyGen.initialize(ecSpec, new SecureRandom());
            return keyGen.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUsername() {
        return username;
    }

    public KeyPair getKeyPair() {
        return keyPair;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
