package org.example;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;

public class Transaction {
    private final String senderPublicKey;
    private final String senderName;
    private final String receiverName;
    private final String recipientPublicKey;
    private final double amount;
    private final String previousTransactionHash;
    private final PrivateKey senderPrivateKey;
    private final User sender;
    private final User receiver;
    private final String transactionHash;

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    private final String signature;

    public Transaction(User sender, User recipient, double amount, String previousTransactionHash, PrivateKey senderPrivateKey) {
        this.senderPublicKey = publicKeyToString(sender.getPublicKey());
        this.recipientPublicKey = publicKeyToString(recipient.getPublicKey());
        this.senderName = sender.getUsername();
        this.receiverName = recipient.getUsername();
        this.amount = amount;
        this.previousTransactionHash = previousTransactionHash;
        this.senderPrivateKey = senderPrivateKey;
        this.sender = sender;
        this.receiver = recipient;
        this.transactionHash = calculateTransactionHash();
        this.signature = signTransactionHash();
    }

    private String calculateTransactionHash() {
        String data = previousTransactionHash + recipientPublicKey + amount;
        return MerkleTree.calculateHash(data);
    }

    private String signTransactionHash() {
        try {
            Signature ecdsaSign = Signature.getInstance("SHA256withECDSA");
            ecdsaSign.initSign(senderPrivateKey);
            ecdsaSign.update(transactionHash.getBytes());
            byte[] signatureBytes = ecdsaSign.sign();

            return Base64.getEncoder().encodeToString(signatureBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getSenderPublicKey() {
        return senderPublicKey;
    }

    public String getRecipientPublicKey() {
        return recipientPublicKey;
    }

    public double getAmount() {
        return amount;
    }

    public String getPreviousTransactionHash() {
        return previousTransactionHash;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public String getSignature() {
        return signature;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getReceiverName() {
        return receiverName;
    }

    private String publicKeyToString(PublicKey publicKey) {
        return Base64.getEncoder().encodeToString(publicKey.getEncoded());
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", recipientPublicKey='" + recipientPublicKey + '\'' +
                ", transactionHash='" + transactionHash + '\'' +
                ", signature='" + signature + '\'' +
                '}';
    }
}
