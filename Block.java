package org.example;

import java.util.List;

class Block {
    int index;
    long timestamp;
    List<Transaction> transactions;
    int proof;
    String previousBlockHeaderHash;
    String merkleRoot;

    public Block(int index, long timestamp, List<Transaction> transactions, int proof, String previousBlockHeaderHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.transactions = transactions;
        this.proof = proof;
        this.previousBlockHeaderHash = previousBlockHeaderHash;
        this.merkleRoot = calculateMerkleRoot(transactions);
    }

    private String calculateMerkleRoot(List<Transaction> transactions) {
        MerkleTree merkleTree = new MerkleTree(transactions);
        return merkleTree.getRoot();
    }

}
