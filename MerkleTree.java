package org.example;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MerkleTree {
    private final String root;

    public MerkleTree(List<Transaction> transactions) {
        this.root = buildTree(transactions.stream()
                .map(Transaction::getTransactionHash)
                .collect(Collectors.toList()));
    }

    private String buildTree(List<String> transactionHashes) {
        List<String> currentLevel = transactionHashes;

        while (!currentLevel.isEmpty() && currentLevel.size() > 1) {
            List<String> nextLevel = new ArrayList<>();

            for (int i = 0; i < currentLevel.size() - 1; i += 2) {
                String concatenated = currentLevel.get(i) + currentLevel.get(i + 1);
                String hash = calculateHash(concatenated);
                nextLevel.add(hash);
            }

            if (currentLevel.size() % 2 != 0) {
                // If there's an odd number of elements, duplicate the last one
                String lastElement = currentLevel.get(currentLevel.size() - 1);
                String duplicatedHash = calculateHash(lastElement + lastElement);
                nextLevel.add(duplicatedHash);
            }

            currentLevel = nextLevel;
        }

        return currentLevel.isEmpty() ? null : currentLevel.get(0);  // The root of the Merkle tree
    }

    static String calculateHash(String data) {
        // This is a simplified hash function; you might want to use a stronger one
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public String getRoot() {
        return root;
    }
}

